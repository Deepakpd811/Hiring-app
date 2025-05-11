package com.bridgelab.hiringapp.dto;

import com.bridgelab.hiringapp.entity.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private Role role;
}
