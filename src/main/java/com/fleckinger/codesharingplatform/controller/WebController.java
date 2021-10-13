package com.fleckinger.codesharingplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WebController {

    /**
     *
     * @return getCode page.html from static folder
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String getCode() {

        /*return "<html> " +
                "<head> " +
                "<title>Code</title> " +
                "</head> " +
                "<body>";*/
        return "getCode page.html";
    }


}
