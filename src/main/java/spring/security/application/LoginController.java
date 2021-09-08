package spring.security.application;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.security.domain.UserCredentials;
import spring.security.domain.UserRepository;
import spring.security.security.UserDto;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-08
 * Project: Security
 */

@RestController
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String singUp(@RequestBody UserCredentials userCredentials){
        userRepository.save(userCredentials);
        return "ok";
    }
}
