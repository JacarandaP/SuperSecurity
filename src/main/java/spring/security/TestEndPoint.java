package spring.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hodei Eceiza
 * Date: 9/6/2021
 * Time: 23:17
 * Project: SuperSecurity
 * Copyright: MIT
 */
@RestController
public class TestEndPoint {

    @PostMapping("/test")
    public ResponseEntity<?> testMe(@RequestBody LoginReq loginReq){
        return ResponseEntity.ok(loginReq);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class LoginReq {
        @JsonProperty(value="username")
        private String email;
        private String password;

        //@JsonCreator
        public LoginReq(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

}
