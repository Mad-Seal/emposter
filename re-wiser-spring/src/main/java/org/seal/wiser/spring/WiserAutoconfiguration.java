package org.seal.wiser.spring;

import org.seal.wiser.backend.InMemoryMessageBackend;
import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.backend.MessageUpdateTrackingBackend;
import org.seal.wiser.re.ReWiser;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(WiserLifecycleHook.class)
@ConditionalOnProperty(name = "spring.wiser.enabled", matchIfMissing = true)
public class WiserAutoconfiguration {

    @Bean
    public WiserLifecycleHook wiserBean(ReWiser wiser) {
        return new WiserLifecycleHook(wiser);
    }

    @Bean
    public ReWiser wiser(MessageBackend messageBackendBean) {
        return smtpServiceFactory().wiser(wiserProperties(), messageBackendBean);
    }

    @Bean
    public ReWiserFactory smtpServiceFactory() {
        return new ReWiserFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.wiser")
    public WiserProperties wiserProperties() {
        return new WiserProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageBackend messageBackend() {
        return new MessageUpdateTrackingBackend(new InMemoryMessageBackend(), (message) -> {
            LoggerFactory.getLogger(WiserAutoconfiguration.class).info(message.toString());
        });
    }
}
