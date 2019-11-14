package cn.bptop.metering.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

@Component
@Service
public class EmailServer
{

    static
    {
        System.setProperty("mail.mime.splitlongparameters", "false");
    }
    // 发件人的 邮箱 和 密码
    public static String SEND_EMAIL_ACCOUNT = "jzzt@ztcj2019.onaliyun.com";
    public static String SEND_EMAIL_PASSWORD = "ztcj2020.";


    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同
    //在公司中进行开发时，需要与业务或项目经理确认smtp的服务器地址，
    // 公司邮箱的smtp地址与我们常用的邮箱smtp地址可能完全不同
    public static String SMTP_HOST = "smtp.mxhichina.com";
    //邮箱的smtp服务器端口,这里是开启ssl加密后的smtp服务器端口号
    public static String SMTP_PORT = "465";
    //开通smtp服务时有的邮箱会有授权码
    public static String AUTHORIZATION_CODE = "";
//    lkvlsmnrdijtdihd

    //在配置properties时使用到以下变量，固定不可变。
    public static String MAIL_TRANSPORT_PROTOCAL = "mail.transport.protocol";
    public static String MAIL_SMTP_HOST = "mail.smtp.host";
    public static String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static String MAIL_SMTP_PORT = "mail.smtp.port";

    public String sendEmail(String filePath, String to)
    {
        //邮箱的一些配置信息
        Properties properties = getProperties();
        //获取session，这里的session是javax包中的，注意不要导错包
        Session session = getSession(properties);

        //这里是设置要做为附件发送的文件的路径
        String inputFilePath = filePath;
        //创建邮件信息
        MimeMessage message = createMessage(session, inputFilePath, to);
        Transport transport = null;
        try
        {
            transport = session.getTransport();
            transport.connect(SEND_EMAIL_ACCOUNT, SEND_EMAIL_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            return e.toString();
        }
        finally
        {
            try
            {
                if (transport != null)
                {
                    transport.close();
                }
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
                return e.toString();
            }
        }
        return "200";
    }

    private MimeMessage createMessage(Session session, String inputFilePath, String to)
    {
        MimeMessage message = new MimeMessage(session);
        try
        {
            //设置发件人邮箱地址、名称、编码格式
            // message.setFrom(new InternetAddress(SEND_EMAIL_ACCOUNT, SEND_EMAIL_ACCOUNT.substring(0, SEND_EMAIL_ACCOUNT.indexOf("@")), "utf-8"));
            message.setFrom(new InternetAddress(SEND_EMAIL_ACCOUNT, "装调车间智能平台服务邮箱", "utf-8"));

            //设置收件人邮箱地址、名称、编码格式，可设置多个收件人邮箱
            message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress(to, "", "utf-8")});
            message.setSubject("装调车间计量工具管理台账");
            message.setSentDate(new Date());
            MimeMultipart messageMultipart = new MimeMultipart("mixed");
            message.setContent(messageMultipart);
            //邮件正文
            MimeBodyPart content = new MimeBodyPart();
            MimeMultipart contentMultipart = new MimeMultipart("related");
            MimeBodyPart body = new MimeBodyPart();
            body.setContent("装调车间计量工具管理台账", "text/html;charset=utf-8");
            contentMultipart.addBodyPart(body);
            content.setContent(contentMultipart);
            messageMultipart.addBodyPart(content);
            //邮件附件
            MimeBodyPart attachment = new MimeBodyPart();
            FileDataSource dataSource = new FileDataSource(new File(inputFilePath));
            DataHandler dataHandler = new DataHandler(dataSource);
            attachment.setDataHandler(dataHandler);
            attachment.setFileName("装调车间计量工具管理台账.xls");
            messageMultipart.addBodyPart(attachment);
            message.saveChanges();
        }
        catch (UnsupportedEncodingException | MessagingException e)
        {
            e.printStackTrace();
        }
        return message;
    }

    private Session getSession(Properties properties)
    {
        return Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(SEND_EMAIL_ACCOUNT, AUTHORIZATION_CODE);
            }
        });
    }

    private Properties getProperties()
    {
        // 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty(MAIL_TRANSPORT_PROTOCAL, "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty(MAIL_SMTP_HOST, SMTP_HOST);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty(MAIL_SMTP_AUTH, "true");            // 需要请求认证
        props.setProperty(MAIL_SMTP_PORT, SMTP_PORT);
        //邮箱使用ssl加密时需要以下代码，同时要使用对应的端口号，如163邮箱开启ssl加密后端口号为465。但是有的邮箱不需加密，或是报错，可将这三行代码注释掉。
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", SMTP_PORT);
        return props;
    }
}

