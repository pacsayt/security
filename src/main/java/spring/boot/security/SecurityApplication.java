package spring.boot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * OAuth 2.0 Spring Boot és Spring Security keretrendszerekkel
 * https://www.jtechlog.hu/2020/02/19/spring-oauth2.html
 * Keycloak inditasa :
 * docker run -e KEYCLOAK_USER=root -e KEYCLOAK_PASSWORD=root -p 8081:8080 --name keycloak jboss/keycloak
 * admin felulet -> http://localhost:8081
 *
 * Realm : SpringBootSecurity
 * Client Id : security (pom.xml : <artifactId>)
 * Role name : security_role
 * User      : securityuser/xxxxx
 *
 * A "JSON az informaciokkal" :
 * http://localhost:8081/auth/realms/SpringBootSecurity/.well-known/openid-configuration
 * Itt :
 * token_endpoint : tokent lehet igényelni
 * jwks_uri : tanúsítvány érhető el itt
 *
 * A tanúsítvány :
 * http://localhost:8081/auth/realms/SpringBootSecurity/protocol/openid-connect/certs
 *
 * curl -s -v http://localhost:8080/authtoken :
 * HTTP/1.1 401 <- 401 Unauthorized
 * Set-Cookie: JSESSIONID=584D3F7ED58B2C58975DFA3E18A89076; Path=/; HttpOnly
 * WWW-Authenticate: Bearer
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * Spring Boot Security Auto-Configuration -
 * https://www.baeldung.com/spring-boot-security-autoconfiguration
 * For example, almost each Spring Boot application is started with Actuator in the classpath.
 * This causes problems because another auto-configuration class needs the one we've just excluded,
 * so the application will fail to start.
 *
 * http://localhost:8080/eleresiutvonalvaltozo/?requestParam=keresparameter
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SecurityApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SecurityApplication.class, args);
	}
}
