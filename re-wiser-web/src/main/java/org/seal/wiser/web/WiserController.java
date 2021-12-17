package org.seal.wiser.web;

import org.seal.wiser.re.ReWiser;
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
    private ReWiserMessageMapper reWiserMessageMapper;

    public WiserController(ReWiser reWiser, ReWiserMessageMapper reWiserMessageMapper) {
        this.reWiser = reWiser;
        this.reWiserMessageMapper = reWiserMessageMapper;
    }

    @GetMapping("messages")
    public Collection<ReWiserMessageDto> getMessages() {
        return reWiser.getMessages().stream()
                .map(reWiserMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("messages")
    public void clear() {
        reWiser.clear();
    }

}
