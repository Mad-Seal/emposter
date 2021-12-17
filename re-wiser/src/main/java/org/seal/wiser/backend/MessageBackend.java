package org.seal.wiser.backend;

import org.seal.wiser.re.ReWiserMessage;

import java.util.Collection;

public interface MessageBackend {

    void save(EmailEntity message);

    Collection<EmailEntity> getAll();

    void clear();

}
