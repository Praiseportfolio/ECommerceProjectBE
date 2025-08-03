package com.pF.E_commerce.controller;

import com.pF.E_commerce.dto.AddressRequestDTO;
import com.pF.E_commerce.modal.Address;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.service.AddressService;
import com.pF.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<Address> add(@RequestBody AddressRequestDTO address) {
        User user = getCurrentUser();
        return ResponseEntity.ok(addressService.addAddress(user, address));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        User user = getCurrentUser();
        return ResponseEntity.ok(addressService.getUserAddresses(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody AddressRequestDTO addressDTO, @RequestHeader("Authorization") String token) {
        User user = getCurrentUser();
        Address updated = addressService.updateAddress(user, id, addressDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = getCurrentUser();
        addressService.deleteAddress(user, id);
        return ResponseEntity.noContent().build();
    }
}