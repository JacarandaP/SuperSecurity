package spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.security.domain.UserCredentials;
import spring.security.domain.UserRepository;
import spring.security.security.JWTAuthenticationFilter;
import spring.security.security.JWTIssuer;
import spring.security.security.UserAuth;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTIssuer jwtIssuer;

    @Autowired
    public SecurityConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTIssuer jwtIssuer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtIssuer = jwtIssuer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), jwtIssuer, new ObjectMapper());

        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .addFilter(filter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username) -> new UserAuth(userRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("user not found")).getUsername(),
                userRepository.findByUsername(username).orElseThrow(()->
                        new UsernameNotFoundException("user not found")).getPassword(),
                userRepository.findByUsername(username).orElseThrow(()->
                        new UsernameNotFoundException("user not found")).getRoleType())).
                passwordEncoder(passwordEncoder);

    }
}
