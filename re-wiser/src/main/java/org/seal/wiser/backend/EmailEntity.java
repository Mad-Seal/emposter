package org.seal.wiser.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {
    private long id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    private OffsetDateTime receivedDateTime;
    private Collection<AttachmentEntity> attachments = Collections.emptyList();
}
