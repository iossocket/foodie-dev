package com.iossocket.service;

import com.iossocket.bo.UserBO;
import com.iossocket.pojo.Users;

public interface PassportService {
    Boolean isUserExisting(String username);
    Users createUser(UserBO user);
}
