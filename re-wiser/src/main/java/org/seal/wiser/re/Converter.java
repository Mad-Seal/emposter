package org.seal.wiser.re;

import lombok.SneakyThrows;
import org.seal.wiser.backend.Attachment;
import org.seal.wiser.backend.Email;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class Converter {

    @SneakyThrows
    public Email convert(byte[] messageData, String to) {
        MimeMessage mimeMessage = new MimeMessage(
                Session.getDefaultInstance(new Properties()), new ByteArrayInputStream(messageData));
        Email email = new Email();
        email.setFrom(addressesAsString(mimeMessage.getFrom()));
        email.setTo(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.TO)));
        email.setCc(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.CC)));
        email.setBcc(getBccs(to, mimeMessage));
        email.setSubject(mimeMessage.getSubject());
        extractPart(mimeMessage, email);
        email.setReceivedDateTime(OffsetDateTime.now());
        return email;
    }

    private void extractPart(Part mimeMessage, Email email) throws IOException, MessagingException {
        Object content = mimeMessage.getContent();
        if (content instanceof String) {
            email.setMessage(content.toString());
        } else if (content instanceof InputStream) {
            Attachment e = new Attachment();
            e.setData(readAllBytes((InputStream) content));
            e.setName(mimeMessage.getFileName());
            email.getAttachments().add(e);
        } else if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                extractPart(bodyPart, email);
            }
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                    outputStream.write(buf, 0, readLen);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }

    private String addressesAsString(Address... addresses) {
        return Arrays.stream(addresses).map(Address::toString).collect(Collectors.joining(","));
    }

    /**
     * mimeMessage won't have BCC because this information not present in messageData.
     * BCC are only populated in SMTP TO header and not part of SMTP DATA. Looking up BBCs.
     */
    @SneakyThrows
    private String getBccs(String to, MimeMessage mimeMessage) {
        Collection<String> allAddresses = Arrays.stream(mimeMessage.getAllRecipients())
                .map(Address::toString)
                .collect(Collectors.toSet());
        return Arrays.stream(to.split(","))
                .filter(recipient -> !allAddresses.contains(recipient))
                .collect(Collectors.joining());
    }


}
