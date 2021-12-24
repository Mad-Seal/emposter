package org.seal.wiser.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
public class EmailEntity {
    @Id
    @SequenceGenerator(name = "email-sequence-generator", sequenceName = "wiser_email_sequence")
    @GeneratedValue(generator = "email-sequence-generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String sender;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
    private OffsetDateTime receivedDateTime;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<AttachmentEntity> attachments = Collections.emptyList();
}
