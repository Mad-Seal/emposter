package org.seal.wiser.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AttachmentEntity {
    @Id
    @SequenceGenerator(name = "attachment-sequence-generator", sequenceName = "wiser_attachment_sequence")
    @GeneratedValue(generator = "attachment-sequence-generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Lob
    private byte[] data;
}
