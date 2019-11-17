package com.iossocket.controller;

import com.iossocket.bo.UserBO;
import com.iossocket.pojo.Users;
import com.iossocket.service.PassportService;
import com.iossocket.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class PassportController {

    @Autowired
    private PassportService passportService;

    @GetMapping("/isUserExisting")
    public JSONResult isUserExisting(@RequestParam(defaultValue = "") String username) {
        if (StringUtils.isBlank(username)) {
            return JSONResult.error("username can not be empty");
        }
        Boolean isExisting = passportService.isUserExisting(username);
        return JSONResult.success(isExisting);
    }

    @PostMapping("/register")
    public JSONResult register(@Valid @RequestBody UserBO userBO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            log.info("errors: {}", errors);
            return JSONResult.error("parameter error");
        }

        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return JSONResult.error("confirm password should be equal to password");
        }

        if (passportService.isUserExisting(userBO.getUsername())) {
            return JSONResult.error("username is existing");
        }

        Users user = passportService.createUser(userBO);

        return JSONResult.success(user.getId());
    }
}
