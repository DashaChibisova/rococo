package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.modal.ArtistCard;
import guru.qa.rococo.page.modal.ArtistInfo;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.rococo.condition.PhotoCondition.photoFromClasspath;

public class ArtistPage extends BasePage<ArtistPage> {
    public static final String PAGE_URL = CFG.frontUrl() + "/artist";
    protected final ArtistCard artistCard = new ArtistCard();
    protected final ArtistInfo artistInfo = new ArtistInfo();
    private final SelenideElement pageContent = $("#page-content");
    private final SelenideElement addArtistBtn = $("#page-content .variant-filled-primary");
    private final SelenideElement artistDataTable = $("#page-content .grid");
    private final SelenideElement searchLine = $("input[type='search']");
    private final SelenideElement iconeSearchBtn = $(".variant-soft-surface");
    private final SelenideElement titleSearchBtn = $(byTitle("Искать художников..."));
    private final SelenideElement avatarImage = $("#page-content .avatar-image");

    @Step("Check that page is loaded")
    public ArtistPage waitForPageLoaded() {
        pageContent.should(visible).shouldHave(text("Художники"));
        titleSearchBtn.should(visible);
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
        iconeSearchBtn.click();
        return this;
    }

    @Step("Search for artist by name")
    public ArtistPage artistVisible(String name) {
        artistDataTable.$$("li")
                .find(text(name))
                .shouldHave(appear);
        return this;
    }

    @Step("Artist size")
    public int artistSize() {
        return artistDataTable.$$("li").size();
    }

    @Step("Add artist")
    public ArtistPage addArtistClick() {
        addArtistBtn.should(visible).click();
        return this;
    }

    @Step("Add artist")
    public ArtistPage checkAvatar(String imageName) {
        artistDataTable.$$("li .avatar-image")
                .find(photoFromClasspath(imageName))
                .shouldHave(appear);
        return this;
    }

    @Step("Select artist")
    public ArtistPage selectArtist(String name) {
        artistDataTable.$$("li")
                .find(text(name))
                .click();
        return this;
    }


    @Step("Get new artist fragment")
    public ArtistCard getCardArtist() {
        return artistCard;
    }

    @Step("Get new artist info")
    public ArtistInfo getArtistInfo() {
        return artistInfo;
    }

}

