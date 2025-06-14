package com.juaracoding.smartpro_web.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.juaracoding.smartpro_web.dto.validation.LoginDTO;

@FeignClient(name = "auth-services", url = "http://localhost:8085/auth")
public interface AuthService {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO);
}
