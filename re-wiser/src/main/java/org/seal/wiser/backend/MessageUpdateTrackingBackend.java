package org.seal.wiser.backend;

import java.util.Collection;
import java.util.function.Consumer;

public class MessageUpdateTrackingBackend implements MessageBackend {

    private MessageBackend delegate;
    private Consumer<Email> onMessageCallback;

    public MessageUpdateTrackingBackend(MessageBackend delegate, Consumer<Email> onMessageCallback) {
        this.delegate = delegate;
        this.onMessageCallback = onMessageCallback;
    }

    @Override
    public void save(Email message) {
        delegate.save(message);
        onMessage(message);
    }

    private void onMessage(Email message) {
        onMessageCallback.accept(message);
    }

    @Override
    public Collection<Email> getAll() {
        return delegate.getAll();
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Attachment getAttachment(long id) {
        return delegate.getAttachment(id);
    }

    @Override
    public boolean canClear() {
        return delegate.canClear();
    }
}
