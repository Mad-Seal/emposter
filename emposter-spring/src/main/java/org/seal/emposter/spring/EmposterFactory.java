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

import org.seal.emposter.backend.MessageBackend;
import org.seal.emposter.smtp.Emposter;
import org.subethamail.smtp.server.SMTPServer;

public class EmposterFactory {

    public Emposter emposter(EmposterProperties emposterProperties, MessageBackend messageBackend) {
        SMTPServer.Builder serverBuilder = createSmtpSeverBuilder(emposterProperties);
        return Emposter.create(serverBuilder, messageBackend);
    }

    private SMTPServer.Builder createSmtpSeverBuilder(EmposterProperties emposterProperties) {
        return SMTPServer.port(emposterProperties.getPort());
    }

}
