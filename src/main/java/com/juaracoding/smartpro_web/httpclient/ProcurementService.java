package com.juaracoding.smartpro_web.httpclient;

import com.juaracoding.smartpro_web.dto.validation.EditRoleDTO;
import com.juaracoding.smartpro_web.dto.validation.ProcurementRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "procurement-service", url = "http://localhost:8085/procurement")
public interface ProcurementService {

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String token, @RequestBody ProcurementRequestDTO procurementRequestDTO, @PathVariable Long id);
}
