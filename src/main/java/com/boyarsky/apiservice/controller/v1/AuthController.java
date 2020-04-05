package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.ApiResponseDto;
import com.boyarsky.apiservice.dto.AuthResponseDto;
import com.boyarsky.apiservice.dto.LoginRequestDto;
import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.entity.user.AuthType;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.exception.BadRequestException;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.UserRolesService;
import com.boyarsky.apiservice.service.impl.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

/**
 * Service that is responsible for authentication with email and password and sign up of such users
 */
@RestController
@RequestMapping(API_VERSION_1 + "/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private UserRolesService userRolesService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserRolesService userRolesService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userRolesService = userRolesService;
    }

    @PostMapping("/login")
    @Operation(description = "Authenticate user and issue token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/signup")
    @Operation(description = "Sign up new user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDTO) {
        if(userRepository.getUserByEmail(signUpRequestDTO.getEmail()) != null) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequestDTO.getName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(signUpRequestDTO.getPassword());

        user.setAuth(AuthType.BASIC);
        user.setRoles(Set.of(userRolesService.createOrGetByUid("USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseDto(true, "User registered successfully"));
    }

}
