package org.seal.wiser.jpa;

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

import javax.persistence.EntityManagerFactory;
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
