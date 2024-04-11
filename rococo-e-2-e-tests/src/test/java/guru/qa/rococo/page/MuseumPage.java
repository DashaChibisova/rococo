package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.modal.MuseumCardSave;
import guru.qa.rococo.page.modal.MuseumInfo;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.rococo.condition.PhotoCondition.photoFromClasspath;

public class MuseumPage extends BasePage<MuseumPage>{
    private final SelenideElement pageContent = $("#page-content"); //id
    private final SelenideElement addMuseumBtn = $("#page-content .variant-filled-primary");
    private final SelenideElement museumDataTable = $("#page-content .grid");
    private final SelenideElement searchLine = $("input[type='search']");

    private final SelenideElement iconeSearchBtn = $(".variant-soft-surface");
    private final SelenideElement titleSearchBtn = $(byTitle("Искать музей..."));

    protected final MuseumInfo museumInfo = new MuseumInfo();
    protected final MuseumCardSave museumCardSave = new MuseumCardSave();


    @Step("Check that page is loaded")
    public MuseumPage waitForPageLoaded() {
        pageContent.should(visible).shouldHave(text("Музеи"));
        titleSearchBtn.should(visible);
        return this;
    }

    @Step("Button add museum is absent")
    public MuseumPage btnAddArtistIsAbsent() {
        addMuseumBtn.should(disappear);
        return this;
    }

    @Step("Search for museum by name")
    public  MuseumPage museumVisible(String name) {
        museumDataTable.$$("li")
                .find(text(name))
                .shouldHave(appear);
        return this;
    }

    @Step("Select museum")
    public MuseumPage selectMuseum(String name) {
        museumDataTable.$$("li")
                .find(text(name))
                .scrollIntoView(true)
                .click();
        return this;
    }

    @Step("Search for museum by name")
    public MuseumPage searchMuseum(String title) {
        searchLine.setValue(title);
        iconeSearchBtn.click();
        return this;
    }

    @Step("Museum size")
    public int museumSize() {
        return museumDataTable.$$("li").size();
    }

    @Step("Add museum")
    public MuseumPage addMuseumClick() {
        addMuseumBtn.should(visible).shouldHave(text("Добавить музей")).click();
        return this;
    }

    @Step("Add museum")
    public MuseumPage checkAvatar(String imageName) {
        museumDataTable.$$("li .rounded-lg")
                .find(photoFromClasspath(imageName))
                .shouldHave(appear);
        return this;
    }

    @Step("Get new museum info")
    public MuseumInfo getMuseumInfo() {
        return museumInfo;
    }

    @Step("Get new museum card save")
    public MuseumCardSave getMuseumCardSave() {
        return museumCardSave;
    }
}
