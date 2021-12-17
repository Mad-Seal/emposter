package org.seal.wiser.test;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private JavaMailSenderImpl client;

    public TestController() {
        JavaMailSenderImpl client = new JavaMailSenderImpl();
        client.setHost("localhost");
        client.setPort(25);
        this.client = client;
    }

    @GetMapping("test")
    public void test() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("from@seal.org");
        simpleMessage.setTo("to1@seal.org", "to2@seal.org");
        simpleMessage.setSubject("subj");
        simpleMessage.setText("text");
        simpleMessage.setCc("cc1@seal.org");
        simpleMessage.setBcc("bcc1@seal.org");
        client.send(simpleMessage);

    }

}
