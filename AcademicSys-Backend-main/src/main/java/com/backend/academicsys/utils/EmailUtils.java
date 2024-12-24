package com.backend.academicsys.utils;

import com.backend.academicsys.entity.Result;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    // 发件人邮箱、密码（这里使用的是授权码，不是邮箱登录密码）
    static final String username = "AcademicSystem_SE@163.com";
    static final String password = "RXEPEMEWDFKEBSWS";

    public static Result<String> sendEmail(String to, String subject, String content) {
        // 邮件服务器配置
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.163.com"); // SMTP服务器地址
        prop.put("mail.smtp.auth", "true"); // 需要验证
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.port", "465"); // 更新端口为465

        // 创建session
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // 设置发件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 设置收件人
            message.setSubject(subject); // 设置邮件主题
            message.setText(content); // 设置邮件正文

            // 发送邮件
            Transport.send(message);
            return new Result<>(200, "发送成功", null);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new Result<>(500, "发送失败", null);
        }
    }
}
