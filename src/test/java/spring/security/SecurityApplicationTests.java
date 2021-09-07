package spring.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import spring.security.security.JWTIssuer;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;

@SpringBootTest
class SecurityApplicationTests {

    @Value("${security.key}")
    private String key;

    @Value("${security.duration}")
    private Integer duration;

    @Value("${security.algorithm}")
    private String algorithm;


    @Test
    void contextLoads() {

        }


}
