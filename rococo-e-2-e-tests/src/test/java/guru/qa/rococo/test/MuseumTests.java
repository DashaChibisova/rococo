package guru.qa.rococo.test;

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

public class MuseumTests extends BaseWebTest{

    //все вводится само

    // отдельно тест на пагинацию и на пустые значения

//   + без авторизации нельзя добавить, можно только смотерть
//         +   можно редактировать с авторизаций (без картин)
//           + можно добавлять с getАвторизацией
      //  + поиск не работает Даша тупая- починила
    // + пустой список
    //если не добавлено что-то из параметров то нельзя сохранить
    // +yевалидные данные
    //добавление картины (если есть музеи то список, если нет, то другая модалка)

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
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkAddMuseum() {
         MuseumRepository museumRepository = new MuseumRepositoryHibernate();

        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toMuseumPageFromContent();
        new MuseumPage()
                .waitForPageLoaded()
                        .addMuseumClick()
                .getMuseumCardSave()
                .waitForNewMuseumLoaded()
                .setTitle(title)
                .setCity(DataUtils.generateRandomCity())
                .selectCountry("Австрия")
                .addPhoto("images/museum/museum1.png")
                .setDescription(DataUtils.generateRandomSentence(7))
                .addBtnClick();
        new MuseumPage()
                .museumVisible(title)
                .checkMessageName(SuccessMsgAddMuseum.MUSEUM_MSG, title)
                .museumVisible(title)
                .checkAvatar("images/museum/museum1.png");

//        artistRepository.deleteInArtistByName(name);
        // удалить артиста не работает решить ошибки

    }

    @Test
    @DisplayName("Check close button on create museum")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkCloseBtnOnAddMuseum() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
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
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @TestMuseum()
    void checkUpdateDataAddMuseum(MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
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
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkInvalideDataForAddArtistDisplayedError() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
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
                .addPhoto("images/museum/museum1.png")
                .addBtnClick()
                .errorMuseumLengthChar();
    }

}
