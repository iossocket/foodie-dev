package com.iossocket.controller.center;

import com.iossocket.pojo.Users;
import com.iossocket.service.center.UserCenterService;
import com.iossocket.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
