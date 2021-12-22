package org.seal.wiser.re;


import lombok.extern.slf4j.Slf4j;
import org.seal.wiser.backend.Email;
import org.seal.wiser.backend.MessageBackend;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.helper.BasicMessageListener;
import org.subethamail.smtp.server.SMTPServer;

import java.util.Collection;

@Slf4j
public class ReWiser implements BasicMessageListener {

    private SMTPServer server;
    private MessageBackend messageBackend;
    private Converter converter;

    public static ReWiser create(SMTPServer.Builder builder, MessageBackend messageBackend) {
        return new ReWiser(builder, messageBackend);
    }

    private ReWiser(SMTPServer.Builder builder, MessageBackend messageBackend) {
        this.server = builder.messageHandlerFactory(new ConsolidatedMessageHandlerFactory(this)).build();
        this.messageBackend = messageBackend;
        converter = new Converter();
    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop();
    }

    @Override
    public void messageArrived(MessageContext context, String from, String to, byte[] data) {
        log.debug("Creating message from data with {} bytes", data.length);
        try {
            messageBackend.save(converter.convert(data, to));
        } catch (Exception e) {
            log.error(" :( ", e);
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




