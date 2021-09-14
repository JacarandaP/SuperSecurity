package spring.security.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.security.domain.UserCredentials;
import spring.security.domain.UserRepository;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-08
 * Project: Security
 */

@RestController
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody UserCredentials userCredentials) {
        try {
            UserCredentials user = userCredentials;
            String encoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoded);

            userRepository.save(user);
            return ResponseEntity.ok().body("User signed up");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
