package org.seal.wiser.backend;


import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

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
