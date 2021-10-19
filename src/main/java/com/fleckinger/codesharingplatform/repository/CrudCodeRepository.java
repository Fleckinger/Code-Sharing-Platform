package com.fleckinger.codesharingplatform.repository;

import com.fleckinger.codesharingplatform.model.CodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrudCodeRepository extends CrudRepository<CodeEntity, Long> {
    Optional<CodeEntity> findTopByOrderByIdDesc();
    Optional<List<CodeEntity>> findFirst10ByOrderByUploadDateDesc();

}
