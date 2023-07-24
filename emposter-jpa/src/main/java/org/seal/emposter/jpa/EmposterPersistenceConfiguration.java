package org.seal.emposter.jpa;

/*-
 * #%L
 * emposter-jpa
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

import org.seal.emposter.backend.MessageBackend;
import org.seal.emposter.jpa.backend.JpaMessageBackend;
import org.seal.emposter.jpa.entity.EmailEntity;
import org.seal.emposter.jpa.repository.AttachmentRepository;
import org.seal.emposter.jpa.repository.EmailRepository;
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
        basePackages = "org.seal.emposter.jpa.repository",
        entityManagerFactoryRef = "emposterEntityManagerFactory",
        transactionManagerRef = "emposterTransactionManager"
)
public class EmposterPersistenceConfiguration {

    @Bean
    @ConfigurationProperties("emposter.datasource")
    public DataSourceProperties emposterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("emposter.datasource.configuration")
    public DataSource emposterDataSource() {
        DataSourceProperties emposterDataSourceProperties = emposterDataSourceProperties();
        return emposterDataSourceProperties
                .initializeDataSourceBuilder()
                .type(emposterDataSourceProperties.getType())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emposterEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            VendorProperties vendorProperties) {
        return builder
                .dataSource(emposterDataSource())
                .properties(vendorProperties.getProperties())
                .packages(EmailEntity.class)
                .build();
    }

    @Bean(name = "emposterTransactionManager")
    public PlatformTransactionManager emposterTransactionManager(
            @Qualifier("emposterEntityManagerFactory") EntityManagerFactory emposterEntityManagerFactory) {
        return new JpaTransactionManager(emposterEntityManagerFactory);
    }

    @Bean
    public MessageBackend persistentBackend(EmailRepository emailRepository, AttachmentRepository attachmentRepository) {
        return new JpaMessageBackend(emailRepository, attachmentRepository);
    }

    @Bean
    @ConfigurationProperties("emposter.orm.vendor")
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
