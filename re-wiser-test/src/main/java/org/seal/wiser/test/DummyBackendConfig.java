package org.seal.wiser.test;

import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.re.ReWiserMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class DummyBackendConfig {

    @Bean
    public MessageBackend dummyBackend() {
        return new DummyBackend();
    }

    public static class DummyBackend implements MessageBackend {
        @Override
        public void save(ReWiserMessage message) {
        }

        @Override
        //TODO: more data to add
        public Collection<ReWiserMessage> getAll() {
            return Arrays.asList(
                    new ReWiserMessage(null, "from", "to", "message".getBytes()),
                    new ReWiserMessage(null, "from", "to", "message".getBytes())
            );
        }

        @Override
        public void clear() {
        }
    }

}
