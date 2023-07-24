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

@Getter
@Setter
@Entity
public class AttachmentEntity {
    @Id
    @SequenceGenerator(name = "attachment-sequence-generator", sequenceName = "emposter_attachment_sequence")
    @GeneratedValue(generator = "attachment-sequence-generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Lob
    private byte[] data;
}
