package org.seal.wiser.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private final long id;
    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;
    private final String message;
    private final OffsetDateTime receivedDateTime;
    private Collection<AttachmentDto> attachments = Collections.emptyList();
}
