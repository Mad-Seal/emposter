package org.seal.wiser.backend;


import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMessageBackend implements MessageBackend {

    private AtomicLong sequence = new AtomicLong(0);
    private Collection<EmailEntity> messages = new CopyOnWriteArrayList<>();

    @Override
    public void save(EmailEntity message) {
        message.setId(sequence.getAndIncrement());
        messages.add(message);
    }

    @Override
    public Collection<EmailEntity> getAll() {
        return messages;
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
