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

import java.util.Optional;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

/**
 * this is like the "entrance" to our system
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
    //we decide here how the app acts with http-> which endpoints are open, which "filters" we want to apply to, how will cors and csrf will be....
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), jwtIssuer, new ObjectMapper());

        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/**", "/login")
                .permitAll().and()
                .addFilter(filter)
                //.anyRequest().authenticated()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    //when authenticationmagenerbuilder is called we will "build" an authentication, using userdetailsService "loadbyusername()" method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //need more refractoring
        auth.userDetailsService(u -> new UserAuth(getUserCredentials(u).getUsername(),
                getUserCredentials(u).getPassword(),
                getUserCredentials(u).getRoleType()))
                .passwordEncoder(passwordEncoder);

    }

    private UserCredentials getUserCredentials(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user not found"));
    }
}
