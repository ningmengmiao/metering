package cn.bptop.metering;

import cn.bptop.metering.service.EmailServer;
import cn.bptop.metering.service.MeteringService;
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
    EmailServer emailServer;

    @Test
    public void contextLoads() throws ParseException, ApiException, IOException
    {
    }
}
