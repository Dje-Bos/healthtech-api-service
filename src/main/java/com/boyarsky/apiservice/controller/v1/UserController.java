package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.entity.user.Role;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.repository.UserRolesRepository;
import com.boyarsky.apiservice.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static com.boyarsky.apiservice.mapper.UserMapper.USER_MAPPER;
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

    @RequestMapping(method = RequestMethod.GET)
    @Operation(description = "Retrieve user by email", security = @SecurityRequirement(name = "JWT"))
    public User getUserByEmail(@RequestParam String email) {
        return userRepository.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/role")
    @Operation(description = "Get user role by uid", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<Role> getRoleByUid(@RequestParam String uid) {
        Optional<Role> roleByUid = userRolesRepository.getRoleByUid(uid);
        return roleByUid.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/me")
    @Operation(description = "Get current user", security = @SecurityRequirement(name = "JWT"))
    public UserDto getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userRepository.getUserById(userPrincipal.getId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("User not found: uid=%s", userPrincipal.getId()));
        }
        return USER_MAPPER.toDto(user);
    }
}
