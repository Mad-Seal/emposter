package org.seal.wiser.spring;

import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.re.ReWiser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.subethamail.smtp.server.SMTPServer;

public class SmtpServiceFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public ReWiser wiser(WiserProperties wiserProperties, MessageBackend messageBackend) {
        SMTPServer.Builder serverBuilder = createSmtpSeverBuilder(wiserProperties);
        return ReWiser.create(serverBuilder, messageBackend);
    }

    private SMTPServer.Builder createSmtpSeverBuilder(WiserProperties wiserProperties) {
        SMTPServer.Builder builder = SMTPServer
                .port(wiserProperties.getPort());
/*
        if (wiserProperties.getSslSocketCreatorBeanName() != null && wiserProperties.getSslSocketFactoryBeanName() != null) {
            throw new IllegalArgumentException();
        }
        if (wiserProperties.getSslSocketCreatorBeanName() != null) {
            builder.startTlsSocketFactory((SSLSocketCreator) getBean(wiserProperties.getSslSocketCreatorBeanName()));
        } else if (wiserProperties.getSslSocketFactoryBeanName() != null) {
            builder.startTlsSocketFactory(
                    (SSLContext) getBean(wiserProperties.getSslSocketFactoryBeanName()),
                    wiserProperties.isRequireClientCertificate());
        }

        if (wiserProperties.getAuthenticationHandlerBeanName() != null) {
            builder.authenticationHandlerFactory((AuthenticationHandlerFactory) getBean(wiserProperties.getAuthenticationHandlerBeanName()));
        }

        if (wiserProperties.getMessageHandlerBeanName() != null) {
            builder.messageHandlerFactory((MessageHandlerFactory) getBean(wiserProperties.getMessageHandlerBeanName()));
        }

        if (wiserProperties.getExecutorServiceBeanName() != null) {
            builder.executorService((ExecutorService) getBean(wiserProperties.getExecutorServiceBeanName()));
        }

        wiserProperties.getHostname().ifPresent(builder::hostName);

        return builder
                .enableTLS(wiserProperties.isEnableTls())
                .requireTLS(wiserProperties.isRequireTsl())
                .hideTLS(wiserProperties.isHideTsl())
                .connectionTimeout(wiserProperties.getTimeout(), TimeUnit.SECONDS)
                .backlog(wiserProperties.getBacklog())
                .bindAddress(wiserProperties.getHost())
                .maxMessageSize(wiserProperties.getMaxMessageSize())
                .maxConnections(wiserProperties.getMaxConnections())
                .maxRecipients(wiserProperties.getMaxRecipients())
                ;*/
        return builder;
    }

    private Object getBean(String beanName) {
        if (beanName == null) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
