package org.seal.emposter.jpa.entity;

/*-
 * #%L
 * emposter-jpa
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

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "email")
public class EmailEntity {
    @Id
    @SequenceGenerator(name = "email-sequence-generator", sequenceName = "emposter_email_sequence")
    @GeneratedValue(generator = "email-sequence-generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String sender;
    @Column(name = "recipient")
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
    private OffsetDateTime receivedDateTime;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<AttachmentEntity> attachments = Collections.emptyList();
}
