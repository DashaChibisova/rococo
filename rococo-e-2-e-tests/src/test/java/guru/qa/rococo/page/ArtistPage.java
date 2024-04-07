package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ArtistPage {

    private final SelenideElement pageContent = $("#page-content"); //id
    private final SelenideElement addArtistBtn = $(".variant-filled-primary");
    private final SelenideElement searchLine = $("input[type='search']");
    private final SelenideElement iconeSearchBtn = $(".variant-soft-surface");


    @Step("Check that page is loaded")
    public ArtistPage waitForPageLoaded() {
        pageContent.should(visible).shouldHave(text("Художники"));
        pageContent.should(visible).shouldHave(text("Искать художников..."));
        return this;
    }

    @Step("Button add artist is absent")
    public ArtistPage btnAddArtistIsAbsent() {
        addArtistBtn.should(disappear);
        return this;
    }

    @Step("Search for artist by name")
    public ArtistPage searchArtist(String name) {
        searchLine.setValue(name);
        iconeSearchBtn.click()
        return this;
    }
}
