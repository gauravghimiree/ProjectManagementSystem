package com.gaurav.projectmgmtsystem.Controller;

import com.gaurav.projectmgmtsystem.config.JwtProvider;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.repository.UserRepository;
import com.gaurav.projectmgmtsystem.request.LoginRequest;
import com.gaurav.projectmgmtsystem.response.AuthResponse;
import com.gaurav.projectmgmtsystem.service.CustomUserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider; // Inject JwtProvider as an instance
    @Autowired
    private CustomUserDetailImpl customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@RequestBody User user) {
        // Check if the email already exists
        User isUserExist = userRepository.findByEmail(user.getEmail());
        if (isUserExist != null) {
            return new ResponseEntity<>("Email already exists. Try with another email.", HttpStatus.CONFLICT);
        }

        // Encode the password and save the user
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setFullName(user.getFullName());
        User savedUser = userRepository.save(createdUser);

        // Authenticate the user
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT using the injected JwtProvider instance
        String jwt = jwtProvider.generateToken(authentication);  // Using instance method

        // Create and return AuthResponse
        AuthResponse response = new AuthResponse();
        response.setMessage("Signup success");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);  // Return AuthResponse
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse>signing(@RequestBody LoginRequest loginRequest) {

        String username= loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username,password);
                SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtProvider.generateToken(authentication);  // Using instance method

        // Create and return AuthResponse
        AuthResponse response = new AuthResponse();
        response.setMessage("Signing success");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails==null)
        {

            throw new UsernameNotFoundException("Invalid username or password.");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
