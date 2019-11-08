package cn.bptop.metering.service;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.dao.UserMapper;
import cn.bptop.metering.pojo.MeteringRecord;
import cn.bptop.metering.until.Tool;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.bptop.metering.until.Ding.sendCardMsg;

/**
 * 定时任务
 * 每日修改数据状态
 * 每周发送钉钉提醒
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class ScheduleTask
{

    @Autowired
    MeteringMapper meteringMapper;
    @Autowired
    MeteringRecordMapper meteringRecordMapper;
    @Autowired
    MeteringService meteringService;
    @Autowired
    UserMapper userMapper;

    //    定时修改记录状态
    @Async
    @Scheduled(cron = "0 0 14 * * ?")
//    @Scheduled(cron = "0,20,40 * * * * ?")
    public void cronUpdate() throws ParseException, ApiException
    {
        Date date30;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date urlDate = Tool.getUrlDate(); //获取网络时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(urlDate);
        calendar.add(Calendar.MONTH, 1);
        date30 = calendar.getTime();
//
        List<MeteringRecord> list = meteringRecordMapper.findRecordByStatus("2");
        for ( MeteringRecord aList : list )
        {
            Date validity = sdf.parse(aList.getMeteringValidity());
            if (validity.before(date30))
            {
                meteringRecordMapper.updateStatus("4", aList.getMeteringRecordId().toString());
            }
        }
        list = meteringRecordMapper.findRecordByStatus("4");
        for ( MeteringRecord aList : list )
        {
            Date validity = sdf.parse(aList.getMeteringValidity());
            if (validity.before(urlDate))
            {
                meteringRecordMapper.updateStatus("5", aList.getMeteringRecordId().toString());
                sendCardMsg(userMapper.findUser(aList.getUserId(), "").getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getUnifyId() + "**  \n 有效期至" + aList.getMeteringValidity() +
                        "  \n 已过有效期，请停止使用！及时送检！");
            }
        }
    }

    //定时发送通知消息
    @Async
    @Scheduled(cron = "0 0 10 ? * 1,4")
//    @Scheduled(cron = "0,20,40 * * * * ?")
    public void cronSend() throws ApiException
    {
        List<MeteringRecord> list = meteringRecordMapper.findRecordByStatus("5");
        for ( MeteringRecord aList : list )
        {
            sendCardMsg(userMapper.findUser(aList.getUserId(), "").getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getUnifyId() + "**  \n 有效期至" + aList.getMeteringValidity() +
                    "  \n 已过有效期，请停止使用！及时送检！");
        }

        list = meteringRecordMapper.findRecordByStatus("4");
        for ( MeteringRecord aList : list )
        {
            sendCardMsg(userMapper.findUser(aList.getUserId(), "").getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getUnifyId() + "**  \n 有效期至" + aList.getMeteringValidity() +
                    "  \n 即将到期，请及时送检！");
        }
    }
}