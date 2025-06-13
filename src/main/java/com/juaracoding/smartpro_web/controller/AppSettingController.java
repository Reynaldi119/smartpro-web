package com.juaracoding.smartpro_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("app-setting")
public class AppSettingController {

    @GetMapping("/staff")
    public String getAllStaff() {
        return "/app-setting/staff";
    }
}