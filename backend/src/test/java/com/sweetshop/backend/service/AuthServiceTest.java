package com.sweetshop.backend.service;

import com.sweetshop.backend.config.JwtUtils;
import com.sweetshop.backend.dto.LoginRequest;
import com.sweetshop.backend.dto.RegisterRequest;
import com.sweetshop.backend.entity.User;
import com.sweetshop.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  AuthenticationManager authenticationManager;
    @Mock
    private  JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @Test
    void test_register_success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("dipanshu");
        request.setEmail("dipanshu@gmail.com");
        request.setPassword("dip@12345");

        when(userRepository.existsByUsername("dipanshu")).thenReturn(false);
        when(userRepository.existsByEmail("dipanshu@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("dip@12345"))
                .thenReturn("encoded-password");

        String response = authService.register(request);

        assertEquals("User registered successfully", response);

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1))
                .encode("dip@12345");
    }

    @Test
    void login_shouldAuthenticateAndReturnJwtToken() {

        LoginRequest request = new LoginRequest();
        request.setUsername("dipanshu");
        request.setPassword("dip@12345");

        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtUtils.generateToken(authentication))
                .thenReturn("mock-jwt-token");

        String token = authService.login(request);

        assertEquals("mock-jwt-token", token);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(authentication);
    }

}
