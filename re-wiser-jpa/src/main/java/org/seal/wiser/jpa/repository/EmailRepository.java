package org.seal.wiser.jpa.repository;

import org.seal.wiser.jpa.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface EmailRepository extends CrudRepository<EmailEntity, Long> {

    @Override
    @NonNull
    Collection<EmailEntity> findAll();
}
