package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeSnippet;
import com.fleckinger.codesharingplatform.model.EmptyJsonResponse;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class WebController {

    private final CrudCodeRepository repository;


    @Autowired
    public WebController(CrudCodeRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/code/new", method = RequestMethod.GET)
    public String newCodePage(Model model) {
        model.addAttribute("codeSnippet", new CodeSnippet());
        return "newCodeForm";
    }

    @RequestMapping(value = "/code/new", method = RequestMethod.POST)
    @ResponseBody
    public EmptyJsonResponse newCodeSubmit(@ModelAttribute CodeSnippet codeSnippet, Model model) {
        model.addAttribute("codeSnippet", codeSnippet);
        if (codeSnippet.getTime() > 0) {
            codeSnippet.setTimeRestrictions(true);
        }
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setViewsRestrictions(true);
        }
        repository.save(codeSnippet);
        return new EmptyJsonResponse();
    }

    /**
     * @param id UUID
     * @return getCode_page.html with code
     * @throws ResponseStatusException 404 NOT FOUND
     */
    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    public String getCodeHtmlPage(Model model, @PathVariable UUID id) {

        CodeSnippet codeSnippet = repository.getCodeSnippetFromDB(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM HH:mm:ss");

        model.addAttribute("code", codeSnippet.getCode());
        model.addAttribute("time", codeSnippet.getUploadDate().format(formatter));
        model.addAttribute("hasTimeRestriction", codeSnippet.hasTimeRestrictions());
        model.addAttribute("hasViewRestriction", codeSnippet.hasViewsRestrictions());
        model.addAttribute("timeRestriction", codeSnippet.getTime());
        model.addAttribute("viewRestriction", codeSnippet.getViews());

        return "getCode_page";
    }

    @RequestMapping(value = "/code/latest", method = RequestMethod.GET)
    public String getLatestCodesHtmlPage(Model model) {
        List<CodeSnippet> latestCodes = repository
                .findFirst10ByTimeRestrictionsFalseAndViewsRestrictionsFalseOrderByUploadDateDesc()
                .orElse(new ArrayList<>());

        model.addAttribute("codesList", latestCodes);
        return "latestCodes_page";
    }

    @PostMapping()
    public void saveToDB(@ModelAttribute("code") CodeSnippet code) {
        repository.save(code);
    }
}