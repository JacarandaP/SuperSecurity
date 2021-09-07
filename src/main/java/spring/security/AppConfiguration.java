package spring.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.security.security.JWTIssuer;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

@Configuration
public class AppConfiguration {

    @Value("${security.key}")
    private String key;

    @Value("${security.duration}")
    private Integer duration;

    @Value("${security.algorithm}")
    private String algorithm;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
     return new BCryptPasswordEncoder();

 }

    @Bean
    public JWTIssuer getJwtIssuer(){
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        final byte[] signingKeyBytes = Base64.encodeBase64(key.getBytes());
        return new JWTIssuer(new SecretKeySpec(signingKeyBytes, signatureAlgorithm.getJcaName()),
                Duration.ofMinutes(duration));

    }



}

