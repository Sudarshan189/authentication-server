package com.cspro.authentication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.cspro.authentication.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class JwtHelper {

    @Autowired
    private RSAPrivateKey rsaPrivateKey;

    @Autowired
    private RSAPublicKey rsaPublicKey;

    public String createJwt(AppUser appUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.DATE, 1);

        // take builder to add claims easily
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withSubject(appUser.getName());

        jwtBuilder.withClaim("username", appUser.getId());
        jwtBuilder.withClaim("fullName", appUser.getName());
        jwtBuilder.withClaim("isAdmin", appUser.isAdmin());
        jwtBuilder.withClaim("isActive", appUser.isActive());
        jwtBuilder.withClaim("country", appUser.getCountry());
        return jwtBuilder.withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(rsaPublicKey, rsaPrivateKey));
    }
}
