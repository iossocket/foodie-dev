package com.iossocket.service.impl;

import com.iossocket.bo.LoginRequest;
import com.iossocket.bo.RegisterRequest;
import com.iossocket.enums.Gender;
import com.iossocket.mapper.UsersMapper;
import com.iossocket.pojo.Users;
import com.iossocket.service.PassportService;
import com.iossocket.utils.DateUtil;
import com.iossocket.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class PassportServiceImpl implements PassportService {

    private static final String USER_AVATAR = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private UsersMapper mapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean isUserExisting(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);

        Users users = mapper.selectOneByExample(userExample);

        return !(users == null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(RegisterRequest registerRequest) {
        String userId = sid.nextShort();
        if (userId == null) {
            return null;
        }

        Users user = new Users();

        user.setId(userId);
        user.setUsername(registerRequest.getUsername());

        String encryptedPwd = MD5Utils.getMD5Str(registerRequest.getPassword());
        if (encryptedPwd == null) {
            return null;
        }
        user.setPassword(encryptedPwd);
        user.setNickName(registerRequest.getUsername());
        user.setAvatar(USER_AVATAR);
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setGender(Gender.unknown.type);
        Date now = new Date();
        user.setCreatedTime(now);
        user.setUpdatedTime(now);

        mapper.insert(user);
        return user;
    }

    @Override
    public Users login(LoginRequest request) {
        String encryptedPwd = MD5Utils.getMD5Str(request.getPassword());
        if (encryptedPwd == null) {
            return null;
        }

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", request.getUsername());
        criteria.andEqualTo("password", encryptedPwd);

        return mapper.selectOneByExample(userExample);
    }
}
