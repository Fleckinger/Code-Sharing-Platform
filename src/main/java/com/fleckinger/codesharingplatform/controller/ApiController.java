package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeEntity;
import com.fleckinger.codesharingplatform.model.EmptyJsonResponse;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;



@Controller
public class ApiController {
    private static final String DATE_FORMAT = "yyyy/dd/MM HH:mm:ss";

    @Autowired
    CrudCodeRepository repository;

    /**
     * @return map with last CodeEntity from DB or default CodeEntity, with formatted date
     */
    @RequestMapping(value = "/api/code", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getCode(HttpServletResponse response) {
        //get last codeEntity from DB or create a default codeEntity
        CodeEntity codeEntity = repository.findTopByOrderByIdDesc().orElse(new CodeEntity());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String formattedDate = codeEntity.getUploadDate().format(formatter);

        Map<String, String> map = new HashMap<>();

        map.put("code", codeEntity.getCode());
        map.put("date", formattedDate);
        response.addHeader("Content-Type", "text/html");
        return map;
    }

    /**
     * add new entity with passed code to db
     * @return empty json
     */
    @RequestMapping(value = "/api/code/new", method = RequestMethod.POST)
    @ResponseBody
    public EmptyJsonResponse newCode(@RequestBody CodeEntity codeEntity) {
        repository.save(codeEntity);
        return new EmptyJsonResponse();
    }
}