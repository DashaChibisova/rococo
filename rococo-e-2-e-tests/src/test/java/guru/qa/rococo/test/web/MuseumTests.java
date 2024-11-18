package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.db.repository.MuseumRepository;
import guru.qa.rococo.db.repository.MuseumRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.MuseumPage;
import guru.qa.rococo.page.message.SuccessMsgAddMuseum;
import guru.qa.rococo.page.message.SuccessMsgEditMuseum;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MuseumTests extends BaseWebTest {

    @Test
    @DisplayName("Add museum without authorization not allowed")
    @TestMuseum()
    void checkWithoutAuthorizationNotAddMuseum(MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .btnAddArtistIsAbsent()
                .museumVisible(museumJsons[0].title())
                .selectMuseum(museumJsons[0].title())
                .getMuseumInfo()
                .checkCardMuseumWithoutAuthorization(museumJsons[0].title(), museumJsons[0].description());
    }

    @Test
    @DisplayName("Check search museum")
    @TestMuseum(count = 3)
    void checkSearchMuseum(MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String museumSearchName = museumJsons[0].title();

        new MainPage()
                .waitForPageLoaded()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .searchMuseum(museumSearchName)
                .museumVisible(museumSearchName);

        assertEquals(new MuseumPage().museumSize(), 1);
    }

    @Test
    @DisplayName("Check add museum")
    @ApiLogin(user = @TestUser)
    void checkAddMuseum() {
        MuseumRepository museumRepository = new MuseumRepositoryHibernate();

        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .addMuseumClick()
                .getMuseumCardSave()
                .waitForNewMuseumLoaded()
                .setTitle(title)
                .setCity(DataUtils.generateRandomCity())
                .selectCountry("Австрия")
                .addPhoto("images/museum.png")
                .setDescription(DataUtils.generateRandomSentence(7))
                .addBtnClick();
        new MuseumPage()
                .museumVisible(title)
                .checkMessageName(SuccessMsgAddMuseum.MUSEUM_MSG, title)
                .museumVisible(title)
                .checkAvatar("images/museum.png");

        museumRepository.deleteMuseumByTitle(title);
    }

    @Test
    @DisplayName("Check close button on create museum")
    @ApiLogin(user = @TestUser)
    void checkCloseBtnOnAddMuseum() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .addMuseumClick()
                .getMuseumCardSave()
                .waitForNewMuseumLoaded()
                .setTitle(DataUtils.generateRandomName())
                .closeBtnClick()
                .waitForNewMuseumDisappear();
    }

    @Test
    @DisplayName("Check update title, description")
    @ApiLogin(user = @TestUser)
    @TestMuseum()
    void checkUpdateDataAddMuseum(MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .selectMuseum(museumJsons[0].title())
                .getMuseumInfo()
                .titleMuseum(museumJsons[0].title())
                .editClick()
                .getCardMuseum()
                .setTitle(title)
                .addBtnClick();
        new MuseumPage()
                .checkMessageName(SuccessMsgEditMuseum.MUSEUM_MSG, title)
                .getMuseumInfo()
                .titleMuseum(title);
    }

    @Test
    @DisplayName("Title/city/description can`t be longer than 3/10/3 characters")
    @ApiLogin(user = @TestUser)
    void checkInvalideDataForAddArtistDisplayedError() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                .addMuseumClick()
                .getMuseumCardSave()
                .waitForNewMuseumLoaded()
                .setTitle(DataUtils.generateRandomString(2))
                .selectCountry("Австрия")
                .setDescription(DataUtils.generateRandomString(4))
                .setCity(DataUtils.generateRandomString(2))
                .addPhoto("images/museum.png")
                .addBtnClick()
                .errorMuseumLengthChar();
    }

}
