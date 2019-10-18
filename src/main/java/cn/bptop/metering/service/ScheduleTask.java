package cn.bptop.metering.service;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.dao.UserMapper;
import cn.bptop.metering.pojo.Metering;
import cn.bptop.metering.pojo.User;
import cn.bptop.metering.until.Tool;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.bptop.metering.until.ding.sendCardMsg;

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
    MeteringService meteringService;
    @Autowired
    UserMapper userMapper;

    @Async
    @Scheduled(cron = "0 0 14 * * ?")
    public void cronUpdate() throws ParseException, ApiException
    {
        Date date30;
        String[] statusCode = {"1", "3"};
        List<Metering> list = meteringMapper.findMetering(null, statusCode);
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date urlDate = Tool.getUrlDate(); //获取网络时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(urlDate);
        calendar.add(Calendar.MONTH, 1);
        date30 = calendar.getTime();
        for ( Metering aList : list )
        {
            Date validity = sdf.parse(aList.getmValidity());
            if (validity.before(urlDate) && aList.getmStatusCode() != 4)  //已过期
            {
                User user = userMapper.findUser(aList.getDdName());
                meteringMapper.updateStatusCode("4", aList.getId().toString());
                meteringService.updateStatus("4", aList.getId().toString());
                sendCardMsg(user.getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getmTool() + "**  \n 有效期至" + aList.getmValidity() +
                        "  \n 已过有效期，请停止使用！及时送检！");
            }
            else if (validity.before(date30) && aList.getmStatusCode() != 3)  //即将过期
            {
                meteringMapper.updateStatusCode("3", aList.getId().toString());
                meteringService.updateStatus("3", aList.getId().toString());
            }
        }
    }

    @Async
    @Scheduled(cron = "0 0 10 ? * 1,4")
//    @Scheduled(cron = "0,20,40 * * * * ? ")
    public void cronSend() throws ApiException
    {
        String[] statusCode = {"3", "4"};
        List<Metering> list = meteringMapper.findMetering(null, statusCode);
        //
        for ( Metering aList : list )
        {
            User user = userMapper.findUser(aList.getDdName());
            if (aList.getmStatusCode() == 3)
            {
                sendCardMsg(user.getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getmTool() + "**  \n 有效期至" + aList.getmValidity() +
                        "  \n 即将到期，请及时送检！");
            }
            else
            {
                sendCardMsg(user.getDdUserid(), "计量工具", "您的计量工具:  \n **" + aList.getmTool() + "**  \n 有效期至" + aList.getmValidity() +
                        "  \n 已过有效期，请停止使用！及时送检！");
            }
        }
    }
}

