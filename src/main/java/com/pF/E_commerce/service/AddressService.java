package com.pF.E_commerce.service;

import com.pF.E_commerce.dto.AddressRequestDTO;
import com.pF.E_commerce.modal.Address;
import com.pF.E_commerce.modal.User;

import java.util.List;

public interface AddressService {
    Address addAddress(User user, AddressRequestDTO dto);
    List<Address> getUserAddresses(User user);
    Address updateAddress(User user, Long addressId, AddressRequestDTO updated);
    void deleteAddress(User user, Long addressId);
}