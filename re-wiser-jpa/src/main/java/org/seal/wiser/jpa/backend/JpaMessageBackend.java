package org.seal.wiser.jpa.backend;

import org.seal.wiser.backend.Attachment;
import org.seal.wiser.backend.Email;
import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.jpa.entity.AttachmentEntity;
import org.seal.wiser.jpa.entity.EmailEntity;
import org.seal.wiser.jpa.repository.AttachmentRepository;
import org.seal.wiser.jpa.repository.EmailRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class JpaMessageBackend implements MessageBackend {

    private EmailRepository emailRepository;
    private AttachmentRepository attachmentRepository;

    public JpaMessageBackend(EmailRepository emailRepository, AttachmentRepository attachmentRepository) {
        this.emailRepository = emailRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public void save(Email email) {
        try {
            EmailEntity entity = new EmailEntity();
            entity.setSender(email.getFrom());
            entity.setTo(email.getTo());
            entity.setCc(email.getCc());
            entity.setBcc(email.getBcc());
            entity.setMessage(email.getText());
            entity.setSubject(email.getSubject());
            entity.setReceivedDateTime(email.getReceivedDateTime());
            entity.setAttachments(email.getAttachments().stream()
                    .map(attachment -> {
                        AttachmentEntity attachmentEntity = new AttachmentEntity();
                        attachmentEntity.setData(attachment.getData());
                        attachmentEntity.setName(attachment.getName());
                        return attachmentEntity;
                    }).collect(Collectors.toList()));
            emailRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Email> getAll() {
        return emailRepository.findAll()
                .stream().map(emailEntity -> {
                    Email email = new Email();
                    email.setId(emailEntity.getId());
                    email.setFrom(emailEntity.getSender());
                    email.setTo(emailEntity.getTo());
                    email.setCc(emailEntity.getCc());
                    email.setBcc(emailEntity.getBcc());
                    email.setText(emailEntity.getMessage());
                    email.setSubject(emailEntity.getSubject());
                    email.setReceivedDateTime(emailEntity.getReceivedDateTime());
                    email.setAttachments(emailEntity.getAttachments().stream()
                            .map(attachmentEntity -> {
                                Attachment attachment = new Attachment();
                                attachment.setId(attachmentEntity.getId());
                                attachment.setName(attachmentEntity.getName());
                                return attachment;
                            })
                            .collect(Collectors.toList())
                    );
                    return email;
                }).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        emailRepository.deleteAll();
    }

    @Override
    public Attachment getAttachment(long id) {
        return attachmentRepository.findById(id)
                .map(attachmentEntity -> {
                    Attachment attachment = new Attachment();
                    attachment.setData(attachmentEntity.getData());
                    attachment.setName(attachmentEntity.getName());
                    return attachment;
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean canClear() {
        return true;
    }
}
