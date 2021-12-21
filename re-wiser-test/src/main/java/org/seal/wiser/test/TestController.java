package org.seal.wiser.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
public class TestController {

    private JavaMailSenderImpl client;
    @Value("madseal-logo.jpg")
    private File file;

    public TestController() {
        JavaMailSenderImpl client = new JavaMailSenderImpl();
        client.setHost("localhost");
        client.setPort(25);
        this.client = client;
    }

    @GetMapping("test")
    public void testSimple() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("from@seal.org");
        simpleMessage.setTo("to1@seal.org", "to2@seal.org");
        simpleMessage.setSubject("subj");
        simpleMessage.setText("text");
        simpleMessage.setCc("cc1@seal.org");
        simpleMessage.setBcc("bcc1@seal.org");
        client.send(simpleMessage);
    }

    @GetMapping("test-multipart")
    public void testMultipart() throws MessagingException {
        MimeMessage message = client.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("from@seal.org");
        helper.setTo(new String[]{"to1@seal.org", "to2@seal.org"});
        helper.setSubject("subj");
        helper.setText("text", true);
        helper.setCc("cc1@seal.org");
        helper.setBcc("bcc1@seal.org");
        helper.addAttachment("important-attachment", file);
        client.send(message);

    }

}
