package cn.bptop.metering;

import cn.bptop.metering.until.Tool;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static cn.bptop.metering.until.ding.getAccess_token;
import static cn.bptop.metering.until.ding.sendCardMsg;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeteringApplicationTests
{

    /**
     *
     */

    @Test
    public void contextLoads() throws ParseException, ApiException
    {
        System.out.println(getAccess_token());
    }
}
