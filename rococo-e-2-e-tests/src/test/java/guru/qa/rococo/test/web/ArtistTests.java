package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.db.repository.ArtistRepository;
import guru.qa.rococo.db.repository.ArtistRepositoryHibernate;
import guru.qa.rococo.db.repository.PaintingRepository;
import guru.qa.rococo.db.repository.PaintingRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.message.SuccessMsgAddArtist;
import guru.qa.rococo.page.message.SuccessMsgAddPaintingArtist;
import guru.qa.rococo.page.message.SuccessMsgEditArtist;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistTests extends BaseWebTest{

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
    @DisplayName("Add artist without authorization not allowed")
    @Artist()
    @TestPainting()
    void checkWithoutAuthorizationNotAddArtist(ArtistJson[] artistJson) {
        Selenide.open(ArtistPage.PAGE_URL, ArtistPage.class);
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
        String artistSearchName = artistJson[0].name();

        Selenide.open(ArtistPage.PAGE_URL, ArtistPage.class);
        new ArtistPage()
                .waitForPageLoaded()
                .searchArtist(artistSearchName)
                .artistVisible(artistJson[0].name());
        assertEquals(new ArtistPage().artistSize(), 1);
    }

    @Test
    @DisplayName("Check add artist on page artist")
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
                .addPhoto("images/artist.png")
                .addBtnClick();
        new ArtistPage()
                .artistVisible(name)
                .checkMessageName(SuccessMsgAddArtist.ARTIST_MSG, name)
                .artistVisible(name)
                .checkAvatar("images/artist.png");
        artistRepository.deleteArtistByName(name);
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
                .waitForEditArtistLoaded()
                .setName(name)
                .addBtnClick();
        new ArtistPage()
                .checkMessageName(SuccessMsgEditArtist.ARTIST_MSG, name)
                .getArtistInfo()
                .nameArtist(name);
    }

    @Test
    @DisplayName("Name/biography can`t be longer than 3/10 characters")
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
                .addPhoto("images/artist.png")
                .addBtnClick()
                .errorArtistLengthChar();

    }

    //ЕДоделать как сделаю добавление картин!!!!
    @Test
    @DisplayName("Check add painting artist")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @Artist()
    @TestMuseum
    void checkAddPaintingOnArtist(ArtistJson[] artistJson, MuseumJson[] museumJsons) {
        PaintingRepository paintingRepository = new PaintingRepositoryHibernate();

        String namePainting = DataUtils.generateRandomString(5);

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
                .getPaintingCard()
                .setTitle(namePainting)
                .setDescription(DataUtils.generateRandomSentence(5))
                .selectMuseum(museumJsons[0].title())
                .addPhoto("images/painting.png")
                .addBtnClick();
        new ArtistPage()
                .checkMessageName(SuccessMsgAddPaintingArtist.PAINTING_MSG, namePainting);
        new ArtistPage()
                .getArtistInfo()
                .getPaintingCard()
                .checkAvatarPainting("images/painting.png")
                .titlePainting(namePainting);
        paintingRepository.deletePaintingByTitle(namePainting);
    }
}
