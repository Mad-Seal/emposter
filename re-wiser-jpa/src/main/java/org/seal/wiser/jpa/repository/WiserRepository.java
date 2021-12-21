package org.seal.wiser.jpa.repository;

import org.seal.wiser.jpa.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface WiserRepository extends CrudRepository<EmailEntity, Long> {

    @Override
    Collection<EmailEntity> findAll();
}
