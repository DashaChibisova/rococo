package guru.qa.rococo.test.api;

import guru.qa.rococo.api.GatewayApiClient;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotation.meta.ApiTest;
import guru.qa.rococo.jupiter.extension.ApiLoginExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

@ApiTest
public abstract class BaseApiTest {

    @RegisterExtension
    protected final ApiLoginExtension apiLoginExtension = new ApiLoginExtension(false);

    protected static final Config CFG = Config.getInstance();

    protected final GatewayApiClient gatewayApiClient = new GatewayApiClient();
}
