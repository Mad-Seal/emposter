package org.seal.wiser.test;

import org.seal.wiser.backend.EmailEntity;
import org.seal.wiser.backend.MessageBackend;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class DummyBackendConfig {

    //    @Bean
    public MessageBackend dummyBackend() {
        return new DummyBackend();
    }

    public static class DummyBackend implements MessageBackend {
        @Override
        public void save(EmailEntity message) {
        }

        @Override
        //TODO: more data to add
        public Collection<EmailEntity> getAll() {
            return Arrays.asList(
                    new EmailEntity(0, "from0", "to", "cc", "bcc", "subject", "message", OffsetDateTime.now(), Collections.emptyList()),
                    new EmailEntity(1, "from1", "to", "cc", "bcc", "subject", "message", OffsetDateTime.now(), Collections.emptyList())
            );
        }

        @Override
        public void clear() {
        }
    }

}
