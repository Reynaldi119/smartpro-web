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

    public static String tokenCheck(Model model, WebRequest request) {
        Object tokenJwt = request.getAttribute("jwt_token", 1);
        if(tokenJwt == null){
            return null;
        }
        return "Bearer " + tokenJwt;
    } 

    public static void setGlobalAttribute(Model model, WebRequest request, String pageName) {
        model.addAttribute("username", request.getAttribute("username", 1).toString());
        // model.addAttribute("menu_navbar", request.getAttribute("menu_navbar", 1).toString());
        // model.addAttribute("page_name", pageName);
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
