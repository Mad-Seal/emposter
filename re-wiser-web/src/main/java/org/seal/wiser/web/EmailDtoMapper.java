package org.seal.wiser.web;

/*-
 * #%L
 * re-wiser-web
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

import org.seal.wiser.backend.Email;
import org.seal.wiser.web.dto.AttachmentDto;
import org.seal.wiser.web.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmailDtoMapper {

    public EmailDto toDto(Email reWiserMessage) {
        return new EmailDto(
                reWiserMessage.getId(),
                reWiserMessage.getFrom(),
                reWiserMessage.getTo(),
                reWiserMessage.getCc(),
                reWiserMessage.getBcc(),
                reWiserMessage.getSubject(),
                reWiserMessage.getText(),
                reWiserMessage.getReceivedDateTime(),
                reWiserMessage.getAttachments().stream()
                        .map(attachment -> new AttachmentDto(attachment.getId(), attachment.getName()))
                        .collect(Collectors.toList())
        );
    }
}
