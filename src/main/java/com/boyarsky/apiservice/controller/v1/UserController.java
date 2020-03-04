package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.entity.Role;
import com.boyarsky.apiservice.entity.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.repository.UserRolesRepository;
import com.boyarsky.apiservice.security.CurrentUser;
import com.boyarsky.apiservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static java.lang.String.format;

@RestController
@RequestMapping(API_VERSION_1 + "/user")
public class UserController {

    private UserRepository userRepository;
    private UserRolesRepository userRolesRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User userModel) {
        return userRepository.save(userModel);
    }

    @RequestMapping(method = RequestMethod.GET)
    public User getUserByEmail(@RequestParam String email) {
        return userRepository.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/role")
    public ResponseEntity<Role> getRoleByUid(@RequestParam String uid) {
        Optional<Role> roleByUid = userRolesRepository.getRoleByUid(uid);
        if (roleByUid.isPresent()) {
            return ResponseEntity.ok(roleByUid.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User userModel = userRepository.getUserById(userPrincipal.getId());
        if (userModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("User not found: uid=%s", userPrincipal.getId()));
        }
        return userModel;
    }
}
