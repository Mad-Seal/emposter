package org.seal.wiser.backend;

/*-
 * #%L
 * re-wiser
 * %%
 * Copyright (C) 2022 authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Decorator for MessageBackend.
 * Triggers provided callback after saving message.
 */
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
