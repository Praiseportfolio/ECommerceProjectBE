//package com.pF.E_commerce.controller;
//
//import com.pF.E_commerce.modal.User;
//import com.pF.E_commerce.service.UserService;
//import jdk.jshell.spi.ExecutionControl;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//        private final UserService userService;
//
//        public UserController(UserService userService) {
//            this.userService=userService;
//        }
//
//        @GetMapping("/profile")
//        public ResponseEntity<User> getUserProfileHandler(
//                @RequestHeader("Authorization") String jwt) throws ExecutionControl.UserException {
//
//            System.out.println("/api/users/profile");
//            User user=userService.findUserByJwtToken(jwt);
//            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
//        }
//
//
//    }
