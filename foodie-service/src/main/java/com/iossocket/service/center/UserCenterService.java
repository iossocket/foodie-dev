package com.iossocket.service.center;

import com.iossocket.bo.UserInfoRequest;
import com.iossocket.pojo.Users;

public interface UserCenterService {
    Users queryUserInfoById(String userId);
    Users updateUserInfo(String userId, UserInfoRequest userInfoRequest);
    Users updateUserAvatar(String userId, String avatarUrl);
}
