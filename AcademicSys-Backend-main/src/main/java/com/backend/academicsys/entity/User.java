package com.backend.academicsys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    private Integer userId;
    @JsonIgnore
    private String password;
    private String userName;
    private String gender;
    private Long schoolId;
    private String idType;
    private String idNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String phone;
    private String email;
    private String country;
    private String nation;
    private String politicalRole;
    private String userAddr;
    private String birthAddr;
    private String userPhoto;
}