package com.example.javamail;

public class JavaMailSend {
    public static void main(String[] args){
        //这个类主要是设置邮件
        MailSendInfo mailInfo = new MailSendInfo();
        mailInfo.setMailServerHost("smtp.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("fengtao256@qq.com");
        mailInfo.setPassword("aajzaboegmbffjgi");//您的邮箱授权码aajzaboegmbffjgi
        mailInfo.setFromAddress("fengtao256@qq.com");
        mailInfo.setToAddress("1565388683@qq.com");
        mailInfo.setSubject("设置邮箱标题 Java Mail Test");
        mailInfo.setContent("设置邮箱内容 这是一个javax Mail测试,是涛哥给你发的");
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式
    }
}
