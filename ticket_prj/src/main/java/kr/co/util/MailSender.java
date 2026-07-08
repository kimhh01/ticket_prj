package kr.co.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String fromEmail;
    private final String fromName;

    public MailSender(
            String host,
            String port,
            String username,
            String password,
            String fromEmail,
            String fromName
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public void sendTempPasswordMail(String toEmail, String memberCode, String tempPassword) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(fromEmail, fromName, "UTF-8"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("[BallPick] 임시 비밀번호 안내", "UTF-8");

        String content =
                "<div style='font-family: Arial, sans-serif; font-size: 14px; line-height: 1.6;'>" +
                "<h2>BallPick 임시 비밀번호 안내</h2>" +
                "<p>안녕하세요.</p>" +
                "<p>요청하신 임시 비밀번호가 발급되었습니다.</p>" +
                "<hr>" +
                "<p><strong>아이디</strong> : " + memberCode + "</p>" +
                "<p><strong>임시 비밀번호</strong> : " + tempPassword + "</p>" +
                "<hr>" +
                "<p>로그인 후 반드시 비밀번호를 변경해 주세요.</p>" +
                "</div>";

        message.setContent(content, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}
