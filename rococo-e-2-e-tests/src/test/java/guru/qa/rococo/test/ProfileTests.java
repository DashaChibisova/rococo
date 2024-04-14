package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;

import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.model.UserJson;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.ProfilePage;
import guru.qa.rococo.page.message.*;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ProfileTests extends BaseWebTest {

    @Test
    @DisplayName("Add avatar")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void avatarShouldBeDisplayedInMainPage() {
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .addAvatar("images/profile.jpg")
                .updateClick()
                .checkMessage(SuccessMsgProfile.PROFILE_MSG);
        new MainPage()
                .checkAvatar("images/profile.jpg");
    }

    @Test
    @DisplayName("Avatar not updated due to size ")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void avatarNotUpdateFailedMessageDisplayed(UserJson user) {
        System.out.println(user.username());
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .addAvatar("images/887kb.png")
                .updateClick()
                .checkMessage(ErrorMsgAvatar.PROFILE_MSG);
        new MainPage()
                .avatarNotSelected();
    }

    @Test
    @DisplayName("Profile button closing profile")
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
    @DisplayName("Profile go from unauthorization zone")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkGoToUnAuthorizationZoneFromProfile(UserJson user) {
        System.out.println(user.username());
        new MainPage()
                .toLoginPageDelete() // delete
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .notAuthorization();

        new MainPage()
                .checkMessage(SuccessMsgSession.SESSION_MSG)
                .toLoginAppiar();
    }

    @ParameterizedTest
    @DisplayName("Updated firstname ")
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
    @DisplayName("Updated lastname")
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
