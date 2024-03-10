package com.mgu.istio.oidclight;

import com.mgu.istio.oidclight.model.UserInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserConfigurationService usersConfiguration;
    private final SignatureConfiguration signatureConfiguration;

    @Value("${mgu.issuer:http://localhost:8001}")
    private String issuer;

    @Value("${mgu.ttl:3600}")
    private int ttl;

    public LoginController(UserConfigurationService usersConfiguration, SignatureConfiguration signatureConfiguration) {
        this.usersConfiguration = usersConfiguration;
        this.signatureConfiguration = signatureConfiguration;
    }

    @PostMapping(path="/login", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> login(@RequestParam MultiValueMap<String, String> request) {
        Object o = request.getFirst("username");
        if (o instanceof String username) {
            UserInformation userConfiguration = usersConfiguration.getUserInformation(username);
            if (userConfiguration!=null && userConfiguration.getPassword().equals(request.getFirst("password"))) {
                String jwt = JwtBuilder.createIdToken(username,
                        issuer,
                        ttl,
                        signatureConfiguration.getPrivateKey());
                return new ResponseEntity<>(jwt, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("denied", HttpStatus.UNAUTHORIZED);
    }
}
