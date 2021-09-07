package spring.security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTIssuer jwtIssuer, ObjectMapper objectMapper) {
    }
}
