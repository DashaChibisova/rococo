package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
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

    static Stream<Arguments> testCasesFirstname() {
        return Stream.of(Arguments.of(DataUtils.generateRandomString(31), ErrorMsgFirstname.FIRSTNAME_MSG),
                Arguments.of(DataUtils.generateRandomString(30), SuccessMsgProfile.PROFILE_MSG));
    }

    static Stream<Arguments> testCasesLastname() {
        return Stream.of(Arguments.of(DataUtils.generateRandomString(51), ErrorMsgLastname.LASTNAME_MSG),
                Arguments.of(DataUtils.generateRandomString(50), SuccessMsgProfile.PROFILE_MSG));
    }

    @Test
    @DisplayName("Add avatar")
    @ApiLogin(user = @TestUser)
    void avatarShouldBeDisplayedInMainPage() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .toMainPageByLoginBtn()
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
    @ApiLogin(user = @TestUser)
    void avatarNotUpdateFailedMessageDisplayed() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .toMainPageByLoginBtn()
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
    @ApiLogin(user = @TestUser)
    void checkCloseBtnDisappiarProfile(UserJson user) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .toMainPageByLoginBtn()
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .closeProfile()
                .waitForProfileDisappear();
    }

    @Test
    @DisplayName("Profile go from authorization zone")
    @ApiLogin(user = @TestUser)
    void checkGoToUnAuthorizationZoneFromProfile() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toMainPageByLoginBtn()
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
    @ApiLogin(user = @TestUser)
    @MethodSource("testCasesFirstname")
    void checkFirstnameBeOrNotBeUpdatedMessageDisplayed(String name, Msg massage) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toMainPageByLoginBtn()
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .setFirstnameUser(name)
                .updateClick()
                .checkMessage(massage);
    }

    @ParameterizedTest
    @DisplayName("Updated lastname")
    @ApiLogin(user = @TestUser)
    @MethodSource("testCasesLastname")
    void checkLastnameBeUpdateFailedMessageDisplayed(String name, Msg massage) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .toMainPageByLoginBtn()
                .goToProfile();
        new ProfilePage()
                .waitForPageLoaded()
                .setSurnameUser(name)
                .updateClick()
                .checkMessage(massage);
    }
}
