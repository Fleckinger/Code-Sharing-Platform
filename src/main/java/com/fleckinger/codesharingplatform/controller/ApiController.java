package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeSnippet;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Controller
public class ApiController {

    private final CrudCodeRepository repository;

    @Autowired
    public ApiController(CrudCodeRepository repository) {
        this.repository = repository;
    }

    /**
     * add new codeSnippet to DB, set restrictions flags if access time or/and number of views more than 0
     * @return Map with key - "id", value - id value. Return this map as response json
     */
    @RequestMapping(value = "/api/code/new", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> newCode(@RequestBody CodeSnippet codeSnippet) {

        if (codeSnippet.getTime() > 0) {
            codeSnippet.setTimeRestrictions(true);
            codeSnippet.setAccessExpireDate(codeSnippet
                    .getUploadDate()
                    .plus(codeSnippet.getTime(), ChronoUnit.SECONDS));

        }
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setViewsRestrictions(true);
        }
        repository.save(codeSnippet);

        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(codeSnippet.getId()));

        return response;
    }

    /**
     * @param id UUID
     * @return CodeSnippet from DB as response json
     * @throws ResponseStatusException 404 NOT FOUND
     */
    @RequestMapping(value = "/api/code/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CodeSnippet getCode(HttpServletResponse response, @PathVariable UUID id) {

        CodeSnippet codeSnippet = repository.getCodeSnippetFromDB(id);

        response.addHeader("Content-Type", "text/html");

        return codeSnippet;
    }

    /**
     * @return last 10 or less codeSnippets that has no restrictions, or return empty list
     */
    @RequestMapping(value = "/api/code/latest", method = RequestMethod.GET)
    @ResponseBody
    public List<CodeSnippet> getLatestCodes() {
        return repository
                .findFirst10ByTimeRestrictionsFalseAndViewsRestrictionsFalseOrderByUploadDateDesc()
                .orElse(new ArrayList<>());
    }

}