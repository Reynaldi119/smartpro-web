package com.juaracoding.smartpro_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.juaracoding.smartpro_web.util.GlobalFunction;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String index(Model model, WebRequest webRequest) {
        String token = GlobalFunction.setGlobalAttributeAndTokenCheck(model, webRequest, "Dashboard");

        if (token == null) {
            return "redirect:/auth/login";
        }

        return "/pages/index";

        // LoginDTO loginDTO = new LoginDTO();
        // GlobalFunction.getCaptchaLogin(loginDTO);
        // model.addAttribute("user", loginDTO);
        // return "/auth/login";
    }
}
