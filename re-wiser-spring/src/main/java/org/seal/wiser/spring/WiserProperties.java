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
