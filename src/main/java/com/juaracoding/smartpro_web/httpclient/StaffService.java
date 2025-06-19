package com.juaracoding.smartpro_web.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.juaracoding.smartpro_web.dto.validation.EditStaffDTO;

@FeignClient(name = "staff-service", url = "http://localhost:8085/staff")
public interface StaffService {
    
    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(
        @RequestHeader("Authorization") String token,
        @RequestParam Boolean editProfile,
        @PathVariable Long id
    );

    @PutMapping("/{id}")
    public ResponseEntity<Object> update( @RequestHeader("Authorization") String token,
                                          @RequestBody EditStaffDTO editStaffDTO,
                                          @PathVariable Long id);

    @PostMapping(value="/files/upload/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(
            @RequestHeader("Authorization") String token,
            @PathVariable String username,
            @RequestPart("file") MultipartFile file);
}
