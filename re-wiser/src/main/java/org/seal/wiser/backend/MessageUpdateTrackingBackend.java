package org.seal.wiser.backend;

import org.seal.wiser.re.ReWiserMessage;

import java.util.Collection;
import java.util.function.Consumer;

public class MessageUpdateTrackingBackend implements MessageBackend {

    private MessageBackend delegate;
    private Consumer<ReWiserMessage> onMessageCallback;

    public MessageUpdateTrackingBackend(MessageBackend delegate, Consumer<ReWiserMessage> onMessageCallback) {
        this.delegate = delegate;
        this.onMessageCallback = onMessageCallback;
    }

    @Override
    public void save(ReWiserMessage message) {
        delegate.save(message);
        onMessage(message);
    }

    private void onMessage(ReWiserMessage message) {
        onMessageCallback.accept(message);
    }

    @Override
    public Collection<ReWiserMessage> getAll() {
        return delegate.getAll();
    }

    @Override
    public void clear() {
        delegate.clear();
    }
}
