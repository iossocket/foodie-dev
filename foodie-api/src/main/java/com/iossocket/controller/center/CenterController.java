package com.iossocket.controller.center;

import com.iossocket.bo.UserInfoRequest;
import com.iossocket.pojo.Users;
import com.iossocket.resource.FileUpload;
import com.iossocket.service.center.UserCenterService;
import com.iossocket.utils.CookieUtils;
import com.iossocket.utils.DateUtil;
import com.iossocket.utils.JSONResult;
import com.iossocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private FileUpload fileUpload;

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
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, String> errorObject = new HashMap<>();
            for (FieldError error : errors) {
                errorObject.put(error.getField(), error.getDefaultMessage());
            }
            log.info("create order error: {}", errorObject);
            return JSONResult.error(errorObject);
        }
        Users users = userCenterService.updateUserInfo(id, userInfoRequest);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        return JSONResult.success(users);
    }

    @PostMapping("/user/{id}/avatar")
    public JSONResult updateAvatar(@PathVariable String id,
                                   @RequestParam("file") MultipartFile file) {
        if (file == null) {
            return JSONResult.error("File can not be empty");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return JSONResult.error("Invalid file name");
        }
        String[] fileNameComponents = originalFilename.split("\\.");
        int length = fileNameComponents.length;

        if (length < 2 || !fileNameComponents[length - 1].equals("png")) {
            return JSONResult.error("Invalid file name");
        }

        String newFileName = "avatar-" + id + ".png";
        String avatarPrefix = File.separator + id;
        String finalPath = fileUpload.getAvatarStoragePath() + avatarPrefix + File.separator + newFileName;

        try {
            File outputFile = new File(finalPath);
            file.transferTo(outputFile);

            String avatarUrlWithTimeStamp = fileUpload.getAvatarBaseUrl()
                    + avatarPrefix + File.separator + newFileName
                    + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

            Users users = userCenterService.updateUserAvatar(id, avatarUrlWithTimeStamp);

            return JSONResult.success(users);
        } catch (IOException e) {
            log.error(e.toString());
        }
        return JSONResult.success("Upload failed");
    }
}
