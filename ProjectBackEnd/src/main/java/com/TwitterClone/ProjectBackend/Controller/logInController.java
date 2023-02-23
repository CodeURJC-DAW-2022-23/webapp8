package com.TwitterClone.ProjectBackend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class logInController {
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
}
