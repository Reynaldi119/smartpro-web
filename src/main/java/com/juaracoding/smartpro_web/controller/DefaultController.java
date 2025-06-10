package com.juaracoding.smartpro_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String index(Model model, WebRequest webRequest) {
        Object username = webRequest.getAttribute("username", 1);
        Object menuNavbar = webRequest.getAttribute("menu_navbar", 1);
        Object urlImg = webRequest.getAttribute("profile_image_url", 1);

        model.addAttribute("username", username);
        model.addAttribute("menu_navbar", menuNavbar);
        model.addAttribute("profile_image_url", urlImg);

        return "index";

        // LoginDTO loginDTO = new LoginDTO();
        // GlobalFunction.getCaptchaLogin(loginDTO);
        // model.addAttribute("user", loginDTO);
        // return "/auth/login";
    }
}
