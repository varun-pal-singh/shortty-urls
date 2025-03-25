package com.shortty.url_shortener_backend.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
