package cn.bptop.metering;

import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.service.EmailServer;
import cn.bptop.metering.service.MeteringService;
import cn.bptop.metering.until.importExcel;
import cn.bptop.metering.until.updateUser;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeteringApplicationTests
{
    @Autowired
    MeteringService meteringService;
    @Autowired
    MeteringRecordMapper meteringRecordMapper;
    @Autowired
    EmailServer emailServer;
    @Autowired
    importExcel importExcel;
    @Autowired
    updateUser updateUser;

    @Test
    public void contextLoads() throws ParseException, ApiException, IOException
    {
//        importExcel.importExcel();
//        updateUser.updateUser();
//        List<MeteringRecordVO> record = meteringRecordMapper.findRecord("", "");
//        for ( int i = 0; i <record.size() ; i++ )
//        {
//            if(!record.get(i).getMeteringRecord().getNotes().equals("")){
//                System.out.println(record.get(i).getMeteringRecord().getMeteringRecordId());
//                meteringService.addLog(String.valueOf(record.get(i).getMeteringRecord().getMeteringRecordId()), record.get(i).getMeteringRecord().getNotes());
//
//            }
//
//        }
    }
}
