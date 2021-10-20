package com.fleckinger.codesharingplatform.controller;

import com.fleckinger.codesharingplatform.model.CodeEntity;
import com.fleckinger.codesharingplatform.repository.CrudCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ApiController {

    private final CrudCodeRepository repository;

    @Autowired
    public ApiController(CrudCodeRepository repository) {
        this.repository = repository;
    }

    /**
     * add new entity with passed code to db
     *
     * @return json with one field "code"
     */
    @RequestMapping(value = "/api/code/new", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> newCode(@RequestBody CodeEntity codeEntity) {
        Map<String, String> response = new HashMap<>();
        repository.save(codeEntity);
        response.put("id", String.valueOf(codeEntity.getId()));
        return response;
    }

    /**
     * @return map with last CodeEntity from DB or default CodeEntity, with formatted date
     */
    @RequestMapping(value = "/api/code/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getCode(HttpServletResponse response, @PathVariable long id) {
        //get codeEntity from DB or create a default codeEntity
        CodeEntity codeEntity = repository.findById(id).orElse(new CodeEntity());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM HH:mm:ss");
        String formattedDate = codeEntity.getUploadDate().format(formatter);

        Map<String, String> map = new HashMap<>();

        map.put("code", codeEntity.getCode());
        map.put("date", formattedDate);
        response.addHeader("Content-Type", "text/html");
        return map;
    }

    /**
     * @return last 10 or less codes, or return empty list
     */
    @RequestMapping(value = "/api/code/latest", method = RequestMethod.GET)
    @ResponseBody
    public List<CodeEntity> getLatestCodes() {

        return repository.findFirst10ByOrderByUploadDateDesc().orElse(new ArrayList<>());
    }
}