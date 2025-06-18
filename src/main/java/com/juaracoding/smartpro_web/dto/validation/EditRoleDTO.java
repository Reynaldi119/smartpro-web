package com.juaracoding.smartpro_web.dto.validation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EditRoleDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s\\.]{4,70}$",
            message = "Invalid format! Only alphabets, dots and spaces are allowed, min 4 to 70 characters")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
