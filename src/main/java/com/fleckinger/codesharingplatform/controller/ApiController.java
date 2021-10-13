package com.fleckinger.codesharingplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ApiController {

    @RequestMapping(value = "/api/code", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getCode(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("code", "test");
        return responseMap;
    }
}
