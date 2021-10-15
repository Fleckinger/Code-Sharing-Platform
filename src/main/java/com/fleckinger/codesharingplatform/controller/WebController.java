package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeEntity;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;


@Controller
public class WebController {
    private static final String DATE_FORMAT = "yyyy/dd/MM HH:mm:ss";

    @Autowired
    CrudCodeRepository repository;

    /**
     * @return getCode_page.html with last or default CodeEntity
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String getCodeHtmlPage(Model model) {

        //get last codeEntity from DB or create a default codeEntity
        CodeEntity codeEntity = repository.findTopByOrderByIdDesc().orElse(new CodeEntity());
        String code = codeEntity.getCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String time = codeEntity.getUploadDate().format(formatter);
        model.addAttribute("code", code);
        model.addAttribute("time", time);
        return "getCode_page";
    }

    @RequestMapping(value = "/code/new", method = RequestMethod.GET)
    public String newCodePage(Model model) {
        model.addAttribute("code", new CodeEntity());
        return "newCodeForm";
    }

    @RequestMapping(value = "/code/new", method = RequestMethod.POST)
    public EmptyJsonResponse newCodeSubmit(@ModelAttribute CodeEntity codeEntity, Model model) {
        model.addAttribute("code", codeEntity);
        repository.save(codeEntity);
        return new EmptyJsonResponse();
    }

    @PostMapping()
    public void create(@ModelAttribute("code") CodeEntity code) {
        repository.save(code);
    }
}
