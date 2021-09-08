package spring.security.security;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

public class JWTIssuer {

    private final Key key;
    private final Duration duration;

    public JWTIssuer(Key key, Duration duration) {
        this.key = key;
        this.duration = duration;
    }

    public String createToken(UserAuth user){

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .signWith(key)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(duration)))
                .compact();


    }

}
