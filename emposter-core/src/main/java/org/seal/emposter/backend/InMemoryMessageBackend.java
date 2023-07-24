package org.seal.emposter.backend;

/*-
 * #%L
 * emposter
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
