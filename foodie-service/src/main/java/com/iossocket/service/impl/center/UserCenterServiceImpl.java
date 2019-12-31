package com.iossocket.service.impl.center;

import com.iossocket.mapper.UsersMapper;
import com.iossocket.pojo.Users;
import com.iossocket.service.center.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
