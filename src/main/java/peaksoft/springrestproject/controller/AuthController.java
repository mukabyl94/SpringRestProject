package peaksoft.springrestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.springrestproject.dto.*;
import peaksoft.springrestproject.entity.User;
import peaksoft.springrestproject.repository.UserRepository;
import peaksoft.springrestproject.security.jwt.JwtTokenUtil;
import peaksoft.springrestproject.service.StudentService;
import peaksoft.springrestproject.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Tag(name = "Auth Api")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;
    @PostMapping("sign-up")
    @Operation(summary = "Sign up", description = "User can register")
    public UserResponse signUp(@RequestBody UserRequest userRequest){
        return userService.create(userRequest);
    }
    @PostMapping("sign-in")
    @Operation(summary = "Sign in", description = "User can Sing in")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        User user = userRepository.findByEmail(token.getName()).get();
        return loginMapper.loginView(jwtTokenUtil.generateToken(user), "Successful", user);
    }
}
