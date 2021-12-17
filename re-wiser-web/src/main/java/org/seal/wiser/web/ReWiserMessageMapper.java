package org.seal.wiser.web;

import org.seal.wiser.backend.EmailEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ReWiserMessageMapper {

    public ReWiserMessageDto toDto(EmailEntity reWiserMessage) {
        return new ReWiserMessageDto(
                reWiserMessage.getId(),
                reWiserMessage.getFrom(),
                reWiserMessage.getTo(),
                reWiserMessage.getCc(),
                reWiserMessage.getBcc(),
                reWiserMessage.getSubject(),
                reWiserMessage.getMessage(),
                reWiserMessage.getReceivedDateTime(),
                Collections.singletonList(new AttachmentDto(-0, "important file")));
    }
}
