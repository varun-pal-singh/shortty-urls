package com.shortty.shortty_backend.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
