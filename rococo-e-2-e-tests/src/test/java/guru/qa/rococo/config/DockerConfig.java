package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;

public class DockerConfig implements Config {

  static final DockerConfig instance = new DockerConfig();

  private DockerConfig() {
  }

  static {
    Configuration.remote = "http://localhost:4444/wd/hub";
    Configuration.browser = "chrome";
  }

  @Override
  public String frontUrl() {
    return "http://frontend.rococo.dc";
  }

  @Override
  public String artistUrl(){
    return "http://127.0.0.1:3000";
  }

  @Override
  public String authUrl() {
    return "http://127.0.0.1:9000";
  }

  @Override
  public String userdataUrl() {
    return "http://127.0.0.1:8089";
  }

  @Override
  public String jdbcHost() {
    return "rococo-all-db";
  }
}
