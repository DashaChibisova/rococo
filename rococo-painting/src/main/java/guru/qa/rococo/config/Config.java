package guru.qa.rococo.config;

public interface Config {

    static Config getInstance() {
        return LocalConfig.instance;
    }

    String frontUrl();

    String authUrl();

    String gatewayUrl();

    String jdbcHost();

    String currencyGrpcHost();

    default String jdbcUser() {
        return "postgres";
    }

    default String jdbcPassword() {
        return "secret";
    }

    default int jdbcPort() {
        return 5432;
    }

    default int currencyGrpcPort() {
        return 8092;
    }
}
