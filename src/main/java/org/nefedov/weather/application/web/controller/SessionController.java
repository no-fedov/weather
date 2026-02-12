package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = {"/sign-up", "/"})
public class SessionController {

    @GetMapping
    public String signUpForm() {
        return "/static/sign-up";
    }

    @PostMapping
    @ResponseBody
    public String registration(HttpServletRequest request){
        System.out.println("dfdsf");
         return "dfdf";
    }

}
