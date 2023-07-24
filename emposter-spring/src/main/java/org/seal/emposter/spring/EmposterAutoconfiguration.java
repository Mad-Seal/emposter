package org.seal.emposter.spring;

/*-
 * #%L
 * emposter-spring
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

import org.seal.emposter.backend.InMemoryMessageBackend;
import org.seal.emposter.backend.MessageBackend;
import org.seal.emposter.backend.MessageUpdateTrackingBackend;
import org.seal.emposter.smtp.Emposter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(EmposterLifecycleHook.class)
@ConditionalOnProperty(name = "spring.emposter.enabled", matchIfMissing = true)
public class EmposterAutoconfiguration {

    @Bean
    public EmposterLifecycleHook emposterHook(Emposter emposter) {
        return new EmposterLifecycleHook(emposter);
    }

    @Bean
    public Emposter emposter(MessageBackend messageBackendBean) {
        return smtpServiceFactory().emposter(emposterProperties(), messageBackendBean);
    }

    @Bean
    public EmposterFactory smtpServiceFactory() {
        return new EmposterFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.emposter")
    public EmposterProperties emposterProperties() {
        return new EmposterProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageBackend messageBackend() {
        return new MessageUpdateTrackingBackend(
                new InMemoryMessageBackend(),
                (message) -> LoggerFactory.getLogger(EmposterAutoconfiguration.class).info(message.toString())
        );
    }
}
