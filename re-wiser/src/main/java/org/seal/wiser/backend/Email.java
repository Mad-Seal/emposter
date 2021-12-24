package org.seal.wiser.backend;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private long id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
    private OffsetDateTime receivedDateTime;
    private Collection<Attachment> attachments = new LinkedList<>();
}
