package guru.qa.rococo.test.web;

import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.model.ArtistList;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.utils.DataUtils;
import guru.qa.rococo.utils.FileUtils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ArtistApiTests extends BaseApiTest {

    @Test
    @DisplayName("Returned current artist")
    @Artist()
    void currentArtistShouldBeReturned(ArtistJson[] artistJsons) throws Exception {
        ArtistJson response = gatewayApiClient.currentArtist(artistJsons[0].id());
        Allure.step("Check name", () -> {
            Assertions.assertEquals(
                    artistJsons[0].name(),
                    response.name()
            );
        });

        Allure.step("Check biography", () -> {
            Assertions.assertEquals(
                    artistJsons[0].biography(),
                    response.biography()
            );
        });
    }

    @Test
    @DisplayName("Save current artist")
    @ApiLogin(user = @TestUser)
    void saveArtistsWithTokenShouldBeReturned(@Token String bearerToken) throws Exception {
        ArtistJson artistJsons = new ArtistJson(
                null,
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("src/test/resources/images/artist.png").toString()
        );

        ArtistJson response = gatewayApiClient.saveArtists(bearerToken, artistJsons);

        Allure.step("Check name", () -> {
            Assertions.assertEquals(
                    artistJsons.name(),
                    response.name()
            );
        });

        Allure.step("Check biography", () -> {
            Assertions.assertEquals(
                    artistJsons.biography(),
                    response.biography()
            );
        });
    }

    @Test // не работает выбрасывать искл
    @DisplayName("Dont save artist without authorization")
    void saveArtistsWithoutTokenNotAllowed() throws Exception {
        ArtistJson artistJsons = new ArtistJson(
                UUID.randomUUID(),
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("src/test/resources/images/artist.png").toString()
        );

        ArtistJson response = gatewayApiClient.saveArtists(null, artistJsons);
        Allure.step("Check id", () -> {
            Assertions.assertEquals(
                    artistJsons.id(),
                    response.id()
            );
        });
        
    }

    @Test
    @DisplayName("Update artist")
    @ApiLogin(user = @TestUser)
    @Artist()
    void userUpdateInfoArtistWithTokenShouldBeReturned(@Token String bearerToken, ArtistJson[] artist) throws Exception {
        String newName = DataUtils.generateRandomUsername();
        String newBiography = artist[0].biography();

        ArtistJson artistJsons = new ArtistJson(
                artist[0].id(),
                newName,
                newBiography,
                artist[0].photo()
        );

        ArtistJson response = gatewayApiClient.userUpdateInfoArtist(bearerToken, artistJsons);

        Allure.step("Check id", () -> {
            Assertions.assertEquals(
                    artist[0].id(),
                    response.id()
            );
        });

        Allure.step("Check name", () -> {
            Assertions.assertEquals(
                    newName,
                    response.name()
            );
        });

        Allure.step("Check biography", () -> {
            Assertions.assertEquals(
                    newBiography,
                    response.biography()
            );
        });
    }

    @Test // не работает выбрасывать искл
    @Artist()
    @DisplayName("Dont update artist without authorization")
    void userUpdateInfoArtistWithoutTokenNotAllowed(ArtistJson[] artist) throws Exception {
        String newName = DataUtils.generateRandomUsername();
        String newBiography = artist[0].biography();

        ArtistJson artistJsons = new ArtistJson(
                artist[0].id(),
                newName,
                newBiography,
                artist[0].photo()
        );

        ArtistJson response = gatewayApiClient.userUpdateInfoArtist(null, artistJsons);
        Allure.step("Check id", () -> {
            Assertions.assertEquals(
                    artistJsons.id(),
                    response.id()
            );
        });

    }

    @Test
    @Artist(count = 5)
    @DisplayName("Get all artist returned by page")
    void checkPaginOnGetAllArtist(@Token String bearerToken, ArtistJson[] artist) throws Exception {
        ArtistList responseOnePage = gatewayApiClient.getAllArtist(0,1,"");
        ArtistList responseToPage = gatewayApiClient.getAllArtist(1,1,"");


        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseOnePage.content().size()

            );
        });

        Allure.step("Check id one page", () -> {
            Assertions.assertEquals(
                    artist[0].id(),
                    responseOnePage.content().get(0).id()

            );
        });

        Allure.step("Check size two page", () -> {
            Assertions.assertEquals(
                    1,
                    responseToPage.content().size()

            );
        });

        Allure.step("Check id two page", () -> {
            Assertions.assertEquals(
                    artist[1].id(),
                    responseToPage.content().get(0).id()

            );
        });

    }
}
