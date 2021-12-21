package org.seal.wiser.jpa.backend;

import org.seal.wiser.backend.Attachment;
import org.seal.wiser.backend.Email;
import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.jpa.entity.AttachmentEntity;
import org.seal.wiser.jpa.entity.EmailEntity;
import org.seal.wiser.jpa.repository.WiserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class JpaMessageBackend implements MessageBackend {

    private WiserRepository wiserRepository;

    public JpaMessageBackend(WiserRepository wiserRepository) {
        this.wiserRepository = wiserRepository;
    }

    @Override
    public void save(Email email) {
        try {
            EmailEntity entity = new EmailEntity();
            entity.setSender(email.getFrom());
            entity.setTo(email.getTo());
            entity.setCc(email.getCc());
            entity.setBcc(email.getBcc());
            entity.setMessage(email.getMessage());
            entity.setSubject(email.getSubject());
            entity.setReceivedDateTime(email.getReceivedDateTime());
            entity.setAttachments(email.getAttachments().stream()
                    .map(attachment -> {
                        AttachmentEntity attachmentEntity = new AttachmentEntity();
                        attachmentEntity.setData(attachment.getData());
                        attachmentEntity.setName(attachment.getName());
                        return attachmentEntity;
                    }).collect(Collectors.toList()));
            wiserRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Email> getAll() {
        return wiserRepository.findAll()
                .stream().map(emailEntity -> {
                    Email email = new Email();
                    email.setId(emailEntity.getId());
                    email.setFrom(emailEntity.getSender());
                    email.setTo(emailEntity.getTo());
                    email.setCc(emailEntity.getCc());
                    email.setBcc(emailEntity.getBcc());
                    email.setMessage(emailEntity.getMessage());
                    email.setSubject(emailEntity.getSubject());
                    email.setReceivedDateTime(emailEntity.getReceivedDateTime());
                    email.setAttachments(emailEntity.getAttachments().stream()
                            .map(attachmentEntity -> {
                                Attachment attachment = new Attachment();
                                attachment.setId(attachmentEntity.getId());
                                attachment.setName(attachmentEntity.getName());
                                attachment.setData(attachmentEntity.getData());
                                return attachment;
                            })
                            .collect(Collectors.toList())
                    );
                    return email;
                }).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        wiserRepository.deleteAll();
    }
}
