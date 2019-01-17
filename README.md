# JWT AUTHORIZATION UTILS LIBRARY
## Overview
A part of Authen & Author mechanism.
* Provide utility functions for: 
  * Checking if jwt access token is present
  * Decode jwt token from http header into claims model make it easier for access by application 
* Annotation APIs
* Compatible with both Spring MVC and Spring boot application

## Release note:
* 2019-01-17: version 1.0.0
  * Verify token following the header format: [Authorization: Bearer {jwt}]
  * Generic get jwt token by header key (by new requirement is get more than one jwt header data)  

## Get started

### Maven dependency

```xml
<dependency>
    <groupId>com.tcbs.tcbs-sdk</groupId>
    <artifactId>jwt-authorization-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

Add Your Repo:
```xml
<repositories>
    <repository>
        <id>YOUR_REPO_ID</id>
        <url>YOUR_REPO_URL</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```

### Adding configuration for Spring app

Enable AspectJ and pre-configurated from library:

#### Spring MVC

```java
@Configuration
@ComponentScan({"com.tcbs"})
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringConfig extends WebMvcConfigurerAdapter{
    // your own configs
}
```

#### Spring Boot

```java
@ComponentScan({"com.tcbs"})
@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class ExampleApplication {
    static Logger logger = LoggerFactory.getLogger(ExampleApplication.class);

    public static void main(String[] args) {
        logger.info("start example application");
        SpringApplication.run(ExampleApplication.class, args);
    }
}
```

### Usage
Using lib's annotation:
```java
@RestController
@RequestMapping("/")
public class ExampleController {

    @RequestMapping("/hello")
    @TcbsAuthorization(failIfTokenNotPresent = true, enableTokenVerifying = false)
    public ResponseEntity<?> sayHi(
            @RequestHeader HttpHeaders headers,
            @TcbsAccessToken TcbsAccessClaims accessUser,
            @TcbsAccessToken(headerKey = Constants.API_KEY) TcbsAccessClaims serviceKey) {
        if(serviceKey == null) {
            if(accessUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token not match pre-define assumption");
            } else {
                return ResponseEntity.ok("Hello iss: " + accessUser.getClaimValue("iss", String.class) + " !");
            }
        } else {
            return ResponseEntity.ok("Hello sub: " + accessUser.getClaimValue("sub", String.class) + " !");
        }
    }
}
```
# Contact
[ThanhTT6](thanhtt6@techcombank.com>)