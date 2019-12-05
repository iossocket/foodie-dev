package com.iossocket.controller;

import com.iossocket.bo.AddressRequest;
import com.iossocket.pojo.UserAddress;
import com.iossocket.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}/address")
    public List<UserAddress> queryAll(@PathVariable String userId) {
        return addressService.queryAll(userId);
    }

    @PostMapping("/address")
    public UserAddress addAddress(@RequestBody AddressRequest request) {
        return addressService.addAddress(request);
    }

    @PutMapping("/address")
    public UserAddress updateAddress(@RequestBody AddressRequest request) {
        return addressService.updateAddress(request);
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public String deleteAddress(@PathVariable String userId, @PathVariable String addressId) {
        return addressService.deleteAddress(userId, addressId);
    }

    @PutMapping("/{userId}/address/{addressId}")
    public UserAddress changeToDefault(@PathVariable String userId, @PathVariable String addressId) {
        return addressService.updateAddressToBeDefault(userId, addressId);
    }
}
