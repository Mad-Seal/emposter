package org.seal.wiser.spring;

/*-
 * #%L
 * re-wiser-spring
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
        return new MessageUpdateTrackingBackend(
                new InMemoryMessageBackend(),
                (message) -> LoggerFactory.getLogger(WiserAutoconfiguration.class).info(message.toString())
        );
    }
}
