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
