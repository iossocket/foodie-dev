package com.iossocket.service.impl;

import com.iossocket.bo.AddressRequest;
import com.iossocket.mapper.UserAddressMapper;
import com.iossocket.pojo.UserAddress;
import com.iossocket.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    public UserAddress queryAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setId(addressId);
        return userAddressMapper.selectOne(address);
    }

    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        return userAddressMapper.select(address);
    }

    @Override
    public UserAddress addAddress(AddressRequest addressRequest) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(addressRequest.getUserId());
        userAddress.setIsDefault(1);
        UserAddress selectResult = userAddressMapper.selectOne(userAddress);

        UserAddress insertOne = convert(addressRequest);
        insertOne.setId(sid.nextShort());
        insertOne.setIsDefault(selectResult != null ? 0 : 1);
        Date date = new Date();
        insertOne.setCreatedTime(date);
        insertOne.setUpdatedTime(date);

        userAddressMapper.insert(insertOne);
        return insertOne;
    }

    @Override
    public UserAddress updateAddress(AddressRequest addressRequest) {
        UserAddress userAddress = convert(addressRequest);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
        return userAddress;
    }

    @Override
    public String deleteAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddressMapper.delete(userAddress);
        return addressId;
    }

    @Override
    public UserAddress updateAddressToBeDefault(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(0);
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("isDefault", 1);
        userAddressMapper.updateByExampleSelective(userAddress, example);

        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        userAddress.setIsDefault(1);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
        return userAddress;
    }

    private UserAddress convert(AddressRequest request) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(request.getUserId());
        userAddress.setId(request.getAddressId());
        userAddress.setReceiver(request.getReceiver());
        userAddress.setMobile(request.getMobile());
        userAddress.setProvince(request.getProvince());
        userAddress.setCity(request.getCity());
        userAddress.setDistrict(request.getDistrict());
        userAddress.setDetail(request.getDetail());
        return userAddress;
    }
}
