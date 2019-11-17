package com.iossocket.controller;

import com.iossocket.bo.LoginRequest;
import com.iossocket.bo.RegisterRequest;
import com.iossocket.pojo.Users;
import com.iossocket.service.PassportService;
import com.iossocket.utils.CookieUtils;
import com.iossocket.utils.JSONResult;
import com.iossocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public JSONResult register(@Valid @RequestBody RegisterRequest registerRequest,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            log.info("errors: {}", errors);
            return JSONResult.error("parameter error");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return JSONResult.error("confirm password should be equal to password");
        }

        if (passportService.isUserExisting(registerRequest.getUsername())) {
            return JSONResult.error("username is existing");
        }

        Users user = passportService.createUser(registerRequest);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        return JSONResult.success(user.getId());
    }


    @PostMapping("/login")
    public JSONResult login(@Valid @RequestBody LoginRequest loginRequest,
                            BindingResult bindingResult,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            log.info("errors: {}", errors);
            return JSONResult.error("parameter error");
        }

        Users user = passportService.login(loginRequest);
        if (user == null) {
            return JSONResult.error("username or password is invalid");
        }

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        return JSONResult.success(user);
    }
}
