package spring.boot.security.controllers;

import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.*;

/**
 * http://localhost:8080/eleresiutvonalvaltozo/?requestParam=keresparameter
 */
@RestController
public class DummyController {

  @RequestMapping(value = "/{pathVariable}", method = RequestMethod.GET)
  public String rootMapping(@PathVariable("pathVariable") String pathVariable, @RequestParam("requestParam") String requestParam) {
    // HELLO WORLD ! pathVariable=eleresiutvonalvaltozo requestParam=keresparameter
    System.out.println( "DummyController::rootMapping() : pathVariable=" + pathVariable);

    return "HELLO WORLD ! pathVariable=" + pathVariable + " requestParam=" + requestParam;
  }

  @GetMapping("authtoken")
  public AuthenticationResponse gettAuthToken( JwtAuthenticationToken jwtAuthenticationToken)
  {
    System.out.println( "DummyController::gettAuthToken() : ");

    System.out.println( jwtAuthenticationToken.getName());
    System.out.println( jwtAuthenticationToken.getAuthorities());
    System.out.println( jwtAuthenticationToken.getTokenAttributes().get( StandardClaimNames.PREFERRED_USERNAME));

    return new AuthenticationResponse("========== Hello JWT! ==========");
  }
}