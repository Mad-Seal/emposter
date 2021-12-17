package org.seal.wiser.backend;

import org.seal.wiser.re.ReWiserMessage;

import java.util.Collection;
import java.util.function.Consumer;

public class MessageUpdateTrackingBackend implements MessageBackend {

    private MessageBackend delegate;
    private Consumer<EmailEntity> onMessageCallback;

    public MessageUpdateTrackingBackend(MessageBackend delegate, Consumer<EmailEntity> onMessageCallback) {
        this.delegate = delegate;
        this.onMessageCallback = onMessageCallback;
    }

    @Override
    public void save(EmailEntity message) {
        delegate.save(message);
        onMessage(message);
    }

    private void onMessage(EmailEntity message) {
        onMessageCallback.accept(message);
    }

    @Override
    public Collection<EmailEntity> getAll() {
        return delegate.getAll();
    }

    @Override
    public void clear() {
        delegate.clear();
    }
}
