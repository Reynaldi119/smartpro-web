package com.juaracoding.smartpro_web.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "division-service", url = "http://localhost:8085/division")
public interface DivisionService {
    
    @GetMapping
    public ResponseEntity<Object> findAll();

    @GetMapping
    public ResponseEntity<Object> findById(Long id);
}
