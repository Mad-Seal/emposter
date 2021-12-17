package org.seal.wiser.backend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentEntity {
    private long id;
    private String name;
    private byte[] data;
}
