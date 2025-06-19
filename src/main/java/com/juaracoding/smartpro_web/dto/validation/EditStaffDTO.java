package com.juaracoding.smartpro_web.dto.validation;

import com.juaracoding.smartpro_web.dto.relation.RelDivisionDTO;
import com.juaracoding.smartpro_web.dto.relation.RelRoleDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EditStaffDTO {
    @Pattern(regexp = "^[a-zA-Z\\s]{4,70}$",
            message = "Invalid format! Only alphabets and spaces are allowed, min 4 to 70 characters")
    private String fullName;

    @Pattern(regexp = "^(62|\\+62|0)8[0-9]{9,13}$",
            message = "Invalid phone number format! Allowed format: min 9 max 13 digits after number 8, for example: (0/62/+62)81111111")
    private String phoneNumber;

    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: michael.laksa123")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            message = "Minimum format allowed: 1 number, 1 lowercase letter, 1 uppercase letter, 1 special characters (_ \"Underscore\", - \"Hyphen\", # \"Hash\", or $ \"Dollar\" or @ \"At\"). Password length allowed 9-16 characters alphanumeric combinations, example: P@ssw0rd123")
    private String password;

    // private String photoProfileUrl;

    @NotNull(message = "Division should not be null!")
    private RelDivisionDTO division;

    @NotNull(message = "Role should not be null!")
    private RelRoleDTO role;

    // setters getters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RelDivisionDTO getDivision() {
        return division;
    }

    public void setDivision(RelDivisionDTO division) {
        this.division = division;
    }

    public RelRoleDTO getRole() {
        return role;
    }

    public void setRole(RelRoleDTO role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    // public String getPhotoProfileUrl() {
    //     return photoProfileUrl;
    // }

    // public void setPhotoProfileUrl(String photoProfileUrl) {
    //     this.photoProfileUrl = photoProfileUrl;
    // }


}
