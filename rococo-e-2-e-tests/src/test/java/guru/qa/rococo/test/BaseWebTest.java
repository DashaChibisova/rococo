package guru.qa.rococo.test;

import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.extension.BrowserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {

    protected static final Config CFG = Config.getInstance();

}
