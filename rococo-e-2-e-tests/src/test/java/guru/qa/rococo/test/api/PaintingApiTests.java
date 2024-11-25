package guru.qa.rococo.test.api;

import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.CountryEntity;
import guru.qa.rococo.db.model.MuseumEntity;
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

public class PaintingApiTests extends BaseApiTest {

    @Test
    @DisplayName("Returned current painting")
    @TestPainting()
    void currentPaintingShouldBeReturned(PaintingJson[] paintingJsons) throws Exception {
        PaintingResponce response = gatewayApiClient.currentPainting(paintingJsons[0].id());
        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].title(),
                    response.title()
            );
        });

        Allure.step("Check description", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].description(),
                    response.description()
            );
        });

        Allure.step("Check content", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].content(),
                    response.content()
            );
        });

        Allure.step("Check museum", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].museum(),
                    response.museum().id().toString()
            );
        });

        Allure.step("Check artist", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].artist(),
                    response.artist().id().toString()
            );
        });
    }

    @Test
    @DisplayName("Save current painting")
    @ApiLogin(user = @TestUser)
    @TestMuseum
    @TestArtist
    void savePaintingWithTokenShouldBeReturned(@Token String bearerToken, MuseumJson[] museumJsons, ArtistJson[] artistJsons) throws Exception {
        PaintingRepository paintingRepository = new PaintingRepositoryHibernate();
        PaintingJson paintingJson = new PaintingJson(
                null,
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("images/painting.png").toString(),
                null,
                null
        );
        PaintingResponce painting = paintingJsonToPaintingResponce(museumJsons[0], artistJsons[0], paintingJson);
        PaintingResponce response = gatewayApiClient.savePainting(bearerToken, painting).body();

        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    painting.title(),
                    response.title()
            );
        });

        Allure.step("Check description", () -> {
            Assertions.assertEquals(
                    painting.description(),
                    response.description()
            );
        });

        Allure.step("Check content", () -> {
            Assertions.assertEquals(
                    painting.content(),
                    response.content()
            );
        });

        Allure.step("Check museum", () -> {
            Assertions.assertEquals(
                    painting.museum(),
                    response.museum()
            );
        });

        Allure.step("Check artist", () -> {
            Assertions.assertEquals(
                    painting.artist(),
                    response.artist()
            );
        });

        paintingRepository.deletePaintingByTitle(response.title());

    }

    @Test
    @DisplayName("Dont save painting without authorization")
    @TestMuseum
    @TestArtist
    void savePaintingWithoutTokenNotAllowed(MuseumJson[] museumJsons, ArtistJson[] artistJsons) throws Exception {

        PaintingJson paintingJson = new PaintingJson(
                null,
                DataUtils.generateRandomUsername(),
                DataUtils.generateRandomSentence(6),
                FileUtils.encodedFileBytes("images/painting.png").toString(),
                null,
                null
        );
        PaintingResponce painting = paintingJsonToPaintingResponce(museumJsons[0], artistJsons[0], paintingJson);
        int code = gatewayApiClient.savePainting("", painting).raw().code();
        Allure.step("Check code", () -> {
            Assertions.assertEquals(
                    401,
                    code
            );
        });

    }

    @Test
    @DisplayName("Update painting")
    @ApiLogin(user = @TestUser)
    @TestPainting()
    void updateInfoPaintingWithTokenShouldBeReturned(@Token String bearerToken, PaintingJson[] paintingJsons) throws Exception {
        String newTitle = DataUtils.generateRandomUsername();
        String newDescription = DataUtils.generateRandomSentence(6);

        PaintingJson paintingJson = new PaintingJson(
                paintingJsons[0].id(),
                newTitle,
                newDescription,
                paintingJsons[0].content(),
                paintingJsons[0].museum(),
                paintingJsons[0].artist()
        );

        MuseumRepository museumRepository = new MuseumRepositoryHibernate();
        ArtistRepository artistRepository = new ArtistRepositoryHibernate();
        MuseumEntity museumById = museumRepository.findMuseumById(UUID.fromString(paintingJsons[0].museum()));
        ArtistEntity artistById = artistRepository.findArtistById(UUID.fromString(paintingJsons[0].artist()));
        PaintingResponce painting = paintingJsonToPaintingResponce(MuseumJson.fromEntity(museumById), ArtistJson.fromEntity(artistById), paintingJson);
        PaintingResponce response = gatewayApiClient.updateInfoPainting(bearerToken, painting).body();

        Allure.step("Check id", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].id(),
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
    @TestPainting
    @DisplayName("Dont update painting without authorization")
    void updateInfoPaintingWithoutTokenNotAllowed(PaintingJson[] paintingJsons) throws Exception {
        String newTitle = DataUtils.generateRandomUsername();
        String newDescription = DataUtils.generateRandomSentence(6);

        PaintingJson paintingJson = new PaintingJson(
                paintingJsons[0].id(),
                newTitle,
                newDescription,
                paintingJsons[0].content(),
                paintingJsons[0].museum(),
                paintingJsons[0].artist()
        );

        MuseumRepository museumRepository = new MuseumRepositoryHibernate();
        ArtistRepository artistRepository = new ArtistRepositoryHibernate();
        MuseumEntity museumById = museumRepository.findMuseumById(UUID.fromString(paintingJsons[0].museum()));
        ArtistEntity artistById = artistRepository.findArtistById(UUID.fromString(paintingJsons[0].artist()));
        PaintingResponce painting = paintingJsonToPaintingResponce(MuseumJson.fromEntity(museumById), ArtistJson.fromEntity(artistById), paintingJson);

        int code = gatewayApiClient.updateInfoPainting(null, painting).raw().code();
        Allure.step("Check code", () -> {
            Assertions.assertEquals(
                    401,
                    code
            );
        });

    }

    @Test
    @TestPainting(count = 5)
    @DisplayName("Search all painting returned")
    void checkSearchPaintingByTitle(PaintingJson[] paintingJsons) throws Exception {
        PaintingList responseName = gatewayApiClient.getAllPainting(0, 1, paintingJsons[0].title());

        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseName.content().size()

            );
        });

        Allure.step("Check title", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].title(),
                    responseName.content().get(0).title()

            );
        });
    }

    @Test
    @DisplayName("Returned painting by author")
    @ApiLogin(user = @TestUser)
    @TestPainting(count = 5)
    void paintingByAuthorShouldBeReturned(PaintingJson[] paintingJsons) throws Exception {

        PaintingList painting = gatewayApiClient.getPaintingByAuthor(UUID.fromString(paintingJsons[0].artist()), 0, 2);

        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    painting.content().size()

            );
        });

        Allure.step("Check id painting", () -> {
            Assertions.assertEquals(
                    UUID.fromString(paintingJsons[0].artist()),
                    painting.content().get(0).artist().id()
            );
        });

    }


    private PaintingResponce paintingJsonToPaintingResponce(MuseumJson museumJson, ArtistJson artistJson, PaintingJson paintingJson) {
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
        MuseumResponce museumResponce = new MuseumResponce(
                museumJson.id(),
                museumJson.title(),
                museumJson.description(),
                museumJson.photo(),
                geoJson
        );

        return new PaintingResponce(
                paintingJson.id(),
                paintingJson.title(),
                paintingJson.description(),
                paintingJson.content(),
                museumResponce,
                artistJson
        );
    }
}
