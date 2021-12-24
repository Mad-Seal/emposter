package org.seal.wiser.spring;

import org.seal.wiser.backend.MessageBackend;
import org.seal.wiser.re.ReWiser;
import org.subethamail.smtp.server.SMTPServer;

public class ReWiserFactory {

    public ReWiser wiser(WiserProperties wiserProperties, MessageBackend messageBackend) {
        SMTPServer.Builder serverBuilder = createSmtpSeverBuilder(wiserProperties);
        return ReWiser.create(serverBuilder, messageBackend);
    }

    private SMTPServer.Builder createSmtpSeverBuilder(WiserProperties wiserProperties) {
        return SMTPServer.port(wiserProperties.getPort());
    }

}
