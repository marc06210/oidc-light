# Getting Started

This application offers a basic OIDC provider on port **8001** (property **server.port**).

The users and their passwords are listed in the **users.json** file. It must be present in the working directory.

The issuer field of the token is driven by the parameter **mgu.issuer** (defaulted
to **http://localhost:8001**) and the TTL of the JWT is driven by the parameter
**mgu.ttl** (defaulted to 3600 (ie. one hour)).

## to build the gradle program

Before you build or run the app you need to create the key files under resources:
- openssl genrsa -out private_key.pem 2048
- openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
- openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der

To build the app **gradle bootJar**

and to run the app **java -jar build/libs/oidc-light-0.0.1-SNAPSHOT.jar**


## exposed endpoints

 - GET **/.well-known/openid-configuration**
 - GET **/.well-known/jwks.json**
 - GET **/idp/userinfo.openid**<br/>with header name **Authorization** and value **Bearer &lt;token&gt;**
 - POST **/login**<br/> with parameters named **username** and **password** and correct values

## commands

To retrieve a token: **http -f POST :8001/login username=user password=user-password**

To get the user info on a token: **http :8001/idp/userinfo.openid "Authorization: bearer &lt;token&gt;"**
