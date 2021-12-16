package org.seal.wiser.re;

import org.subethamail.smtp.internal.Constants;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;

public class ReWiserMessage {
    private byte[] messageData;
    private Session session;
    private String envelopeSender;
    private String envelopeReceiver;

    public ReWiserMessage(Session session, String envelopeSender, String envelopeReceiver, byte[] messageData) {
        this.session = session;
        this.envelopeSender = envelopeSender;
        this.envelopeReceiver = envelopeReceiver;
        this.messageData = messageData;
    }

    public MimeMessage getMimeMessage() throws MessagingException {
        return new MimeMessage(session, new ByteArrayInputStream(this.messageData));
    }

    /**
     * Get's the raw message DATA.
     */
    public byte[] getData() {
        return this.messageData;
    }

    /**
     * Get's the RCPT TO:
     */
    public String getEnvelopeReceiver() {
        return this.envelopeReceiver;
    }

    /**
     * Get's the MAIL FROM:
     */
    public String getEnvelopeSender() {
        return this.envelopeSender;
    }

    @Override
    public String toString() {
        if (this.getData() == null)
            return "";

        return new String(this.getData(), Constants.SMTP_CHARSET);
    }
}
