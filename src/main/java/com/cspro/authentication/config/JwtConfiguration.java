package com.cspro.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Slf4j
@Configuration
public class JwtConfiguration {

    @Value("${app.security.jwt.keystore-location}")
    private String keyStorePath;

    @Value("${app.security.jwt.keystore-password}")
    private String keyStorePassword;

    @Value("${app.security.jwt.key-alias}")
    private String keyAlias;

    @Value("${app.security.jwt.private-key-passphrase}")
    private String privateKeyPassphrase;

    // get the keystore
    @Bean
    public KeyStore keyStore() {
        try {
            // default type jks
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // get the kestore file as input stream
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            // load the password
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error("Unable to load keystore : {}",keyStorePath, e);
            throw new IllegalArgumentException("Unable to load keystore", e);
        }
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        // get the key by passing the passphrase and key alias
        try {
            Key key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
            log.error("Key algorithm not supported {}", key.getAlgorithm());
            throw new RuntimeException("Key algorithm not supported");
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | RuntimeException e) {
            log.error("Unable to load the privateKey from the keystore {}", keyStorePath, e);
            throw new IllegalArgumentException("Unable to load the private key", e);
        }
    }


    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        // get the public key to validate the request
        try {
            Certificate certificate = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = certificate.getPublicKey();
            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
            log.error("Public key algorithm not supported {}", publicKey.getAlgorithm());
            throw new RuntimeException("PublicKey algorithm not supported");
        } catch (KeyStoreException e) {
            log.error("Unable to load the public Key from the keystore {}", keyStorePath, e);
            throw new IllegalArgumentException("Unable to load the public key", e);
        }
    }


    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}
