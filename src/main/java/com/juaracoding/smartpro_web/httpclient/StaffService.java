package com.juaracoding.smartpro_web.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "staff-service", url = "http://localhost:8085/staff")
public interface StaffService {
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(
        @RequestHeader("Authorization") String token,
        @PathVariable Long id
    );
}
