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

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Represents email.
 * Transferred between components of system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private long id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
    private OffsetDateTime receivedDateTime;
    private Collection<Attachment> attachments = new LinkedList<>();
}
