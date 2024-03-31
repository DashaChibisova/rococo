package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.AllureIdParam;
import guru.qa.rococo.jupiter.annotation.ApiLogin;

import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.extension.*;
import guru.qa.rococo.jupiter.model.UserJson;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.ProfilePage;
import guru.qa.rococo.page.message.*;
import guru.qa.rococo.utils.DataUtils;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;



@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class, ApiLoginExtension.class})
public class ProfileTests {

    @Test
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void avatarShouldBeDisplayedInMainPage() {
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .addAvatar("images/profile/duck.jpg")
                .updateClick()
                .checkMessage(SuccessMsgProfile.PROFILE_MSG);
        new MainPage()
                .checkAvatar("images/profile/duck.jpg");
    }

    @Test
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void avatarNotUpdateFailedMessageDisplayed(UserJson user) {
        System.out.println(user.username());
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .addAvatar("images/profile/7.png")
                .updateClick()
                .checkMessage(ErrorMsgAvatar.PROFILE_MSG);
        new MainPage()
                .avatarNotSelected();
    }

    @Test
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkCloseBtnDisappiarProfile(UserJson user) {
        System.out.println(user.username());
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .closeProfile();
        new MainPage();
//                .avatarNotSelected(); waitForPageLoaded
    }

    @Test
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkGoToUnAvtorizationZoneFromProfile(UserJson user) {
        System.out.println(user.username());
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .notAvtorization();

        new MainPage()
                .checkMessage(SuccessMsgSession.SESSION_MSG)
                .toLoginAppiar();
    }

    @ParameterizedTest
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @MethodSource("testCasesFirstname")
    void checkFirstnameBeOrNotBeUpdatedMessageDisplayed(String name, Msg massage) {
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .setFirstnameUser(name)
                .updateClick()
                .checkMessage(massage);
        new MainPage();
//                .avatarNotSelected();
    }

    @ParameterizedTest
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @MethodSource("testCasesLastname")
    void checkLastnameBeUpdateFailedMessageDisplayed(String name, Msg massage) {
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .setSurnameUser(name)
                .updateClick()
                .checkMessage(massage);
        new MainPage();
//                .avatarNotSelected();
    }

    static Stream<Arguments> testCasesFirstname() {
        return Stream.of(Arguments.of(DataUtils.generateRandomString(31),ErrorMsgFirstname.FIRSTNAME_MSG),
                Arguments.of(DataUtils.generateRandomString(30), SuccessMsgProfile.PROFILE_MSG));
    }

    static Stream<Arguments> testCasesLastname() {
        return Stream.of(Arguments.of(DataUtils.generateRandomString(51),ErrorMsgLastname.LASTNAME_MSG),
                Arguments.of(DataUtils.generateRandomString(50), SuccessMsgProfile.PROFILE_MSG));
    }
}
