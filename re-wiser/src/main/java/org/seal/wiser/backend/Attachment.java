package org.seal.wiser.backend;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents email attachment.
 * Transferred between components of system.
 */
@Getter
@Setter
public class Attachment {
    private long id;
    private String name;
    private byte[] data;
}
