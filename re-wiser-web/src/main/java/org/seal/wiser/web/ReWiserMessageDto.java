package org.seal.wiser.web;

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
public class ReWiserMessageDto {
    private final long id;
    private final String from;
    private final String to;
    private final String subject;
    private final String message;
    private final OffsetDateTime date;
    private Collection<Attachment> attachments = Collections.emptyList();
}
