package org.seal.wiser.spring;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;
import java.util.Properties;

public class EmailClient {

    private JavaMailSender javaMailSender;

    public EmailClient() {
        this.javaMailSender = init();
    }

    public void send() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("from-test@test.org");
        simpleMessage.setTo("to-test@test.org");
        simpleMessage.setCc("cc-test@test.org");
        simpleMessage.setBcc("bcc-test@test.org");
        simpleMessage.setSubject("subject-test");
        simpleMessage.setText("body-test");
        simpleMessage.setReplyTo("reply-to-test@test.org");
        simpleMessage.setSentDate(new Date());
        javaMailSender.send(simpleMessage);
    }

    private JavaMailSender init() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(25);

//        mailSender.setUsername("my.gmail@gmail.com");
//        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
