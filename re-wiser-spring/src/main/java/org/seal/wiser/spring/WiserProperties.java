package org.seal.wiser.spring;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WiserProperties {
    private int port = 25;
    /*
    Only useful properties for now.
    Unsupported properties:
    private int timeout = 1000;
    private String authenticationHandlerBeanName;
    private int backlog = 100;
    private Optional<InetAddress> host = Optional.empty();
    private boolean requireTsl;
    private boolean hideTsl;
    private Optional<String> hostname = Optional.empty();
    private int maxMessageSize;
    private int maxConnections;
    private int maxRecipients;
    private String messageHandlerBeanName;
    private String executorServiceBeanName;
    private String sslSocketFactoryBeanName;
    private String fromAddressValidatorBeanName;
    private String sslSocketCreatorBeanName;
    private boolean requireClientCertificate;
    private boolean enableTls;
    */

}
