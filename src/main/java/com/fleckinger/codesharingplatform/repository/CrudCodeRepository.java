package com.fleckinger.codesharingplatform.repository;

import com.fleckinger.codesharingplatform.model.CodeSnippet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrudCodeRepository extends CrudRepository<CodeSnippet, Long> {

    Optional<List<CodeSnippet>> findFirst10ByTimeRestrictionsFalseAndViewsRestrictionsFalseOrderByUploadDateDesc();

    Optional<CodeSnippet> findById(UUID id);

    default CodeSnippet getCodeSnippetFromDB(UUID id) {

        Optional<CodeSnippet> codeSnippetOptional = findById(id);
        CodeSnippet codeSnippet;
        LocalDateTime now = LocalDateTime.now();

        if (codeSnippetOptional.isPresent()) {
            codeSnippet = codeSnippetOptional.get();

            if (codeSnippet.hasTimeRestrictions() && now.isAfter(codeSnippet.getAccessExpireDate())
                    || codeSnippet.hasViewsRestrictions() && codeSnippet.getViews() < 1) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            if (codeSnippet.hasTimeRestrictions()) {
                long timeLeft = ChronoUnit.SECONDS.between(now, codeSnippet.getAccessExpireDate());
                codeSnippet.setTime((int) timeLeft);
                save(codeSnippet);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (codeSnippet.hasViewsRestrictions()) {
            codeSnippet.setViews(codeSnippet.getViews() - 1);
            save(codeSnippet);
        }

        return codeSnippet;
    }
}
