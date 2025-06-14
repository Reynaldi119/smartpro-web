package com.juaracoding.smartpro_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juaracoding.smartpro_web.httpclient.DivisionService;

@Controller
@RequestMapping("app-setting")
public class AppSettingController {

    @Autowired
    private DivisionService divisionService;

    // Staff

    @GetMapping("/staff")
    public String getStaffListPage() {
        return "/pages/app-setting/staff/index";
    }

    // Division

    @GetMapping("/division")
    public String getDivisionListPage() {
        // get data from API
        return "/pages/app-setting/division/index";
    }
    
    @GetMapping("/division/{id}")
    public String getMethodName(@PathVariable Long id) {

        // get data from API with param id
        return "/pages/app-setting/division/edit";
    }
}