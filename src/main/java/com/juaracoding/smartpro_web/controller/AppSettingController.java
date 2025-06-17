package com.juaracoding.smartpro_web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.smartpro_web.dto.response.DivisionDTO;
import com.juaracoding.smartpro_web.httpclient.DivisionService;
import com.juaracoding.smartpro_web.httpclient.StaffService;
import com.juaracoding.smartpro_web.util.GlobalFunction;


@Controller
@RequestMapping("app-setting")
public class AppSettingController {

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private StaffService staffService;

    // Staff
    @GetMapping("/staff")
    public String getStaffListPage(Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;
        
        try {
            // set global attributes and validate token
            String jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");

            responseObj = staffService.findAll(jwtToken);
            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            GlobalFunction.setPagingElement(model, mapData, "staff", new HashMap<>());

        } catch (Exception e) {
            System.out.println("Errornya ini: " + e.getMessage());
            return "redirect:/";
        }

        return "/pages/app-setting/staff/index";
    }

    // Division
    @GetMapping("/division")
    public String getDivisionListPage(Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;

        try {
            // set global attributes and validate token
            String jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Division");

            responseObj = divisionService.findAll(jwtToken);
            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            GlobalFunction.setPagingElement(model, mapData, "app-setting/division", new HashMap<>());
        }
        catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }

        return "/pages/app-setting/division/index";
    }
    
    @GetMapping("/division/{id}")
    public String getMethodName(@PathVariable Long id, @RequestParam Boolean isEdit, Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;

        try {
            String jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Edit Division");
            responseObj = divisionService.findById(jwtToken, id);

            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            model.addAttribute("division", new ObjectMapper().convertValue(mapData, DivisionDTO.class));

            if (isEdit) {
                return "/pages/app-setting/division/edit";
            }
            else {
                return "/pages/app-setting/division/view";
            }

        } catch (Exception e) {
            System.out.println("Errornya ini: " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/division/{id}")
    public String postMethodName(@PathVariable Long id) {
        //TODO: process POST request
        
        return id.toString();
    }
    
}