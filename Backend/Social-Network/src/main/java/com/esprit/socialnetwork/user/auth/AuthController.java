package com.esprit.socialnetwork.user.auth;
import com.esprit.socialnetwork.user.config.JwtService;

import com.esprit.socialnetwork.user.User;
import com.esprit.socialnetwork.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private  final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/logon")
    public ResponseEntity<User> getuser(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.getuser(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> hello(@CurrentSecurityContext(expression="authentication?.name") String username) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
      User user = userService.getUser(username);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return ResponseEntity.ok(user)  ;
    }

}
