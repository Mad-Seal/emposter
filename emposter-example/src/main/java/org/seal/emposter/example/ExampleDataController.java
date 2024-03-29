package org.seal.emposter.example;

/*-
 * #%L
 * io.github.mad-seal:emposter-example
 * %%
 * Copyright (C) 2022 - 2023 author or authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@RestController
public class ExampleDataController {

    private JavaMailSenderImpl client;
    @Value("madseal-logo.jpg")
    private File file;

    public ExampleDataController() {
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
        helper.setText("<div>text</div>", true);
        helper.setCc("cc1@seal.org");
        helper.setBcc("bcc1@seal.org");
        helper.addAttachment("attachment one.jpg", file);
        helper.addAttachment("attachment two.jpg", file);
        client.send(message);
    }

}
