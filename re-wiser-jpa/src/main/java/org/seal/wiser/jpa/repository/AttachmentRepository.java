package org.seal.wiser.jpa.repository;

import org.seal.wiser.jpa.entity.AttachmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<AttachmentEntity, Long> {
}
