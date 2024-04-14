package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.modal.MuseumCardSave;
import guru.qa.rococo.page.modal.MuseumInfo;
import guru.qa.rococo.page.modal.PaintingCardSave;
import guru.qa.rococo.page.modal.PaintingInfo;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.rococo.condition.PhotoCondition.photoFromClasspath;

public class PaintingPage extends BasePage<PaintingPage> {

    private final SelenideElement pageContent = $("#page-content"); //id
    private final SelenideElement addPaintingBtn = $("#page-content .variant-filled-primary");
    private final SelenideElement paintingDataTable = $("#page-content .grid");
    private final SelenideElement searchLine = $("input[type='search']");

    private final SelenideElement iconeSearchBtn = $(".variant-soft-surface");
    private final SelenideElement titleSearchBtn = $(byTitle("Искать картины..."));

    protected final PaintingInfo paintingInfo = new PaintingInfo();
    protected final PaintingCardSave paintingCardSave = new PaintingCardSave();

    @Step("Check that page is loaded")
    public PaintingPage waitForPageLoaded() {
        pageContent.should(visible).shouldHave(text("Картины"));
        titleSearchBtn.should(visible);
        return this;
    }

    @Step("Button add painting is absent")
    public PaintingPage btnAddPaintingIsAbsent() {
        addPaintingBtn.should(disappear);
        return this;
    }

    @Step("Search for painting by name")
    public  PaintingPage paintingVisible(String name) {
        paintingDataTable.$$("li")
                .find(text(name))
                .shouldHave(appear);
        return this;
    }

    @Step("Select painting")
    public PaintingPage selectPainting(String name) {
        paintingDataTable.$$("li")
                .find(text(name))
                .scrollIntoView(true)
                .click();
        return this;
    }

    @Step("Search for painting by name")
    public PaintingPage searchPainting(String title) {
        searchLine.setValue(title);
        iconeSearchBtn.click();
        return this;
    }

    @Step("Painting size")
    public int paintingSize() {
        return paintingDataTable.$$("li").size();
    }

    @Step("Add painting")
    public PaintingPage addPaintingClick() {
        addPaintingBtn.should(visible).shouldHave(text("Добавить картину")).click();
        return this;
    }

    @Step("Add painting")
    public PaintingPage checkAvatar(String imageName) {
        paintingDataTable.$$("li .rounded-lg")
                .find(photoFromClasspath(imageName))
                .shouldHave(appear);
        return this;
    }

    @Step("Get new painting info")
    public PaintingInfo getPaintingInfo() {
        return paintingInfo;
    }

    @Step("Get new painting card save")
    public PaintingCardSave getPaintingCardSave() {
        return paintingCardSave;
    }
}
