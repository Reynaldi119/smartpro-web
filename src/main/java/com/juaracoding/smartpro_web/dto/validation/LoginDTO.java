package com.juaracoding.smartpro_web.dto.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 {
     "email":"mike@gmail.com",
     "password": "P@ssw0rd123"
 }
 */
public class LoginDTO {
    @NotNull(message = "Username tidak boleh kosong")
    @NotEmpty(message = "Username tidak boleh kosong")
    @NotBlank(message = "Username tidak boleh kosong")
    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: michael.laksa123")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            message = "Minimum format allowed: 1 number, 1 lowercase letter, 1 uppercase letter, 1 special characters (_ \"Underscore\", - \"Hyphen\", # \"Hash\", or $ \"Dollar\" or @ \"At\"). Password length allowed 9-16 characters alphanumeric combinations, example: P@ssw0rd123")
    private String password;

    private String captcha;
    private String hiddenCaptcha;
    private String realCaptcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getHiddenCaptcha() {
        return hiddenCaptcha;
    }

    public void setHiddenCaptcha(String hiddenCaptcha) {
        this.hiddenCaptcha = hiddenCaptcha;
    }

    public String getRealCaptcha() {
        return realCaptcha;
    }

    public void setRealCaptcha(String realCaptcha) {
        this.realCaptcha = realCaptcha;
    }
}
