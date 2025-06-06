package com.juaracoding.smartpro_web.controller;

import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import com.juaracoding.smartpro_web.util.GlobalFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        GlobalFunction.getCaptchaLogin(loginDTO);
        model.addAttribute("user", loginDTO);
        return "/auth/login";
    }
}
