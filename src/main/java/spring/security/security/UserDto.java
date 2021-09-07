package spring.security.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String username;
    private String password;

    public UserDto(){

    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
