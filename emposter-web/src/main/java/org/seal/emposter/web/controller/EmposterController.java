package org.seal.emposter.web.controller;

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

import org.seal.emposter.smtp.Emposter;
import org.seal.emposter.web.dto.EmailDto;
import org.seal.emposter.web.EmailDtoMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("emposter")
public class EmposterController {

    private Emposter emposter;
    private EmailDtoMapper emailDtoMapper;

    public EmposterController(Emposter emposter, EmailDtoMapper emailDtoMapper) {
        this.emposter = emposter;
        this.emailDtoMapper = emailDtoMapper;
    }

    @GetMapping("messages")
    public Collection<EmailDto> getMessages() {
        return emposter.getMessages().stream()
                .map(emailDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("messages")
    public void clear() {
        emposter.clear();
    }

    @GetMapping("messages/purgable")
    public boolean canPurge() {
        return emposter.canClear();
    }

}
