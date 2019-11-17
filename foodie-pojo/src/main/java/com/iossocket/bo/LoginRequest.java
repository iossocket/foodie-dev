package com.iossocket.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
