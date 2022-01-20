package org.seal.wiser.jpa;

/*-
 * #%L
 * re-wiser-jpa
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

import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.jpa.backend.JpaMessageBackend;
import org.seal.wiser.jpa.entity.EmailEntity;
import org.seal.wiser.jpa.repository.AttachmentRepository;
import org.seal.wiser.jpa.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Separate DataSource configuration to not interfere into Datasource of carrier application
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "org.seal.wiser.jpa.repository",
        entityManagerFactoryRef = "wiserEntityManagerFactory",
        transactionManagerRef = "wiserTransactionManager"
)
public class WiserPersistenceConfiguration {

    @Bean
    @ConfigurationProperties("wiser.datasource")
    public DataSourceProperties wiserDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("wiser.datasource.configuration")
    public DataSource wiserDataSource() {
        DataSourceProperties wiserDataSourceProperties = wiserDataSourceProperties();
        return wiserDataSourceProperties
                .initializeDataSourceBuilder()
                .type(wiserDataSourceProperties.getType())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean wiserEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            VendorProperties vendorProperties) {
        return builder
                .dataSource(wiserDataSource())
                .properties(vendorProperties.getProperties())
                .packages(EmailEntity.class)
                .build();
    }

    @Bean(name = "wiserTransactionManager")
    public PlatformTransactionManager wiserTransactionManager(
            @Qualifier("wiserEntityManagerFactory") EntityManagerFactory wiserEntityManagerFactory) {
        return new JpaTransactionManager(wiserEntityManagerFactory);
    }

    @Bean
    public MessageBackend persistentBackend(EmailRepository emailRepository, AttachmentRepository attachmentRepository) {
        return new JpaMessageBackend(emailRepository, attachmentRepository);
    }

    @Bean
    @ConfigurationProperties("wiser.orm.vendor")
    public VendorProperties vendorProperties() {
        return new VendorProperties();
    }

    public static class VendorProperties {
        private Map<String, String> properties;

        public Map<String, String> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }
    }

}
