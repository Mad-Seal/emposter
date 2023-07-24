package org.seal.emposter.smtp;

/*-
 * #%L
 * emposter
 * %%
 * Copyright (C) 2022 authors
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


import lombok.extern.slf4j.Slf4j;
import org.seal.emposter.backend.Email;
import org.seal.emposter.backend.MessageBackend;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.helper.BasicMessageListener;
import org.subethamail.smtp.server.SMTPServer;

import java.util.Collection;

/**
 * Main class responsible for starting SMTP server, listening for incoming emails and handling them accordingly
 */
@Slf4j
public class Emposter implements BasicMessageListener {

    private SMTPServer server;
    private MessageBackend messageBackend;
    private EmailConverter emailConverter;

    public static Emposter create(SMTPServer.Builder builder, MessageBackend messageBackend) {
        return new Emposter(builder, messageBackend);
    }

    private Emposter(SMTPServer.Builder builder, MessageBackend messageBackend) {
        this.server = builder.messageHandlerFactory(new ConsolidatedMessageHandlerFactory(this)).build();
        this.messageBackend = messageBackend;
        emailConverter = new EmailConverter();
    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop();
    }

    @Override
    public void messageArrived(MessageContext context, String from, String to, byte[] data) {
        log.debug("Message with {} bytes arrived", data.length);
        save(convert(to, data));
    }

    private void save(Email email) {
        try {
            messageBackend.save(email);
            log.debug("Email saved");
        } catch (Exception e) {
            log.error("Failed to save", e);
            throw new RuntimeException(e);
        }
    }

    private Email convert(String to, byte[] data) {
        try {
            return emailConverter.convert(data, to);
        } catch (Exception e) {
            log.error("Failed to convert raw mime message into email");
            throw new RuntimeException(e);
        }
    }

    public Collection<Email> getMessages() {
        return messageBackend.getAll();
    }

    public void clear() {
        messageBackend.clear();
    }

    public boolean canClear() {
        return messageBackend.canClear();
    }
}




