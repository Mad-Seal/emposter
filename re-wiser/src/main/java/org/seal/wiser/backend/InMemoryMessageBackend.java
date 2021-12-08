package org.seal.wiser.backend;


import org.seal.wiser.re.ReWiserMessage;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryMessageBackend implements MessageBackend {

    private Collection<ReWiserMessage> messages = new CopyOnWriteArrayList<>();

    @Override
    public void save(ReWiserMessage message) {
        messages.add(message);
    }

    @Override
    public Collection<ReWiserMessage> getAll() {
        return messages;
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
