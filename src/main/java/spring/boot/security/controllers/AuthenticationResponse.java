package spring.boot.security.controllers;

public class AuthenticationResponse
{
  private String content;

  public AuthenticationResponse( String iniContent)
  {
    content = iniContent;
  }

  public String getContent()
  {
    return content;
  }

  @Override
  public String toString()
  {
    return "AuthenticationResponse{ content='" + content + "'}";
  }
}