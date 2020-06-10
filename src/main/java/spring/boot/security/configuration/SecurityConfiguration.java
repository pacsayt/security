package spring.boot.security.configuration;

import net.minidev.json.JSONObject;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity // The @EnableWebSecurity annotation is crucial if we disable the default security configuration.
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
  @Override
  protected void configure( AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
  {
    // we need to use the PasswordEncoder to set the passwords when using Spring Boot 2
    // https://www.baeldung.com/spring-security-5-default-password-encoder
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    authenticationManagerBuilder.inMemoryAuthentication()
                                .withUser("user")
                                .password(encoder.encode("password"))
                                .roles("USER")
                                .and()
                                .withUser("admin")
                                .password(encoder.encode("admin"))
                                .roles("USER", "ADMIN");
  }

  @Override
  protected void configure( HttpSecurity httpSecurity) throws Exception
  {
    httpSecurity.authorizeRequests()
                .antMatchers("/authtoken")
                .access("hasAuthority('security_role')")

                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter( grantedAuthoritiesExtractor());
//                .httpBasic();
  }

  private Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractor()
  {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter( new GrantedAuthoritiesExtractor());
    return jwtAuthenticationConverter;
  }

  static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>>
  {
    public Collection<GrantedAuthority> convert(Jwt jwt)
    {

      JSONObject realmAccess = (JSONObject) jwt.getClaims().get("realm_access");
      Collection<String> roleNames = (Collection<String>) realmAccess.get("roles");

      return roleNames.stream()
                      .map( SimpleGrantedAuthority::new)
                      .collect( Collectors.toList());
    }
  }
}