package com.juaracoding.smartpro_web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.smartpro_web.dto.response.ApvProcurementDetailDTO;
import com.juaracoding.smartpro_web.dto.response.ResStaffDTO;
import com.juaracoding.smartpro_web.httpclient.DivisionService;
import com.juaracoding.smartpro_web.httpclient.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.juaracoding.smartpro_web.util.GlobalFunction;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DefaultController {

//    @Autowired
//    private ProcurementService procurementService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private DivisionService divisionService;

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

//    @GetMapping("/#")
//    public String getStaffApvListPage(Model model, WebRequest request) {
//        ResponseEntity<Object> responseObj = null;
//
//        try {
//            // set global attributes and validate token
//            String jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");
//
//            responseObj = procurementService.findAll(jwtToken);
//            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
//            Map<String, Object> mapData = (Map<String, Object>) response.get("data");
//
//            GlobalFunction.setPagingElement(model, mapData, "pages/index", new HashMap<>());
//
//        } catch (Exception e) {
//            System.out.println("Errornya ini: " + e.getMessage());
//            return "redirect:/";
//        }
//
//        return "/pages/index";
//    }
//
//    @GetMapping("/#/{id}")
//    public String getStaffApprove(@PathVariable Long id, @RequestParam Boolean isEdit, Model model, WebRequest request) {
//        ResponseEntity<Object> responseObj = null;
//        String jwtToken = "";
//
//        try {
//            if (isEdit) {
//                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Approve");
//
//                responseObj = procurementService.findById(jwtToken, id);
//
//                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
//                Map<String, Object> mapData = (Map<String, Object>) response.get("data");
//
//                model.addAttribute("approve", objectMapper.convertValue(mapData, ApvProcurementDetailDTO.class));
//
//                return "/pages/index";
//            } else {
//                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Approve");
//
//                responseObj = divisionService.findById(jwtToken, id);
//
//                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
//                Map<String, Object> mapData = (Map<String, Object>) response.get("data");
//
//                model.addAttribute("approve", objectMapper.convertValue(mapData, ResStaffDTO.class));
//
//                return "/pages/index";
//            }
//        } catch (Exception e) {
//            System.out.println("Error : " + e.getMessage());
//            return "redirect:/";
//        }
//    }
}
