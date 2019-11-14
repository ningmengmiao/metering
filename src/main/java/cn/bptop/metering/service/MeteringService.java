package cn.bptop.metering.service;

import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.pojo.MeteringRecordVO;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import static cn.bptop.metering.until.Tool.getUrlDate;

@Service
public class MeteringService
{
    @Autowired
    MeteringRecordMapper meteringRecordMapper;
    //质检 385870218

    public String exportExcel() throws IOException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HHmmsszz");
        String file = "C:/file/装调车间计量工具台账" + sdf.format(getUrlDate()) + ".xls";
        FileOutputStream out = new FileOutputStream(new File(file));
        List<MeteringRecordVO> list = meteringRecordMapper.findRecord("");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFSheet sheet0 = wb.createSheet("A类");
        HSSFSheet sheet1 = wb.createSheet("B类");
        HSSFSheet sheet2 = wb.createSheet("C类");
        HSSFSheet[] sheet = {sheet0, sheet1, sheet2};
        for ( int i = 0; i < 3; i++ )
        {
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 15);
            sheet[i].addMergedRegion(region);
            HSSFRow titlerRow = sheet[i].createRow(0);
            titlerRow.createCell(0).setCellStyle(style);
            titlerRow.getCell(0).setCellValue("装调车间A类计量器具管理台账");
            titlerRow = sheet[i].createRow(1);
            titlerRow.createCell(0).setCellValue("序号");
            titlerRow.createCell(1).setCellValue("技术对象说明");
            titlerRow.createCell(2).setCellValue("统一编号");
            titlerRow.createCell(3).setCellValue("检定周期");
            titlerRow.createCell(4).setCellValue("定检日期");
            titlerRow.createCell(5).setCellValue("有效日期");
            titlerRow.createCell(6).setCellValue("型号");
            titlerRow.createCell(7).setCellValue("分类");
            titlerRow.createCell(8).setCellValue("测量范围");
            titlerRow.createCell(9).setCellValue("使用部门");
            titlerRow.createCell(10).setCellValue("使用人");
            titlerRow.createCell(11).setCellValue("使用地");
            titlerRow.createCell(12).setCellValue("出厂编号");
            titlerRow.createCell(13).setCellValue("用户状态");
            titlerRow.createCell(14).setCellValue("维护人");
            titlerRow.createCell(15).setCellValue("备注");
        }
        //4.遍历数据,创建数据行
        for ( MeteringRecordVO alist : list )
        {
            int i = 0;
            switch (alist.getMetering().getMeteringClassify())
            {
                case "A":
                    i = 0;
                    break;
                case "B":
                    i = 1;
                    break;
                case "C":
                    i = 2;
                    break;
            }
            //获取最后一行的行号
            int lastRowNum = sheet[i].getLastRowNum();
            HSSFRow dataRow = sheet[i].createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(lastRowNum);
            dataRow.createCell(1).setCellValue(alist.getMetering().getMeteringName());
            dataRow.createCell(2).setCellValue(alist.getMeteringRecord().getUnifyId());
            dataRow.createCell(3).setCellValue(alist.getMetering().getMeteringPeriod());
            dataRow.createCell(4).setCellValue(alist.getMeteringRecord().getMeteringValidity());
            dataRow.createCell(5).setCellValue(alist.getMeteringRecord().getMeteringValidity());
            dataRow.createCell(6).setCellValue(alist.getMetering().getMeteringModel());
            dataRow.createCell(7).setCellValue(alist.getMetering().getMeteringClassify());
            dataRow.createCell(8).setCellValue(alist.getMeteringRecord().getMeteringRange());
            dataRow.createCell(9).setCellValue(alist.getMeteringRecord().getPlant());
            dataRow.createCell(10).setCellValue(alist.getMeteringRecord().getDdName());
            dataRow.createCell(11).setCellValue(alist.getMeteringRecord().getDepartment());
            dataRow.createCell(12).setCellValue(alist.getMeteringRecord().getManufacturingId());
            String meteringStatus = "";
            switch (alist.getMeteringRecord().getMeteringStatus())
            {
                case "0":
                    meteringStatus = "已报废";
                    break;
                case "1":
                    meteringStatus = "封存";
                    break;
                case "2":
                    meteringStatus = "在用";
                    break;
                case "3":
                    meteringStatus = "在用";
                    break;
                case "4":
                    meteringStatus = "已送检";
                    break;
                case "5":
                    meteringStatus = "已过期";
                    break;
            }
            dataRow.createCell(13).setCellValue(meteringStatus);
            dataRow.createCell(14).setCellValue("林峪名");
            dataRow.createCell(15).setCellValue(alist.getMeteringRecord().getNotes());
        }
//
        for ( int i = 0; i < 3; i++ )
        {
            for ( int j = 1; j < sheet[i].getLastRowNum() + 1; j++ )
            {
                sheet[i].getRow(j).setHeight((short) 400);
                for ( int k = 0; k < sheet[i].getRow(1).getLastCellNum(); k++ )
                {
                    sheet[i].autoSizeColumn((short) k);
                    // 解决自动设置列宽中文失效的问题
                    sheet[i].setColumnWidth(k, sheet[i].getColumnWidth(k) * 17 / 10);
                    sheet[i].getRow(j).getCell(k).setCellStyle(style);
                }
            }
        }
//        //7.获取mimeType
//        ServletContext servletContext = ServletActionContext.getServletContext();
//        String mimeType = servletContext.getMimeType(fileName);
//        //8.获取浏览器信息,对文件名进行重新编码
//        HttpServletRequest request = ServletActionContext.getRequest();
//        fileName = FileUtils.filenameEncoding(fileName, request);
//        //9.设置信息头
//        response.setContentType(mimeType);
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        wb.write(out);
        out.close();
        return file;
    }
}



