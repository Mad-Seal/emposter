package org.seal.wiser.re;


import lombok.extern.slf4j.Slf4j;
import org.seal.wiser.backend.MessageBackend;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.server.SMTPServer;
import org.subethamail.wiser.Wiser;

import javax.mail.Session;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

@Slf4j
public class ReWiser implements SimpleMessageListener {

    private static final Wiser.Accepter ACCEPTER_DEFAULT = (from, recipient) -> {
        log.debug("Accepting mail from {} to {}", from, recipient);
        return true;
    };

    private SMTPServer server;
    private MessageBackend messageBackend;
    private Wiser.Accepter accepter;

    public static ReWiser create(SMTPServer.Builder builder, MessageBackend messageBackend) {
        return new ReWiser(builder, ACCEPTER_DEFAULT, messageBackend);
    }

    private ReWiser(SMTPServer.Builder builder, Wiser.Accepter accepter, MessageBackend messageBackend) {
        this.server = builder.simpleMessageListener(this).build();
        this.accepter = accepter;
        this.messageBackend = messageBackend;
    }

    /**
     * Starts the SMTP Server
     */
    public void start() {
        this.server.start();
    }

    /**
     * Stops the SMTP Server
     */
    public void stop() {
        this.server.stop();
    }

    @Override
    public boolean accept(String from, String recipient) {
        return accepter.accept(from, recipient);
    }

    /**
     * Cache the messages in memory
     */
    @Override
    public void deliver(String from, String recipient, InputStream data) throws IOException {
        log.debug("Delivering mail from {} to {}", from, recipient);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        data = new BufferedInputStream(data);

        // read the data from the stream
        int current;
        while ((current = data.read()) >= 0) {
            out.write(current);
        }

        byte[] bytes = out.toByteArray();

        log.debug("Creating message from data with {} bytes", bytes.length);

        Session session = Session.getDefaultInstance(new Properties());
        messageBackend.save(new ReWiserMessage(session, from, recipient, bytes));
    }

    /**
     * Returns the list of WiserMessages.
     * <p>
     * The number of mail transactions and the number of mails may be different.
     * If a message is received with multiple recipients in a single mail
     * transaction, then the list will contain more WiserMessage instances, one
     * for each recipient.
     */
    public Collection<ReWiserMessage> getMessages() {
        return messageBackend.getAll();
    }

    public void clear() {
        messageBackend.clear();
    }

    /**
     * @return the server implementation
     */
    public SMTPServer getServer() {
        return this.server;
    }

}




