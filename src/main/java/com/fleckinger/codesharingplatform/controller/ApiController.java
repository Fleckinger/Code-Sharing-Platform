package com.fleckinger.codesharingplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ApiController {

    @GetMapping("/api/code")
    public void getCode() {
        System.out.println("TestGetCode");
    }
    //1 коммит
    //2 коммит


}
