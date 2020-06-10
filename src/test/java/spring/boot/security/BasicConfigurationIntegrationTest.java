package spring.boot.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// @RunWith(SpringRunner.class) JUnit4
// See : gs-testing-web @WebMvcTest(...Controller.class) + @Autowired MockMvc mockMvc;
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BasicConfigurationIntegrationTest
{
  private TestRestTemplate restTemplate;
  private URL baseUrl;

  @LocalServerPort
  private int port;

  @BeforeEach // @Before JUnit4
  public void setUp() throws MalformedURLException
  {
    restTemplate = new TestRestTemplate("user", "password");
    baseUrl = new URL("http://localhost:" + port + "/eleresiutvonalvaltozo/?requestParam=keresparameter");
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException
  {
    ResponseEntity<String> response = restTemplate.getForEntity( baseUrl.toString(), String.class);

    assertEquals( HttpStatus.OK, response.getStatusCode());
    assertTrue( response.getBody().contains( "HELLO WORLD !"));
  }

  @Test()
  public void whenUserWithWrongCredentials_thenUnauthorizedPage() throws Exception
  {
    restTemplate = new TestRestTemplate("user", "wrongpassword");
    ResponseEntity<String> response = restTemplate.getForEntity(baseUrl.toString(), String.class);

    assertEquals( HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull( response.getBody());
    assertTrue( response.getBody().contains( "Unauthorized"));
  }
}