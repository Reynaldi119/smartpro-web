package com.juaracoding.smartpro_web.controller;

import java.util.HashMap;
import java.util.Map;

import com.juaracoding.smartpro_web.dto.response.ResProfileDTO;
import com.juaracoding.smartpro_web.dto.response.ResStaffDTO;
import com.juaracoding.smartpro_web.dto.validation.EditStaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.smartpro_web.dto.response.RoleDTO;
import com.juaracoding.smartpro_web.dto.validation.EditDivisionDTO;
import com.juaracoding.smartpro_web.dto.validation.EditRoleDTO;
import com.juaracoding.smartpro_web.httpclient.DivisionService;
import com.juaracoding.smartpro_web.httpclient.MenuService;
import com.juaracoding.smartpro_web.httpclient.RoleService;
import com.juaracoding.smartpro_web.httpclient.StaffService;
import com.juaracoding.smartpro_web.util.GlobalFunction;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("app-setting")
public class AppSettingController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

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

            GlobalFunction.setPagingElement(model, mapData, "app-setting/staff", new HashMap<>());

        } catch (Exception e) {
            System.out.println("Errornya ini: " + e.getMessage());
            return "redirect:/";
        }

        return "/pages/app-setting/staff/index";
    }

    @GetMapping("/staff/{id}")
    public String getStaff(@PathVariable Long id, @RequestParam Boolean isEdit, Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        try {
            if (isEdit) {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");

                responseObj = staffService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("staff", objectMapper.convertValue(mapData, ResStaffDTO.class));

                return "/pages/app-setting/staff/edit";
            } else {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");

                responseObj = divisionService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("staff", objectMapper.convertValue(mapData, ResStaffDTO.class));

                return "/pages/app-setting/staff/view";
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/staff/profile/{id}")
    public String staffEditProfile(@PathVariable Long id, Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        try {
            jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");

            responseObj = staffService.findById(jwtToken, id);

            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            model.addAttribute("staff", objectMapper.convertValue(mapData, ResProfileDTO.class));

            return "/pages/app-setting/staff/edit_profile";
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/staff/{id}")
    public String updateStaff(
            @ModelAttribute("staff") @Valid EditStaffDTO editStaffDTO,
            @PathVariable Long id,
            BindingResult bindingResult,
            Model model,
            WebRequest request
    ) {
        //TODO: process POST request
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        if (bindingResult.hasErrors()) {
            model.addAttribute("staff", editStaffDTO);
            model.addAttribute("id", id);
            return "redirect:/";
        }

        try {
            jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Staff");
            responseObj = staffService.update(jwtToken, editStaffDTO, id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }

        return "redirect:/app-setting/staff";
    }

//    @PostMapping("/staff/{id}")
//    public String uploadStaffImage(
//            Model model,
//            @PathVariable String username,
//            @RequestParam MultipartFile file, WebRequest request) {
//        ResponseEntity<Object> response = null;
//        String jwt = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request,"Edit Profile");
//        if (jwt.equals("redirect:/")) {
//            return jwt;
//        }
//        try {
//            response = staffService.uploadImage(jwt,username, file);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return "redirect:/";
//        }
//        Map<String, Object> data = (Map<String, Object>) response.getBody();
//        String urlImg = data.get("url-img").toString();
////        model.addAttribute("pesan","Data Berhasil Diubah");
//        request.setAttribute("URL_IMG", urlImg, 1);
//        GlobalFunction.setGlobalAttributeAndTokenCheck(model,request,"HOME");
//        model.addAttribute("URL_IMG",urlImg);
//        return "/pages/app-setting/staff/index";
//    }

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
    public String getDivision(@PathVariable Long id, @RequestParam Boolean isEdit, Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        try {           
            if (isEdit) {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Edit Division");

                responseObj = divisionService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("division", objectMapper.convertValue(mapData, RoleDTO.class));

                return "/pages/app-setting/division/edit";
            }
            else {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Division Detail");

                responseObj = divisionService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("division", objectMapper.convertValue(mapData, RoleDTO.class));

                return "/pages/app-setting/division/view";
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/division/{id}")
    public String updateDivision(
        @ModelAttribute("division") @Valid EditDivisionDTO divisionDTO,
        @PathVariable Long id, 
        BindingResult bindingResult,
        Model model, 
        WebRequest request
    ) {
        //TODO: process POST request
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        if (bindingResult.hasErrors()) {
            model.addAttribute("division", divisionDTO);
            model.addAttribute("id", id);
            return "redirect:/";
        }

        try {
            jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Edit Division");
            responseObj = divisionService.update(jwtToken, divisionDTO, id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }

        return "redirect:/app-setting/division";
    }

    // Role
    @GetMapping("/role")
    public String getRoleListPage(Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;

        try {
            // set global attributes and validate token
            String jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Role");

            responseObj = roleService.findAll(jwtToken);
            Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
            Map<String, Object> mapData = (Map<String, Object>) response.get("data");

            GlobalFunction.setPagingElement(model, mapData, "app-setting/role", new HashMap<>());
        }
        catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }

        return "/pages/app-setting/role/index";
    }
    
    @GetMapping("/role/{id}")
    public String getRole(@PathVariable Long id, @RequestParam Boolean isEdit, Model model, WebRequest request) {
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        try {           
            if (isEdit) {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Edit Role");

                responseObj = roleService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("role", objectMapper.convertValue(mapData, RoleDTO.class));

                return "/pages/app-setting/role/edit";
            }
            else {
                jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Role Detail");

                responseObj = roleService.findById(jwtToken, id);

                Map<String, Object> response = (Map<String, Object>) responseObj.getBody();
                Map<String, Object> mapData = (Map<String, Object>) response.get("data");

                model.addAttribute("role", objectMapper.convertValue(mapData, RoleDTO.class));

                return "/pages/app-setting/role/view";
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/role/{id}")
    public String updateRole(
        @ModelAttribute("role") @Valid EditRoleDTO roleDTO,
        @PathVariable Long id, 
        BindingResult bindingResult,
        Model model, 
        WebRequest request
    ) {
        //TODO: process POST request
        ResponseEntity<Object> responseObj = null;
        String jwtToken = "";

        if (bindingResult.hasErrors()) {
            model.addAttribute("role", roleDTO);
            model.addAttribute("id", id);
            return "redirect:/";
        }

        try {
            jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Edit Role");
            responseObj = roleService.update(jwtToken, roleDTO, id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "redirect:/";
        }

        return "redirect:/app-setting/role";
    }

    @GetMapping("/role/permission")
    public String getAccessPermission(Model model, WebRequest request) {
        ResponseEntity<Object> responseMenu = null;
        ResponseEntity<Object> responseRole = null;
        String jwtToken = "";

        try {
            jwtToken = GlobalFunction.setGlobalAttributeAndTokenCheck(model, request, "Access Permission");
            responseMenu = menuService.findAll(jwtToken);
            responseRole = roleService.findAll(jwtToken);

            // Map<String, Object> mapDataMenu = (Map<String, Object>)(Map<String, Object>) response.get("data");
            Map<String, Object> mapPermission = new HashMap<>();

            model.addAttribute("listRole", responseRole);
            model.addAttribute("permission", mapPermission);
        } catch (Exception e) {

        }
        return "/pages/app-setting/role/permission";
    }
    
}