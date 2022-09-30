package com.cspro.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Slf4j
@Configuration
public class SecurityKeyConfiguration {

    @Value("${app.security.jwt.public-key}")
    private String publicKey;

    // Get the RSA Public key
    // Keep it for Authorization


    // Get the RSA Private key
    @Bean
    @Value("${app.security.jwt.private-key}")
    public RSAPrivateKey jwtSigningKey(String privateKey) {
        try {
            // Clean Up
            String privateKeyContent = privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("\n", "")
                    .replace("-----END PRIVATE KEY-----", "");
            // Get the key factory
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
            PrivateKey privKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            return (RSAPrivateKey) privKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Exception when parsing the privateKey {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Value("${app.security.jwt.public-key}")
    public RSAPublicKey jwtValidationKey(String publicKey) {
        try {
            // Clean Up
            String publicKeyContent = publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("\n", "")
                    .replace("-----END PUBLIC KEY-----", "");
            // Get the key factory
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
            PublicKey publicKey1 = keyFactory.generatePublic(x509EncodedKeySpec);
            return (RSAPublicKey) publicKey1;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Exception when parsing the privateKey {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
