package com.juaracoding.smartpro_web.controller;

import java.util.List;
import java.util.Map;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.juaracoding.smartpro_web.config.OtherConfig;
import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import com.juaracoding.smartpro_web.httpclient.AuthService;
import com.juaracoding.smartpro_web.httpclient.StaffService;
import com.juaracoding.smartpro_web.security.BCryptImpl;
import com.juaracoding.smartpro_web.util.GenerateStringMenu;
import com.juaracoding.smartpro_web.util.GlobalFunction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private StaffService staffService;

    @GetMapping("/login")
    public String redirectToLogin(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("user", loginDTO);
        GlobalFunction.getCaptchaLogin(loginDTO);
        return "auth-login";
    }

    @PostMapping("/login")
    public String login (
            Model model,
            @Valid @ModelAttribute("user") LoginDTO loginDTO,
            BindingResult result,
            WebRequest request
    ) {
        String strAnswer = loginDTO.getHiddenCaptcha();
        String decodePassword = new String(Base64.decode(loginDTO.getPassword()));
        GlobalFunction.matchingPattern(
            decodePassword, 
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            "password",
            "Incorrect password format", 
            "user", 
            result
        );

        Boolean isValid = false;
        if(OtherConfig.getEnableAutomationTesting().equals("y")) {
            isValid = loginDTO.getCaptcha().equals(strAnswer);
        } else {
            isValid = BCryptImpl.verifyHash(loginDTO.getCaptcha(), strAnswer);
        }

        if(result.hasErrors() || !isValid) {
            if(!isValid) {
                model.addAttribute("captchaMessage", "Invalid Captcha");
            }
            GlobalFunction.getCaptchaLogin(loginDTO);
            return "auth-login";
        }
        
        loginDTO.setPassword(decodePassword);
        loginDTO.setCaptcha("");
        loginDTO.setHiddenCaptcha("");
        loginDTO.setRealCaptcha("");

        ResponseEntity<Object> response = null;
        String tokenJwt = "";
        String menuNavBar = "";
        Long staffId = 0L;

        try { 
            response = authService.login(loginDTO);
            Map<String, Object> responseMap = (Map<String, Object>) response.getBody();
            Map<String, Object> mapData = (Map<String, Object>) responseMap.get("data");

            List<Map<String,Object>> listMenu = (List<Map<String, Object>>) mapData.get("menu");
            staffId = ((Number) mapData.get("staffId")).longValue();
            menuNavBar = new GenerateStringMenu().stringMenu(listMenu);
            
            tokenJwt = (String) mapData.get("token");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            GlobalFunction.getCaptchaLogin(loginDTO);
            return "auth-login";
        }

        ResponseEntity<Object> responseGetStaff = null;
        responseGetStaff = staffService.findById("Bearer " + tokenJwt, staffId);
        Map<String, Object> mapStaff = (Map<String, Object>) ((Map<String, Object>) responseGetStaff.getBody()).get("data");

        request.setAttribute("jwt_token", tokenJwt, 1);
        request.setAttribute("username", loginDTO.getUsername(), 1);
        request.setAttribute("password", loginDTO.getPassword(), 1);
        request.setAttribute("staff_obj", mapStaff, 1);
        request.setAttribute("menu_navbar", menuNavBar, 1);
        
        model.addAttribute("user", loginDTO);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/auth/login";
    }
}
