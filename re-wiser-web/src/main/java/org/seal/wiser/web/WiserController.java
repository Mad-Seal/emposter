package org.seal.wiser.web;

import org.seal.wiser.re.ReWiser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wiser")
public class WiserController {

    private ReWiser reWiser;

    public WiserController(ReWiser reWiser) {
        this.reWiser = reWiser;
    }

    @GetMapping("messages")
    //FIXME: move to mapper
    public Collection<ReWiserMessageDto> getMessages() {
        long[] id = {Long.MAX_VALUE};
        return reWiser.getMessages().stream()
                .map(m -> new ReWiserMessageDto(id[0]--,
                        "from",
                        "to",
                        "subject",
                        m.toString(),
                        OffsetDateTime.now(),
                        Collections.singletonList(new Attachment(-0, "important file"))))
                .collect(Collectors.toList());
    }

    @DeleteMapping("messages")
    public void clear() {
        reWiser.clear();
    }

}
