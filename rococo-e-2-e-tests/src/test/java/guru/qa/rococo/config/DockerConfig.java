package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class DockerConfig implements Config {

  static final DockerConfig instance = new DockerConfig();

  private DockerConfig() {
  }

  static {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);
    selenoidOptions.put("enableVideo", false);
    selenoidOptions.put("timeout", 20000L);
    selenoidOptions.put("sessionTimeout", "20m");
    capabilities.setCapability("selenoid:options", selenoidOptions);
    Configuration.remote = "http://selenoid:4444/wd/hub";
    Configuration.browser = "chrome";
    Configuration.browserVersion = "117.0";
    Configuration.browserCapabilities = capabilities;
    Configuration.browserSize = "1980x1024";
    Configuration.headless = false;
    Configuration.timeout = 20000L;
  }

  @Override
  public String frontUrl() {
    return "http://client.rococo.dc";
  }

  @Override
  public String gatewayUrl() {
    return "http://gateway.rococo.dc:8090";
  }

  @Override
  public String authUrl() {
    return "http://auth.rococo.dc:9000";
  }

  @Override
  public String userdataUrl() {
    return "http://userdata.rococo.dc:8089";
  }

  @Override
  public String jdbcHost() {
    return "rococo-all-db";
  }
}
