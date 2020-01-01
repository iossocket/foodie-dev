package com.iossocket.controller.center;

import com.iossocket.bo.UserInfoRequest;
import com.iossocket.pojo.Users;
import com.iossocket.service.center.UserCenterService;
import com.iossocket.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("center")
public class CenterController {
    @Autowired
    private UserCenterService userCenterService;

    @GetMapping("/user/{id}")
    public JSONResult queryUserInfo(@PathVariable String id) {
        Users user = userCenterService.queryUserInfoById(id);
        return JSONResult.success(user);
    }

    @PutMapping("/user/{id}")
    public JSONResult updateUserInfo(@PathVariable String id,
                                     @Valid @RequestBody UserInfoRequest userInfoRequest,
                                     BindingResult bindingResult,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            Map<String, String> errorObject = new HashMap<>();
            for (ObjectError error : errors) {
                errorObject.put(((FieldError)error).getField(), error.getDefaultMessage());
            }
            log.info("create order error: {}", errorObject);
            return JSONResult.error(errorObject);
        }
        Users users = userCenterService.updateUserInfo(id, userInfoRequest);
        return JSONResult.success(users);
    }
}
