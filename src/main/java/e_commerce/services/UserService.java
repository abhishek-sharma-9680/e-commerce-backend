package e_commerce.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import e_commerce.dto.LoginRequest;
import e_commerce.dto.UserDto;
import e_commerce.model.Role;
import e_commerce.model.User;
import e_commerce.repository.UserRepo;
import e_commerce.security.JwtUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	  private UserRepo userRepo;
	  
	  private final AuthenticationManager authenticationManager;
	  
	  private JwtUtil jwtUtil;
	  
	  private final PasswordEncoder passwordEncoder;

	    public User registerUser(UserDto request) {
	        // Check if user already exists
	        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
	            throw new RuntimeException("User already exists with this email!");
	        }

	        User user = User.builder()
	                .name(request.getName())
	                .email(request.getEmail())
	                .password(passwordEncoder.encode(request.getPassword()))  // TODO: encrypt password later
	                .role(Role.ADMIN)              // Default role is CUSTOMER
	                .build();

	        return userRepo.save(user);
	    }
	    
//=================================================login request============================================================   
	    
	    public String login(LoginRequest request) {
	        // Authenticate user (this checks email + password)
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	        );

	        // If no exception → authentication successful
	        return jwtUtil.generateToken(request.getEmail());
	    }
	
}
