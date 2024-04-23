package guru.qa.rococo.config;

public interface Config {

  static Config getInstance() {
    return "docker".equals(System.getProperty("test.env"))
        ? DockerConfig.instance
        : LocalConfig.instance;
  }

  String frontUrl();

  String authUrl();

  String gatewayUrl();

  String userdataUrl();

  String jdbcHost();

  default String jdbcUser() {
    return "postgres";
  }

  default String jdbcPassword() {
    return "secret";
  }

  default int jdbcPort() {
    return 5432;
  }
}
