package org.seal.emposter.web;

/*-
 * #%L
 * emposter-web
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

import org.seal.emposter.backend.Email;
import org.seal.emposter.web.dto.AttachmentDto;
import org.seal.emposter.web.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmailDtoMapper {

    public EmailDto toDto(Email email) {
        return new EmailDto(
                email.getId(),
                email.getFrom(),
                email.getTo(),
                email.getCc(),
                email.getBcc(),
                email.getSubject(),
                email.getText(),
                email.getReceivedDateTime(),
                email.getAttachments().stream()
                        .map(attachment -> new AttachmentDto(attachment.getId(), attachment.getName()))
                        .collect(Collectors.toList())
        );
    }
}
