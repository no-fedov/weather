package org.nefedov.weather.application.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/hello")
public class HelloController {

    @GetMapping
    public String doGet() {
        return "hello";
    }
}
