package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeEntity;
import com.fleckinger.codesharingplatform.model.EmptyJsonResponse;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class WebController {
    private static final String DATE_FORMAT = "yyyy/dd/MM HH:mm:ss";

    CrudCodeRepository repository;

    @Autowired
    public WebController(CrudCodeRepository repository) {
        this.repository = repository;
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

    /**
     * @return getCode_page.html with last or default CodeEntity
     */
    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    public String getCodeHtmlPage(Model model, @PathVariable long id) {

        //get  codeEntity from DB or create a default codeEntity
        CodeEntity codeEntity = repository.findById(id).orElse(new CodeEntity());
        String code = codeEntity.getCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String time = codeEntity.getUploadDate().format(formatter);
        model.addAttribute("code", code);
        model.addAttribute("time", time);
        return "getCode_page";
    }

    @RequestMapping(value = "/code/latest", method = RequestMethod.GET)
    public String getLatestCodesHtmlPage(Model model) {
        List<CodeEntity> latestCodes = repository.findFirst10ByOrderByUploadDateDesc().orElse(new ArrayList<>());
        model.addAttribute("codesList", latestCodes);
        /*List<CodeEntity> latestCodes = List.of(new CodeEntity("код1"),new CodeEntity("код2"), new CodeEntity("код3"));
        model.addAttribute("codeList", latestCodes);*/
        return "latestCodes_page";
    }

    @PostMapping()
    public void save(@ModelAttribute("code") CodeEntity code) {
        repository.save(code);
    }
}
