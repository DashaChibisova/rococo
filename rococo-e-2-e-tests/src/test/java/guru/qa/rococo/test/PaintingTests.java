package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.db.repository.ArtistRepository;
import guru.qa.rococo.db.repository.ArtistRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.extension.*;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.message.SuccessMsgAddArtist;
import guru.qa.rococo.page.message.SuccessMsgEditArtist;
import guru.qa.rococo.utils.DataUtils;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreateArtistExtension.class})
//@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class,ApiLoginExtension.class})
@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class,ApiLoginExtension.class,HibernateCreateArtistExtension.class })

public class PaintingTests {

    // отдельно тест на пагинацию и на пустые значения

//   + без авторизации нельзя добавить, можно только смотерть
//         +   можно редактировать с авторизаций (без картин)
//           + можно добавлять с getАвторизацией
      //  + поиск не работает Даша тупая- починила
    // + пустой список
    //если не добавлено что-то из параметров то нельзя сохранить
    // +yевалидные данные
    //добавление картины (если есть музеи то список, если нет, то другая модалка)

//    при создании картины выбрать из существующих художников и выбрать где храниться оригинал
//    при создании картины нет существующих художников и нет храниться оригинал
//    длина на вводимые символы

    @Test
    @DisplayName("Add artist without authorization not allowed")
    @Artist()
    void checkWithoutAuthorizationNotAddArtist(ArtistJson[] artistJson) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .btnAddArtistIsAbsent()
                .artistVisible(artistJson[0].name())
                .selectArtist(artistJson[0].name())
                .getArtistInfo()
                .checkCardArtistWithoutAuthorization(artistJson[0].name(), artistJson[0].biography());

    }

    @Test
    @DisplayName("Check search artist")
    @Artist(count = 3)
    void checkSearchArtist(ArtistJson[] artistJson) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String artistSearchName = artistJson[0].name();

        new MainPage()
                .waitForPageLoaded()
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .searchArtist(artistSearchName)
                .artistVisible(artistJson[0].name());
        assertEquals(new ArtistPage().artistSize(), 1);
    }

    @Test
    @DisplayName("")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkAddArtist() {
         ArtistRepository artistRepository = new ArtistRepositoryHibernate();

        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String name = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                        .addArtistClick()
                .getCardArtist()
                .waitForNewArtistLoaded()
                .setName(name)
                .setBiography(DataUtils.generateRandomString(20))
                .addPhoto("images/profile/artist.png")
                .addBtnClick();
        new ArtistPage()
                .artistVisible(name)
                .checkMessageName(SuccessMsgAddArtist.ARTIST_MSG, name)
                .artistVisible(name)
                .checkAvatar("images/profile/artist.png");

//        artistRepository.deleteInArtistByName(name);
        // удалить артиста не работает решить ошибки

    }

    @Test
    @DisplayName("Check close button on create artist")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkCloseBtnOnAddArtist() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .addArtistClick()
                .getCardArtist()
                .waitForNewArtistLoaded()
                .setName(DataUtils.generateRandomName())
                .closeBtnClick()
                .waitForNewArtistDisappear();
    }


    //(баг при редактировании биографии на 10 символов)
    @Test
    @DisplayName("Check update name, biography, photo")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @Artist()
    void checkUpdateDataAddArtist(ArtistJson[] artistJson) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String name = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .selectArtist(artistJson[0].name())
                .getArtistInfo()
                .nameArtist(artistJson[0].name())
                .editClick()
                .getCardArtist()
                .setName(name)
                .addBtnClick();
        new ArtistPage()
                .checkMessageName(SuccessMsgEditArtist.ARTIST_MSG, name)
                .getArtistInfo()
                .nameArtist(name);
    }

    @Test
    @DisplayName("name/biography can`t be longer than 3/10 characters")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkInvalideDataForAddArtistDisplayedError() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .addArtistClick()
                .getCardArtist()
                .waitForNewArtistLoaded()
                .setName(DataUtils.generateRandomString(2))
                .setBiography(DataUtils.generateRandomString(4))
                .addPhoto("images/profile/artist.png")
                .addBtnClick()
                .errorArtistLengthChar();

    }

    //ЕДоделать как сделаю добавление картин!!!!
    @Test
    @DisplayName("")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @Artist()
    void checkAddPaintingOnArtist(ArtistJson[] artistJson) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .selectArtist(artistJson[0].name())
                .getArtistInfo()
                .checkEmptyListPainting()
                .addPaintingClick()
        ;
/// ЕДоделать как сделаю добавление картин!!!! замокать что нет данных



    }


}
