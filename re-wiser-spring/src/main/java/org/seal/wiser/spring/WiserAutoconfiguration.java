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
@ConditionalOnMissingBean(WiserBean.class)
@ConditionalOnProperty(name = "spring.wiser.enabled", matchIfMissing = true)
public class WiserAutoconfiguration {

    @Bean
    public WiserBean wiserBean(ReWiser wiser) {
        return new WiserBean(wiser);
    }

    @Bean
    public ReWiser wiser() {
        return smtpServiceFactory().wiser(wiserProperties(), messageBackend());
    }

    @Bean
    public SmtpServiceFactory smtpServiceFactory() {
        return new SmtpServiceFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.wiser")
    public WiserProperties wiserProperties() {
        return new WiserProperties();
    }

    @Bean
    public MessageBackend messageBackend() {
        return new MessageUpdateTrackingBackend(new InMemoryMessageBackend(), (message) -> {
            LoggerFactory.getLogger(WiserAutoconfiguration.class).info(message.toString());
        });
    }
}
