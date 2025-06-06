package com.juaracoding.smartpro_web.controller;

import com.juaracoding.smartpro_web.config.OtherConfig;
import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import com.juaracoding.smartpro_web.security.BCryptImpl;
import com.juaracoding.smartpro_web.interfaces.AuthService;
import com.juaracoding.smartpro_web.util.GlobalFunction;
import jakarta.validation.Valid;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login (
            Model model,
            @Valid @ModelAttribute("user") LoginDTO loginDTO,
            BindingResult result,
            WebRequest request
    ) {
        String strAnswer = loginDTO.getHiddenCaptcha();
        String decodePassword = new String(Base64.decode(loginDTO.getPassword()));
        System.out.println("Password Decoded: " + decodePassword);
        GlobalFunction.matchingPattern(decodePassword,"^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
                "password", "Incorrect password format", "user", result);

        Boolean isValid = false;
        if(OtherConfig.getEnableAutomationTesting().equals("y")) {
            isValid = loginDTO.getCaptcha().equals(strAnswer);
        } else {
            isValid = BCryptImpl.verifyHash(loginDTO.getCaptcha(),strAnswer);
        }

        if(result.hasErrors() || !isValid) {
            if(!isValid) {
                model.addAttribute("captchaMessage", "Invalid Captcha");
            }
            GlobalFunction.getCaptchaLogin(loginDTO);
            return "/auth/login";
        }

        return "index";
    }
}
