package guru.qa.rococo.test.api;

import guru.qa.rococo.db.model.CountryEntity;
import guru.qa.rococo.db.repository.*;
import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.model.*;
import guru.qa.rococo.utils.DataUtils;
import guru.qa.rococo.utils.FileUtils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MuseumApiTests extends BaseApiTest {

    @Test
    @DisplayName("Returned current museum")
    @TestMuseum()
    void currentMuseumShouldBeReturned(MuseumJson[] museumJsons) throws Exception {
        MuseumResponce response = gatewayApiClient.currentMuseum(museumJsons[0].id());
        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    museumJsons[0].title(),
                    response.title()
            );
        });

        Allure.step("Check description", () -> {
            Assertions.assertEquals(
                    museumJsons[0].description(),
                    response.description()
            );
        });
    }

    @Test
    @DisplayName("Save current museum")
    @ApiLogin(user = @TestUser)
    void saveArtistsWithTokenShouldBeReturned(@Token String bearerToken) throws Exception {
        MuseumRepository museumRepository = new MuseumRepositoryHibernate();
        MuseumJson museumJson = new MuseumJson(
                null,
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("images/painting.png").toString(),
                "Любой",
                "Австралия"
        );
        MuseumResponce museumResponce = museumJsonToMuseumResponce(museumJson);
        MuseumResponce response = gatewayApiClient.saveMuseum(bearerToken, museumResponce).body();

        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    museumResponce.title(),
                    response.title()
            );
        });

        Allure.step("Check description", () -> {
            Assertions.assertEquals(
                    museumResponce.description(),
                    response.description()
            );
        });
        museumRepository.deleteMuseumByTitle(response.title());
    }

    @Test
    @DisplayName("Dont save museum without authorization")
    void saveMuseumWithoutTokenNotAllowed() throws Exception {
        MuseumJson museumJson = new MuseumJson(
                UUID.randomUUID(),
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("images/painting.png").toString(),
                "Любой",
                "Австралия"
        );
        MuseumResponce museumResponce = museumJsonToMuseumResponce(museumJson);
        int code = gatewayApiClient.saveMuseum("", museumResponce).raw().code();
        Allure.step("Check code", () -> {
            Assertions.assertEquals(
                    401,
                    code
            );
        });
        
    }

    @Test
    @DisplayName("Update museum")
    @ApiLogin(user = @TestUser)
    @TestMuseum()
    void userUpdateInfoMuseumWithTokenShouldBeReturned(@Token String bearerToken, MuseumJson[] museumJsons) throws Exception {
        String newTitle = DataUtils.generateRandomUsername();
        String newDescription = DataUtils.generateRandomSentence(6);

        MuseumJson museumJson = new MuseumJson(
                museumJsons[0].id(),
                newTitle,
                newDescription,
                museumJsons[0].photo(),
                museumJsons[0].city(),
                museumJsons[0].country()
        );
        MuseumResponce museumResponce = museumJsonToMuseumResponce(museumJson);
        MuseumResponce response = gatewayApiClient.updateMuseumInfo(bearerToken, museumResponce).body();

        Allure.step("Check id", () -> {
            Assertions.assertEquals(
                    museumResponce.id(),
                    response.id()
            );
        });

        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    newTitle,
                    response.title()
            );
        });

        Allure.step("Check description", () -> {
            Assertions.assertEquals(
                    newDescription,
                    response.description()
            );
        });
    }

    @Test
    @TestMuseum()
    @DisplayName("Dont update artist without authorization")
    void userUpdateInfoArtistWithoutTokenNotAllowed(MuseumJson[] museumJsons) throws Exception {
        String newTitle = DataUtils.generateRandomUsername();
        String newDescription = DataUtils.generateRandomSentence(6);

        MuseumJson museumJson = new MuseumJson(
                museumJsons[0].id(),
                newTitle,
                newDescription,
                museumJsons[0].photo(),
                museumJsons[0].city(),
                museumJsons[0].country()
        );
        MuseumResponce museumResponce = museumJsonToMuseumResponce(museumJson);

        int code = gatewayApiClient.updateMuseumInfo("", museumResponce).raw().code();
        Allure.step("Check code", () -> {
            Assertions.assertEquals(
                    401,
                    code
            );
        });

    }

    @Test
    @TestMuseum(count = 5)
    @DisplayName("Search museum by title")
    void checkSearchMuseumByTitle(MuseumJson[] museumJsons) throws Exception {
        MuseumList responseByTitle = gatewayApiClient.getAllMuseum(0,1,museumJsons[0].title());

        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseByTitle.content().size()

            );
        });

        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    museumJsons[0].title(),
                    responseByTitle.content().get(0).title()

            );
        });
    }

    @Test
    @DisplayName("Get all country returned by page")
    void checkPaginOnGetAllCountry() throws Exception {
        CountryList responseOnePage = gatewayApiClient.getAllCountry(0,1);
        CountryList responseToPage = gatewayApiClient.getAllCountry(1,1);


        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseOnePage.content().size()

            );
        });

        Allure.step("Check id one page", () -> {
            Assertions.assertEquals(
                    "Австралия",
                    responseOnePage.content().get(0).name()

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
                    "Австрия",
                    responseToPage.content().get(0).name()

            );
        });

    }

    private MuseumResponce museumJsonToMuseumResponce(MuseumJson museumJson){
        MuseumRepository museumRepository = new MuseumRepositoryHibernate();
        CountryEntity countryByName = museumRepository.findCountryByName(museumJson.country());
        CountryJson countryJson = new CountryJson(
                countryByName.getId(),
                museumJson.country()
        );
        GeoJson geoJson = new GeoJson(
                null,
                museumJson.city(),
                countryJson
        );
        return new MuseumResponce(
                museumJson.id(),
                museumJson.title(),
                museumJson.description(),
                museumJson.photo(),
                geoJson
        );
    }
}
