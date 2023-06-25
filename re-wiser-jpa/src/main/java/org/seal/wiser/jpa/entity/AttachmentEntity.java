package org.seal.wiser.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class AttachmentEntity {
    @Id
    @SequenceGenerator(name = "attachment-sequence-generator", sequenceName = "attachment_sequence")
    @GeneratedValue(generator = "attachment-sequence-generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Lob
    private byte[] data;
}
