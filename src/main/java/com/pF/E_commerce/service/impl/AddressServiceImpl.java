package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.dto.AddressRequestDTO;
import com.pF.E_commerce.modal.Address;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.repository.AddressRepository;
import com.pF.E_commerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address addAddress(User user, AddressRequestDTO dto) {
        Address address = Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .postalCode(dto.getPostalCode())
                .user(user)
                .build();

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getUserAddresses(User user) {
        return addressRepository.findByUser(user);
    }

    @Override
    public Address updateAddress(User user, Long id, AddressRequestDTO updated) {
        Address existing = addressRepository.findById(id)
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existing.setStreet(updated.getStreet());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setCountry(updated.getCountry());
        existing.setPostalCode(updated.getPostalCode());

        return addressRepository.save(existing);
    }

    @Override
    public void deleteAddress(User user, Long id) {
        Address address = addressRepository.findById(id)
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.delete(address);
    }
}