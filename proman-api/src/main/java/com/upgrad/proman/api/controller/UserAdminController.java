package com.upgrad.proman.api.controller;

import com.upgrad.proman.api.model.CreateUserRequest;
import com.upgrad.proman.api.model.CreateUserResponse;
import com.upgrad.proman.api.model.UserDetailsResponse;
import com.upgrad.proman.api.model.UserStatusType;
import com.upgrad.proman.service.business.UserAdminBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserAdminController {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid) throws ResourceNotFoundException {
        final UserEntity userEntity = userAdminBusinessService.getUser(userUuid);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().id(userEntity.getUuid()).firstName(userEntity.getFirstName()).lastName(userEntity.getLastName())
                .emailAddress(userEntity.getEmail()).mobileNumber(userEntity.getMobilePhone()).status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody final CreateUserRequest userRequest){

    }
}
