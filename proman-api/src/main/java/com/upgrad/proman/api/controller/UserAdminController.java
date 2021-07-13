package com.upgrad.proman.api.controller;

import com.upgrad.proman.api.model.UserDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserAdminController {

    public ResponseEntity<UserDetailsResponse> getUser() {

    }
}
