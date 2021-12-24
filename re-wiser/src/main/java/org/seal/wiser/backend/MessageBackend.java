package org.seal.wiser.backend;

import java.util.Collection;

/**
 * Interface describes possible operations of MessageBackend.
 */
public interface MessageBackend {

    /**
     * Saves message. This is called by {@link org.seal.wiser.re.ReWiser} when message arrives.
     */
    void save(Email message);

    Collection<Email> getAll();

    /**
     * Clears all messages.
     * This is can be invoked even if {@link #canClear()} returns false.
     */
    void clear();

    Attachment getAttachment(long id);

    /**
     * This indicates whether {@link MessageBackend} fully supports clearing of messages.
     */
    boolean canClear();
}
