package com.juaracoding.smartpro_web.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

import com.juaracoding.smartpro_web.config.OtherConfig;
import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import com.juaracoding.smartpro_web.security.BCryptImpl;

import cn.apiclub.captcha.Captcha;

public class GlobalFunction {
    public static void getCaptchaLogin (LoginDTO loginDTO) {
        Captcha captcha = CaptchaUtil.createCaptcha(275,70);
        String answer = captcha.getAnswer();
        if (OtherConfig.getEnableAutomationTesting().equals("y")) {
            loginDTO.setHiddenCaptcha(answer);
        } else {
            loginDTO.setHiddenCaptcha(BCryptImpl.hash(answer));
        }
        loginDTO.setCaptcha("");
        loginDTO.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }

    public static void matchingPattern (
            String value,
            String regex,
            String field,
            String message,
            String modelAttribut,
            BindingResult result
    ) {
        Boolean isValid = Pattern.compile(regex).matcher(value).find();
        if (!isValid) {
            result.rejectValue(field,"error." + modelAttribut,message);
        }
    }

    /***
     * Set global attribute (like values in header, menus in sidebar) and check for token
     * @param model
     * @param request
     * @param pageName
     * @return token that has been stored in session
     */

    public static String setGlobalAttributeAndTokenCheck(Model model, WebRequest request, String pageName) {
        Object username = request.getAttribute("username", 1);
        Object menuNavbar = request.getAttribute("menu_navbar", 1);
        Object urlImg = request.getAttribute("profile_image_url", 1);
        Object token = request.getAttribute("jwt_token", 1);
        Object staff = request.getAttribute("staff_obj", 1);
        
        if (token == null) {
            return null;
        }

        Map<String, Object> mapStaff = (Map<String, Object>) staff;
        Map<String, Object> mapRole = (Map<String, Object>) mapStaff.get("role");
        Map<String, Object> mapDivision = (Map<String, Object>) mapStaff.get("division");

        model.addAttribute("username", username);
        model.addAttribute("menu_navbar", menuNavbar);
        model.addAttribute("profile_image_url", urlImg);
        model.addAttribute("staff_name", mapStaff.get("full-name"));
        model.addAttribute("staff_id", mapStaff.get("id"));
        model.addAttribute("role_name", mapRole.get("name"));
        model.addAttribute("division_name", mapDivision.get("name"));
        model.addAttribute("page_name", pageName);

        return "Bearer " + token;
    }

    public static void setPagingElement(Model model, Map<String, Object> mapData, String pathServer, Map<Object, Object> filterColumn) {
        List<Map<String, Object>> listContent = (List<Map<String, Object>>) mapData.get("content");
        Map<String,Object> listColumns = new LinkedHashMap<>();
        List<String> listHelper = new ArrayList<>();
        Map<String,Object> columnHeader = listContent.get(0);
        String keyVal = "";
        for(Map.Entry<String,Object> entry : columnHeader.entrySet()){
            keyVal = entry.getKey();
            listHelper.add(keyVal);
            listColumns.put(keyVal, GlobalFunction.camelToStandard(keyVal).toUpperCase());
        }
        /** Content Pagination */
        model.addAttribute("listColumns", listColumns);
        model.addAttribute("listContent", listContent);
        model.addAttribute("listHelper", listHelper);

        /** Element Pagination */
        int currentPage = (int) mapData.get("current-page");
        model.addAttribute("sort", mapData.get("sort"));
        model.addAttribute("sortBy", mapData.get("sort-by"));
        model.addAttribute("currentPage", currentPage==0 ? 1 : (currentPage + 1));
        model.addAttribute("columnName", mapData.get("column-name"));
        model.addAttribute("value", mapData.get("value"));
        model.addAttribute("sizePerPage" ,mapData.get("size-per-page"));
        model.addAttribute("totalPage", mapData.get("total-page"));
        model.addAttribute("totalData", mapData.get("total-mapData"));
        model.addAttribute("pathServer", pathServer); //REQUEST MAPPING
        // model.addAttribute("SIZE_COMPONENT", ConstantValue.SIZE_COMPONENT);
        model.addAttribute("filterColumn", filterColumn);
    }

    private static String camelToStandard(String keyVal) {
        StringBuilder sb = new StringBuilder();
        char c = keyVal.charAt(0);
        sb.append(Character.toLowerCase(c));
        for (int i = 1; i < keyVal.length(); i++) {
            char c1 = keyVal.charAt(i);
            if(Character.isUpperCase(c1)){
                sb.append(' ').append(Character.toLowerCase(c1));
            }
            else {
                sb.append(c1);
            }
        }
        return sb.toString();
    }
}
