package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.db.repository.PaintingRepository;
import guru.qa.rococo.db.repository.PaintingRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.jupiter.model.PaintingJson;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.PaintingPage;
import guru.qa.rococo.page.message.SuccessMsgAddPainting;
import guru.qa.rococo.page.message.SuccessMsgEditPainting;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintingTests extends BaseWebTest {

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
    @DisplayName("Check add painting")
    @ApiLogin(user = @TestUser)
    @TestMuseum
    @TestArtist
    void checkAddPainting(ArtistJson[] artistJsons, MuseumJson[] museumJsons) {
         PaintingRepository paintingRepository = new PaintingRepositoryHibernate();

        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                        .addPaintingClick()
                .getPaintingCardSave()
                .waitForNewPaintingLoaded()
                .setTitle(title)
                .setDescription(DataUtils.generateRandomSentence(10))
                .addPhoto("images/painting.png")
                .selectAuthor(artistJsons[0].name())
                .selectMuseum(museumJsons[0].title())
                .addBtnClick();
        new PaintingPage()
                .paintingVisible(title)
                .checkMessageName(SuccessMsgAddPainting.PAINTING_MSG, title) // Добавлена картины!!!
                .paintingVisible(title)
                .checkAvatar("images/painting.png");

        paintingRepository.deletePaintingByTitle(title);

    }

    @Test
    @DisplayName("Check close button on create painting")
    @ApiLogin(user = @TestUser)
    void checkCloseBtnOnAddPainting() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .addPaintingClick()
                .getPaintingCardSave()
                .waitForNewPaintingLoaded()
                .setTitle(DataUtils.generateRandomName())
                .closeBtnClick()
                .waitForNewPaintingDisappear();
    }

    @Test
    @DisplayName("Check update title, description, content")
    @ApiLogin(user = @TestUser)
    @TestPainting()
    void checkUpdateDataAddPainting(PaintingJson[] paintingJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        String title = DataUtils.generateRandomName();

        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
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
    @DisplayName("Name/description can`t be longer than 3/10 characters")
    @ApiLogin(user = @TestUser)
    @TestArtist
    @TestMuseum
    void checkInvalideDataForAddPaintingDisplayedError(ArtistJson[] artistJsons, MuseumJson[] museumJsons) {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toMainPageByLoginBtn()
                .toPaintingPageFromHeader();
        new PaintingPage()
                .waitForPageLoaded()
                .addPaintingClick()
                .getPaintingCardSave()
                .waitForNewPaintingLoaded()
                .setTitle(DataUtils.generateRandomString(2))
                .setDescription(DataUtils.generateRandomString(4))
                .addPhoto("images/painting.png")
                .selectAuthor(artistJsons[0].name())
                .selectMuseum(museumJsons[0].title())
                .addBtnClick()
                .errorPaintingLengthChar();

    }
}
