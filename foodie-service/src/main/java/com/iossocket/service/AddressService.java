package com.iossocket.service;

import com.iossocket.bo.AddressRequest;
import com.iossocket.pojo.UserAddress;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);
    UserAddress queryAddress(String userId, String addressId);
    UserAddress addAddress(AddressRequest addressRequest);
    UserAddress updateAddress(AddressRequest addressRequest);
    String deleteAddress(String userId, String addressId);
    UserAddress updateAddressToBeDefault(String userId, String addressId);
}
