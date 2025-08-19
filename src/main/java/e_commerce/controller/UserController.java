package e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.dto.LoginRequest;
import e_commerce.dto.UserDto;
import e_commerce.model.User;
import e_commerce.security.JwtUtil;
import e_commerce.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
	

 
    private AuthenticationManager authenticationManager;

    
	
	private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDto request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(user);

}
    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(token);
    }
  
    
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//    	
//        String username = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        try {
//            // Authenticate user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//
//            // Generate JWT
//            String token = jwtUtil.generateToken(username);
//
//           
//            return token;
//
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid username or password");
//        }
//    }
//    
}
