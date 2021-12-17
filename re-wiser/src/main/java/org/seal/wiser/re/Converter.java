package org.seal.wiser.re;

import lombok.SneakyThrows;
import org.seal.wiser.backend.EmailEntity;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class Converter {

    @SneakyThrows
    public EmailEntity convert(byte[] messageData, String to) {
        MimeMessage mimeMessage = new MimeMessage(
                Session.getDefaultInstance(new Properties()), new ByteArrayInputStream(messageData));
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setFrom(addressesAsString(mimeMessage.getFrom()));
        emailEntity.setTo(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.TO)));
        emailEntity.setCc(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.CC)));
        emailEntity.setBcc(getBccs(to, mimeMessage));
        emailEntity.setSubject(mimeMessage.getSubject());
        emailEntity.setMessage(
                mimeMessage.getContent() instanceof String
                        ? mimeMessage.getContent().toString()
                        : "unsupported");
        emailEntity.setReceivedDateTime(OffsetDateTime.now());
        return emailEntity;
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
