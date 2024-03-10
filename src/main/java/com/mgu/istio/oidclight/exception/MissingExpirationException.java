package com.mgu.istio.oidclight.exception;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class MissingExpirationException extends ClaimJwtException {
    public MissingExpirationException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    protected MissingExpirationException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }
}
