package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;

import guru.qa.rococo.jupiter.extension.ApiLoginExtension;
import guru.qa.rococo.jupiter.extension.ContextHolderExtension;
import guru.qa.rococo.jupiter.extension.CreateUserExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.sleep;


@ExtendWith({ContextHolderExtension.class,  ApiLoginExtension.class})
public class ProfileTests {
    @Test
    @ApiLogin(username = "12345", password = "123")
    void exampleTest() {
        sleep(1000000000L);

    }

}
