package com.leiHealth.baymax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/html")
public class HtmlController {

    @GetMapping("/")
    public String IndexHTML() {
        return "index";
    }
}
