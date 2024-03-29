package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;

import guru.qa.rococo.jupiter.annotation.DbUser;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.extension.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.sleep;


@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class, ApiLoginExtension.class})
public class ProfileTests {
    @Test
    @ApiLogin(username = "12345", password = "123")
    void exampleTest() {
        sleep(1000000000L);

    }

    @Test
    @ApiLogin(user = @TestUser)
    void exampleTest1() {
        sleep(1000000000L);

    }

    @Test
    @DbUser
    void exampleTest2() {
        sleep(1000000000L);

    }

}
