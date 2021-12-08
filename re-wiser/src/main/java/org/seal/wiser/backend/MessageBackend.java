package org.seal.wiser.backend;

import org.seal.wiser.re.ReWiserMessage;

import java.util.Collection;

public interface MessageBackend {

    void save(ReWiserMessage message);

    Collection<ReWiserMessage> getAll();

    void clear();

}
