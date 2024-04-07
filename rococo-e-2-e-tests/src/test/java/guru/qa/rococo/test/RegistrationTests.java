package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.extension.ApiLoginExtension;
import guru.qa.rococo.jupiter.extension.BrowserExtension;
import guru.qa.rococo.jupiter.extension.ContextHolderExtension;
import guru.qa.rococo.jupiter.extension.HibernateCreteUserExtension;
import guru.qa.rococo.jupiter.model.UserJson;
import guru.qa.rococo.page.LoginPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.ProfilePage;
import guru.qa.rococo.page.RegisterPage;
import guru.qa.rococo.page.message.*;
import guru.qa.rococo.utils.DataUtils;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class})
public class RegistrationTests {

    @ParameterizedTest
    @DisplayName("Username/password can`t be longer than 30/50 characters")
    @MethodSource("testCasesLogin")
    void checkInvalideDataForRegistrationDisplayedError(String username, String password) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toLoginPage();
        new LoginPage()
                .waitForPageLoaded()
                .goToRegister();
        new RegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordRepeat(password)
                .submitClick()
                .errorRegistrationLengthChar();

    }

    @Test
    @DisplayName("")
    void checkRegistrationBeSuccess() {
        String password = DataUtils.generateRandomString(20);
        String username = DataUtils.generateRandomString(10);
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toLoginPage();
        new LoginPage()
                .waitForPageLoaded()
                .goToRegister();
        new RegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordRepeat(password)
                .submitClick();
        new LoginPage()
                .waitForPageLoaded()
                .setLogin(username)
                .setPassword(password)
                .submit();
        new MainPage()
                .avatarNotSelected();    }

    @Test
    @DisplayName("")
    void checkDifferentPasswordDisplayedError() {
        String password = DataUtils.generateRandomString(20);
        String password2 = DataUtils.generateRandomString(20);
        String username = DataUtils.generateRandomString(10);
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toLoginPage();
        new LoginPage()
                .waitForPageLoaded()
                .goToRegister();
        new RegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordRepeat(password2)
                .submitClick()
                .errorRegistrationDifferentPassword();
   }

    static Stream<Arguments> testCasesLogin() {
        return Stream.of(Arguments.of(DataUtils.generateRandomString(51), DataUtils.generateRandomString(2)),
                Arguments.of(DataUtils.generateRandomString(55), DataUtils.generateRandomString(13)));
    }

}
