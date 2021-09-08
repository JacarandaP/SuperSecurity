package spring.security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTIssuer jwtIssuer;
    private ObjectMapper objectMapper;

    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager, final JWTIssuer jwtIssuer, final ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtIssuer = jwtIssuer;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return getPrincipal(request)
                .map(user -> authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword(),
                                new ArrayList<>()))
                )
                .orElse(null);
    }

    private Optional<UserDto> getPrincipal(HttpServletRequest req) {
        try {
            return Optional.of(objectMapper.readValue(req.getInputStream().readAllBytes(), UserDto.class));
        } catch (IOException e) {
            //LOG.info("Unable to fetch user from request");
            return Optional.empty();
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult)
                                            throws IOException, ServletException {
        UserAuth user = (UserAuth) authResult.getPrincipal();

        String token = jwtIssuer.createToken(user);
        response.getWriter().write("Bearer " + token);
        response.getWriter().flush();
    }
}
