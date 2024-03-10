package com.mgu.istio.oidclight;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.*;

/**
 * Convenient class to hold static methods dealing with a JWT.
 *
 * @author m408461
 *
 */
public class JwtBuilder {

    public static final String JWT_ID = "mgu-oidc-light";
    public static final String SCOPE_KEY = "scope";
    public static final List<String> PING_JWT_SCOPES = Arrays.asList("openid", "address", "email", "phone", "profile");

    /**
     * Creates an id token.
     *
     * @param subject - user concerned by the JWT
     * @param issuer - URL of the issuer to be injected in the JWT
     * @param jwtTtl - TTL of the JWT
     * @param signingKey - the private key used to sign the JWT
     * @return
     */
    public static String createIdToken(String subject, String issuer, int jwtTtl, Key signingKey) {
        Calendar c = Calendar.getInstance();
        // Let's set the JWT Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put(SCOPE_KEY, PING_JWT_SCOPES);
        io.jsonwebtoken.JwtBuilder builder = Jwts.builder()
                .claims(claims)
                .id(JWT_ID)
                .issuedAt(c.getTime())
                .subject(subject)
                .issuer(issuer)
                .signWith(signingKey);

        c.add(Calendar.SECOND, jwtTtl);
        builder.expiration(c.getTime());

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
