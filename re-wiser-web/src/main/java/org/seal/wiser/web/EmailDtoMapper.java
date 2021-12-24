package org.seal.wiser.web;

import org.seal.wiser.backend.Email;
import org.seal.wiser.web.dto.AttachmentDto;
import org.seal.wiser.web.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmailDtoMapper {

    public EmailDto toDto(Email reWiserMessage) {
        return new EmailDto(
                reWiserMessage.getId(),
                reWiserMessage.getFrom(),
                reWiserMessage.getTo(),
                reWiserMessage.getCc(),
                reWiserMessage.getBcc(),
                reWiserMessage.getSubject(),
                reWiserMessage.getText(),
                reWiserMessage.getReceivedDateTime(),
                reWiserMessage.getAttachments().stream()
                        .map(attachment -> new AttachmentDto(attachment.getId(), attachment.getName()))
                        .collect(Collectors.toList())
        );
    }
}
