package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.TestUser;

import guru.qa.rococo.jupiter.extension.CreateUserExtension;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({CreateUserExtension.class})
public class ProfileTests {
    @Test
    @ApiLogin(user = @TestUser)
    void exampleTest() {

    }

}
