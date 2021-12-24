package org.seal.wiser.web.controller;

import org.seal.wiser.backend.Attachment;
import org.seal.wiser.backend.MessageBackend;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attachment")
public class AttachmentController {

    private MessageBackend messageBackend;

    public AttachmentController(MessageBackend messageBackend) {
        this.messageBackend = messageBackend;
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getAttachment(@PathVariable long id) {
        Attachment attachment = messageBackend.getAttachment(id);
        Resource resource = new ByteArrayResource(attachment.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .contentLength(attachment.getData().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
