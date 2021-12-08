package org.seal.wiser.web;

import org.seal.wiser.re.ReWiser;
import org.seal.wiser.re.ReWiserMessage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("wiser")
public class WiserController {

    private ReWiser reWiser;

    public WiserController(ReWiser reWiser) {
        this.reWiser = reWiser;
    }

    @GetMapping("messages")
    public Collection<ReWiserMessage> getMessages() {
        return reWiser.getMessages();
    }

    @DeleteMapping("messages")
    public void clear() {
        reWiser.clear();
    }

}
