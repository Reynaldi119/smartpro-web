package com.juaracoding.smartpro_web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.juaracoding.smartpro_web.httpclient.DivisionService;
import com.juaracoding.smartpro_web.util.GlobalFunction;

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
    public String getDivisionListPage(Model model, WebRequest request) {
        // get data from API
        ResponseEntity<Object> responseObj = null;
        String jwt = GlobalFunction.tokenCheck(model, request);

        try {
            responseObj = divisionService.findAll(jwt);
            System.out.println(responseObj.getHeaders().toString());
            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            GlobalFunction.setPagingElement(model, mapData, "division", new HashMap<>());
            GlobalFunction.setGlobalAttribute(model, request, "Division");
        }
        catch (Exception e) {
            System.out.println("Errornya ini: " + e.getMessage());
            return "redirect:/";
        }

        return "/pages/app-setting/division/index";
    }
    
    @GetMapping("/division/{id}")
    public String getMethodName(@PathVariable Long id) {

        // get data from API with param id
        return "/pages/app-setting/division/edit";
    }
}