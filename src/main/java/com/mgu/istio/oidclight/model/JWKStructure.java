package com.mgu.istio.oidclight.model;

public class JWKStructure {
    public String kty;
    public String kid;
    public String use;
    public String n;
    public String e;

    public JWKStructure(String kty, String kid, String use, String n, String e) {
        this.kty = kty;
        this.kid = kid;
        this.use = use;
        this.n = n;
        this.e = e;
    }
}
