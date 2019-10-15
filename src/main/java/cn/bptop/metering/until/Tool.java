package cn.bptop.metering.until;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Tool
{
    /**
     * 网址访问
     *
     * @return urlDate 对象网址时间
     */
    public static Date getUrlDate()
    {
        Date date = new Date();
        try
        {
            URL url = new URL("http://time.tianqi.com");
            URLConnection conn = null;  //生成连接对象
            conn = url.openConnection();
            conn.connect();  //连接对象网页
            date = new Date(conn.getDate());  //获取对象网址时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  //设置日期格式
//            urlDate = df.format(date);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return date;
    }
}
