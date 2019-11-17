package com.iossocket.service;

import com.iossocket.bo.LoginRequest;
import com.iossocket.bo.RegisterRequest;
import com.iossocket.pojo.Users;

public interface PassportService {
    Boolean isUserExisting(String username);
    Users createUser(RegisterRequest request);
    Users login(LoginRequest request);
}
