package guru.qa.rococo.test.api;


import guru.qa.rococo.jupiter.annotation.*;
import guru.qa.rococo.jupiter.model.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

@Isolated
public class PaginApiTests extends BaseApiTest {

    @Test
    @Artist(count = 5)
    @DisplayName("Get all artist returned by page")
    void checkPaginOnGetAllArtist(ArtistJson[] artist) throws Exception {
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

    @Test
    @TestPainting(count = 5)
    @DisplayName("Get all painting returned by page")
    void checkPaginOnGetAllPainting(PaintingJson[] paintingJsons) throws Exception {
        PaintingList responseOnePage = gatewayApiClient.getAllPainting(0,1,"");
        PaintingList responseToPage = gatewayApiClient.getAllPainting(1,1,"");
        PaintingList responseName = gatewayApiClient.getAllPainting(1,1,paintingJsons[0].title());


        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseOnePage.content().size()

            );
        });

        Allure.step("Check id one page", () -> {
            Assertions.assertEquals(
                    paintingJsons[0].id(),
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
                    paintingJsons[1].id(),
                    responseToPage.content().get(0).id()

            );
        });

    }

    @Test
    @TestMuseum(count = 5)
    @DisplayName("Get all museum returned by page")
    void checkPaginOnGetAllMuseum(MuseumJson[] museumJsons) throws Exception {
        MuseumList responseOnePage = gatewayApiClient.getAllMuseum(0,1,"");
        MuseumList responseToPage = gatewayApiClient.getAllMuseum(1,1,"");


        Allure.step("Check size one page", () -> {
            Assertions.assertEquals(
                    1,
                    responseOnePage.content().size()

            );
        });

        Allure.step("Check id one page", () -> {
            Assertions.assertEquals(
                    museumJsons[0].id(),
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
                    museumJsons[1].id(),
                    responseToPage.content().get(0).id()

            );
        });

    }
}
