package cn.bptop.metering.until;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.dao.UserMapper;
import cn.bptop.metering.pojo.MeteringRecord;
import cn.bptop.metering.pojo.User;
import com.taobao.api.ApiException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static cn.bptop.metering.until.ding.getDdUser;
import static cn.bptop.metering.until.ding.getDepartment;

@Component
public class importExcel
{

    @Autowired
    MeteringMapper meteringMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MeteringRecordMapper meteringRecordMapper;

    public void importExcel()
    {
        ArrayList<MeteringRecord> list = new ArrayList<>();
        try
        {
            InputStream inputStream = new FileInputStream("/C:/Users/Administrator/Desktop/装调车间计量工具台账.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            for ( int i = 0; i < 3; i++ )
            {
                HSSFSheet sheetAt = workbook.getSheetAt(i);
                for ( Row row : sheetAt )
                {
                    if (row.getRowNum() == 0 || row.getRowNum() == 1)
                    {
                        continue;
                    }
                    String meteringName = row.getCell(1).getStringCellValue();
                    String unifyId = row.getCell(2).getStringCellValue();
                    String meteringPeriod = row.getCell(3).getStringCellValue();
                    String meteringValidity = row.getCell(5).getStringCellValue();
                    String meteringModel = row.getCell(6).getStringCellValue();
                    String meteringClassify = row.getCell(7).getStringCellValue();
                    String meteringRange = row.getCell(8).getStringCellValue();
                    String ddName = row.getCell(10).getStringCellValue();
                    String manufacturingId = row.getCell(12).getStringCellValue();
                    String Status = row.getCell(13).getStringCellValue();
                    String notes = row.getCell(15).getStringCellValue();
                    String meteringId = meteringMapper.findMetering(meteringName, meteringModel).get(0).getMeteringId().toString();
                    User user = userMapper.findUser("", ddName);
                    System.out.println(user.getUserId());
                    String department = getDepartment(getDdUser(user.getDdUserid()).getDepartment().get(0).toString()).getName();
                    String userId = user.getUserId();
                    String meteringStatus = "";
                    switch (Status)
                    {
                        case "已报废":
                            meteringStatus = "0";
                            break;
                        case "封存":
                            meteringStatus = "1";
                            break;
                        case "在用":
                            meteringStatus = "2";
                            break;
                        case "已送检":
                            meteringStatus = "3";
                            break;
                        case "即将过期":
                            meteringStatus = "4";
                            break;
                        case "已过期":
                            meteringStatus = "5";
                            break;
                    }
                    meteringRecordMapper.addRecord(meteringId, unifyId, meteringValidity, meteringRange, department, userId, ddName, manufacturingId, meteringStatus, notes);
                    System.out.println("插入" + meteringId + unifyId + meteringValidity + meteringRange + department + userId + ddName + manufacturingId + meteringStatus + notes);
                }
            }
            workbook.close();
        }
        catch (IOException | ApiException e)
        {
            e.printStackTrace();
        }
    }
}
