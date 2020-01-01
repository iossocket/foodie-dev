package com.iossocket.service.impl.center;

import com.iossocket.bo.UserInfoRequest;
import com.iossocket.mapper.UsersMapper;
import com.iossocket.pojo.Users;
import com.iossocket.service.center.UserCenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users queryUserInfoById(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public Users updateUserInfo(String userId, UserInfoRequest userInfoRequest) {
        Users users = new Users();
        users.setId(userId);
        BeanUtils.copyProperties(userInfoRequest, users);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);
        return usersMapper.selectByPrimaryKey(userId);
    }
}
