package org.seal.wiser.web;

import org.seal.wiser.backend.Email;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReWiserMessageMapper {

    public ReWiserMessageDto toDto(Email reWiserMessage) {
        return new ReWiserMessageDto(
                reWiserMessage.getId(),
                reWiserMessage.getFrom(),
                reWiserMessage.getTo(),
                reWiserMessage.getCc(),
                reWiserMessage.getBcc(),
                reWiserMessage.getSubject(),
                reWiserMessage.getMessage(),
                reWiserMessage.getReceivedDateTime(),
                reWiserMessage.getAttachments().stream()
                        .map(attachment -> new AttachmentDto(attachment.getId(), attachment.getName()))
                        .collect(Collectors.toList())
        );
    }
}
