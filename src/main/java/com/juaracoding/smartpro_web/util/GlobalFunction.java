package com.juaracoding.smartpro_web.util;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.smartpro_web.config.OtherConfig;
import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import com.juaracoding.smartpro_web.security.BCryptImpl;
import org.springframework.validation.BindingResult;

import java.util.regex.Pattern;

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
}
