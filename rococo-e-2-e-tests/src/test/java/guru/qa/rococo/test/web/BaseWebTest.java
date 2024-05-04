package guru.qa.rococo.test.web;

import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotation.meta.WebTest;

@WebTest
public abstract class BaseWebTest {

  protected static final Config CFG = Config.getInstance();

}
