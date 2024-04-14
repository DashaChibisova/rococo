package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.db.model.PaintingEntity;
import guru.qa.rococo.db.repository.ArtistRepository;
import guru.qa.rococo.db.repository.ArtistRepositoryHibernate;
import guru.qa.rococo.db.repository.PaintingRepository;
import guru.qa.rococo.db.repository.PaintingRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.extension.*;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.jupiter.model.PaintingJson;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.PaintingPage;
import guru.qa.rococo.page.message.SuccessMsgAddArtist;
import guru.qa.rococo.page.message.SuccessMsgAddPainting;
import guru.qa.rococo.page.message.SuccessMsgEditArtist;
import guru.qa.rococo.page.message.SuccessMsgEditPainting;
import guru.qa.rococo.utils.DataUtils;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreatePaintingExtension.class})
//@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class,ApiLoginExtension.class, HibernateCreateArtistExtension.class, HibernateCreateMuseumExtension.class})
@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class,ApiLoginExtension.class, HibernateCreateArtistExtension.class, HibernateCreateMuseumExtension.class,  HibernateCreatePaintingExtension.class})

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
    @DisplayName("Add painting without authorization not allowed")
    @TestPainting()
    void checkWithoutAuthorizationNotAddPainting(PaintingJson[] paintingJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .btnAddPaintingIsAbsent()
                .paintingVisible(paintingJsons[0].title())
                .selectPainting(paintingJsons[0].title())
                .getPaintingInfo()
                .checkCardPaintingWithoutAuthorization(paintingJsons[0].title(), paintingJsons[0].description());
    }

    @Test
    @DisplayName("Check search painting")
    @TestPainting(count = 3)
    void checkSearchPainting(PaintingJson[] paintingJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String paintingSearchName = paintingJsons[0].title();

        new MainPage()
                .waitForPageLoaded()
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .searchPainting(paintingSearchName)
                .paintingVisible(paintingJsons[0].title());
        assertEquals(new PaintingPage().paintingSize(), 1);
    }

    @Test
    @DisplayName("")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @TestMuseum
    @Artist
    void checkAddPainting(ArtistJson[] artistJsons, MuseumJson[] museumJsons) {
         PaintingRepository artistRepository = new PaintingRepositoryHibernate();

        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                        .addPaintingClick()
                .getPaintingCardSave()
                .waitForNewPaintingLoaded()
                .setTitle(title)
                .setDescription(DataUtils.generateRandomSentence(10))
                .addPhoto("images/museum/museum1.png")
                .selectAuthor(artistJsons[0].name())
                .selectMuseum(museumJsons[0].title())
                .addBtnClick();
        new PaintingPage()
                .paintingVisible(title)
                .checkMessageName(SuccessMsgAddPainting.PAINTING_MSG, title) // Добавлена картины!!!
                .paintingVisible(title)
                .checkAvatar("images/museum/museum1.png");

//        artistRepository.deleteInArtistByName(name);
        // удалить артиста не работает решить ошибки

    }

    @Test //----
    @DisplayName("Check close button on create painting")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    void checkCloseBtnOnAddPainting() {
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
    @DisplayName("Check update title, biography, content")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @TestPainting()
    void checkUpdateDataAddPainting(PaintingJson[] paintingJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .selectPainting(paintingJsons[0].title())
                .getPaintingInfo()
                .titlePainting(paintingJsons[0].title())
                .editClick()
                .getCardPainting()
                .setTitle(title)
                .addBtnClick();
        new PaintingPage()
                .checkMessageName(SuccessMsgEditPainting.PAINTING_MSG, title)
                .getPaintingInfo()
                .titlePainting(title);
    }

    @Test
    @DisplayName("name/biography can`t be longer than 3/10 characters")
    @ApiLogin(user = @TestUser) //не регает, только поле нажатия войти заходит
    @Artist
    @TestMuseum
    void checkInvalideDataForAddPaintingDisplayedError(ArtistJson[] artistJsons, MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toLoginPageDelete() // delete
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .addPaintingClick()
                .getPaintingCardSave()
                .waitForNewPaintingLoaded()
                .setTitle(DataUtils.generateRandomString(2))
                .setDescription(DataUtils.generateRandomString(4))
                .addPhoto("images/museum/museum1.png")
                .selectAuthor(artistJsons[0].name())
                .selectMuseum(museumJsons[0].title())
                .addBtnClick()
                .errorPaintingLengthChar();

    }

    //ЕДоделать как сделаю добавление картин!!!!
    @Test//----
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
