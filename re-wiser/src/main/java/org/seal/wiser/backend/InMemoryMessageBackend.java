package org.seal.wiser.backend;


import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This naive implementation of MessageBackend stores messages in memory.
 * There is no auto-cleanup or capacity limiting mechanism,
 * use accordingly if you expect big number of emails or big attachments.
 * <p>
 * Implementation does not sync state of internal storage in multi-instance deployment.
 * Depending on load balancer configuration
 * results of {@link #save(Email)} and {@link #clear()} might be inconsistent.
 * <p>
 * This implementation is more suitable for local usage rather than for running on environment.
 */
public class InMemoryMessageBackend implements MessageBackend {

    private AtomicLong sequence = new AtomicLong(0);
    private Collection<Email> messages = new CopyOnWriteArrayList<>();

    @Override
    public void save(Email message) {
        message.setId(sequence.getAndIncrement());
        message.getAttachments().forEach(attachment -> attachment.setId(sequence.getAndIncrement()));
        messages.add(message);
    }

    @Override
    public Collection<Email> getAll() {
        return messages;
    }

    @Override
    public void clear() {
        messages.clear();
    }

    @Override
    public Attachment getAttachment(long id) {
        return messages.stream()
                .map(Email::getAttachments)
                .flatMap(Collection::stream)
                .filter(attachment -> attachment.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean canClear() {
        return false;
    }
}
