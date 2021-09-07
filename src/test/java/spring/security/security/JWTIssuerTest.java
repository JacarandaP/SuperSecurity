package spring.security.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

class JWTIssuerTest {

    JWTIssuer jwtIssuer;

    private String key = "*F-J@NcRfUjXn2r5u8x/A?D(G+KbPdSgVkYp3s6v9y$B&E)H@McQfThWmZq4t7w!";
    private String algorithm = "HS512";
    private Integer duration=5;

    @BeforeEach
    private void getJWTIssuer(){

        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        final byte[] signingKeyBytes = Base64.encodeBase64(key.getBytes());
        jwtIssuer = new JWTIssuer(new SecretKeySpec(signingKeyBytes, signatureAlgorithm.getJcaName()),
                Duration.ofMinutes(duration));
    }

    @Test
    void createToken() {
       String actual = jwtIssuer.createToken(new User("name", "password", new ArrayList<>()));
        Assertions.assertEquals("", actual);
    }
}