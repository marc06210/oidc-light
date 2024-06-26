# Getting Started

This application offers a basic OIDC provider on port **8001**. You can configure it with property **server.port**.

The users and their passwords are listed in the **users.json** file. It must be present in the working directory.

The issuer field of the token is driven by the parameter **mgu.issuer** (defaulted
to **http://localhost:8001**) and the TTL of the JWT is driven by the parameter
**mgu.ttl** (defaulted to 3600 (ie. one hour)).

## To build the gradle program

Before you build or run the app you need to create the key files under **src/main/resources**:
- openssl genrsa -out private_key.pem 2048
- openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
- openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der

To build the app **gradle bootJar**

And to run the app **java -jar build/libs/oidc-light-0.0.1-SNAPSHOT.jar**

## Variables

Below are the properties (**application.yaml** file) you can use to customize the application.

|Name of property| Purpose                                 |Type| Default value         |
|----------------|-----------------------------------------|----|-----------------------|
|server.port| Port of the oidc server                 |int| 8001                  |
|mgu.issuer| @ of the issuer that emitted the JWT  |string| http://localhost:8001 |
|mgu.ttl| Validity duration of the JWT in seconds |int| 3600<br/> (1h)        |
|mgu.claim-roles| If this variable is present, then the profiles of the user will be injected in the JWT|string|null|


## Users definition

All users and their information are defined in the **users.json** file.
This file is not packaged at build time and must be present in the directory where the application is launched.

You can define custom profiles assigned to each user with the **profile** attribute.

Below is an example of the result to a invocation to the **idp/userinfo.endpoint**.

```json
{
  "email": "user-email",
  "family_name": "user-lastName",
  "given_name": "user-firstName",
  "logoutUrl": "TODO",
  "name": "user-lastName user-firstName",
  "profile": [
    "USER",
    "OTHER"
  ],
  "sub": "user"
}

```


## Exposed endpoints

 - GET **/.well-known/openid-configuration**
 - GET **/.well-known/jwks.json**
 - GET **/idp/userinfo.openid**<br/>with header name **Authorization** and value **Bearer &lt;token&gt;**
 - POST **/login**<br/> with parameters named **username** and **password** and correct values

## HTTPie commands

To retrieve a token: **http -f POST :8001/login username=user password=user-password**

To get the user info on a token: **http :8001/idp/userinfo.openid "Authorization: bearer &lt;token&gt;"**
