package org.seal.wiser.backend;

import java.util.Collection;

public interface MessageBackend {

    void save(Email message);

    Collection<Email> getAll();

    void clear();

}
