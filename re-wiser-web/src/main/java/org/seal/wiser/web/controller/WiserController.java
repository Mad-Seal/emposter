package org.seal.wiser.web.controller;

import org.seal.wiser.re.ReWiser;
import org.seal.wiser.web.dto.EmailDto;
import org.seal.wiser.web.EmailDtoMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wiser")
public class WiserController {

    private ReWiser reWiser;
    private EmailDtoMapper emailDtoMapper;

    public WiserController(ReWiser reWiser, EmailDtoMapper emailDtoMapper) {
        this.reWiser = reWiser;
        this.emailDtoMapper = emailDtoMapper;
    }

    @GetMapping("messages")
    public Collection<EmailDto> getMessages() {
        return reWiser.getMessages().stream()
                .map(emailDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("messages")
    public void clear() {
        reWiser.clear();
    }

    @GetMapping("messages/purgable")
    public boolean canPurge(){
        return reWiser.canClear();
    }

}
