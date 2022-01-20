package org.seal.wiser.re;

/*-
 * #%L
 * re-wiser
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

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import lombok.SneakyThrows;
import org.seal.wiser.backend.Attachment;
import org.seal.wiser.backend.Email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Converter to transform raw mime message (from SMTP DATA fragment)
 * and comma separated list of recipients (from SMTP TO header) into {@link Email} for further internal usage.
 */
public class EmailConverter {

    @SneakyThrows
    public Email convert(byte[] rawMimeMessage, String to) {
        MimeMessage mimeMessage = new MimeMessage(
                Session.getDefaultInstance(new Properties()), new ByteArrayInputStream(rawMimeMessage));
        Email email = new Email();
        email.setFrom(addressesAsString(mimeMessage.getFrom()));
        email.setTo(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.TO)));
        email.setCc(addressesAsString(mimeMessage.getRecipients(Message.RecipientType.CC)));
        email.setBcc(getBccs(to, mimeMessage));
        email.setSubject(mimeMessage.getSubject());
        extractParts(mimeMessage, email);
        email.setReceivedDateTime(OffsetDateTime.now());
        return email;
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

    /**
     * Traverses mime message parts and extracts email text and attachments, if any.
     */
    @SneakyThrows
    private void extractParts(Part mimeMessagePart, Email email) {
        Object content = mimeMessagePart.getContent();
        if (content instanceof String) {
            email.setText(content.toString());
        } else if (content instanceof InputStream) {
            Attachment attachment = new Attachment();
            attachment.setData(readAllBytes((InputStream) content));
            attachment.setName(mimeMessagePart.getFileName());
            email.getAttachments().add(attachment);
        } else if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                extractParts(bodyPart, email);
            }
        }
    }

    private byte[] readAllBytes(InputStream inputStream) {
        int bufferLength = 4 * 0x400; // 4KB
        byte[] buffer = new byte[bufferLength];
        int readLength;
        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLength = inputStream.read(buffer, 0, bufferLength)) != -1)
                    outputStream.write(buffer, 0, readLength);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            handleInputException(inputStream, e);
            throw new RuntimeException(e);
        }
    }

    private void handleInputException(InputStream inputStream, IOException e) {
        try {
            inputStream.close();
        } catch (IOException ise) {
            e.addSuppressed(ise);
        }
    }

    private String addressesAsString(Address... addresses) {
        return Arrays.stream(addresses).map(Address::toString).collect(Collectors.joining(","));
    }


}
