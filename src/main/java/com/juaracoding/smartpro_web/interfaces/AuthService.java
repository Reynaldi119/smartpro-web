package com.juaracoding.smartpro_web.interfaces;

import com.juaracoding.smartpro_web.dto.validation.LoginDTO;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-services", url = "http://localhost:8085/auth")
public interface AuthService {

    @PostMapping("/login")
    public Response<Object> login(@RequestBody LoginDTO loginDTO);
}
