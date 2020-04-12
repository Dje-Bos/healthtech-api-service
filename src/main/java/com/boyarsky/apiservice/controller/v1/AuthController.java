package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.AuthResponseDto;
import com.boyarsky.apiservice.dto.ApiErrorDto;
import com.boyarsky.apiservice.dto.LoginRequestDto;
import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.service.UserService;
import com.boyarsky.apiservice.service.impl.TokenProvider;
import com.boyarsky.apiservice.service.impl.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

/**
 * Service that is responsible for authentication with email and password and sign up of such users
 */
@RestController
@RequestMapping(API_VERSION_1 + "/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    @Operation(description = "Authenticate user and issue token")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDTO) {

        Authentication authentication = authenticateWithCredentials(loginRequestDTO);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    private Authentication authenticateWithCredentials(@RequestBody @Valid LoginRequestDto loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @PostMapping("/signup")
    @Operation(description = "Sign up new user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDTO) {
        signUpRequestDTO.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        UserDto registeredUser = userService.register(signUpRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(registeredUser.getId()).toUri();

        return ResponseEntity.created(location).body(registeredUser);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDto> handle(UserAlreadyExistsException e) {
        ApiErrorDto errorResponseDto = new ApiErrorDto();
        errorResponseDto.setErr(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

}
