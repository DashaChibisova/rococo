package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ProfileTests extends BaseWebTest{
    @Test
    @DisplayName("...")
    @Tag("...")
    @ApiLogin(user = @TestUser)
    void exampleTest() {

    }

}
