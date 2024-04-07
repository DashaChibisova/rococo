package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.extension.*;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.ProfilePage;
import guru.qa.rococo.page.message.SuccessMsgProfile;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreateArtistExtension.class})

public class ArtistTests {

//    без авторизации нельзя добавить, можно только смотерть
//            можно редактировать с авторизаций
//            можно добавлять с getАвторизацией
            //поиск не работает Даша тупая- починила
    //пустой список

    @Test
    @DisplayName("Add artist without authorization not allowed")
    @Artist()
    void checkWithoutAuthorizationNotAddArtist() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .btnAddArtistIsAbsent();

    }

    @Test
    @DisplayName("Check search artists")
    @Artist(count = 3)
    void checkSearchArtist() {
        Selenide.open(MainPage.PAGE_URL, MainPage.class);
        new MainPage()
                .waitForPageLoaded()
                .toArtistPageFromHeader();
        new ArtistPage()
                .waitForPageLoaded()
                .searchArtist()
                ;

    }
}
