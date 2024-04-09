package guru.qa.rococo.page.modal;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MuseumInfo extends BaseComponent<MuseumInfo>{

    public MuseumInfo() {
        super($(".card"));
    }

    private final SelenideElement editBtn = $("button[data-testid='edit-artist']");
    private final SelenideElement addPaintingUnderPhoto = $(".card .flex .variant-filled-primary");
    private final SelenideElement addPaintingUnderPainting = $(".card .rounded-container-token");
    private final SelenideElement cardHeader = $(".card .card-header");
    private final SelenideElement contentInfo = $(".card .grid");
    private final SelenideElement contentInfo2 = $(".card .grid");
    protected final MuseumCardSave museumCardSave = new MuseumCardSave();


    @Step("Edit button click")
    public MuseumInfo editClick() {
        editBtn.click();
        return this;
    }

    @Step("Add painting click")
    public MuseumInfo addPaintingClick() {
        addPaintingUnderPainting.shouldHave(text("Добавить картину")).click();
        return this;
    }

    @Step("Check name artist")
    public MuseumInfo nameMuseum(String title) {
        cardHeader.should(visible).shouldHave(text(title));
        return this;
    }

    @Step("Check description about museum")
    public MuseumInfo descriptionMuseum(String description) {
        contentInfo.should(visible).shouldHave(text(description));

        return this;
    }

    @Step("Get new artist fragment")
    public MuseumCardSave getCardMuseum() {
        return museumCardSave;
    }

    @Step("Check card without authorization")
    public MuseumInfo checkCardMuseumWithoutAuthorization(String title, String description) {
//        editBtn.shouldNotBe(visible);
//        addPaintingUnderPhoto.shouldNotBe(visible);
//        addPaintingUnderPainting.shouldNotBe(visible);
        nameMuseum(title);
        descriptionMuseum(description);
        return this;
    }

    @Step("Empty list painting")
    public MuseumInfo checkEmptyListPainting() {
        getSelf().should(visible).shouldHave(text("Пока что список картин этого художника пуст."));
        return this;
    }

    @Step("Check title museum")
    public MuseumInfo titleMuseum(String title) {
        cardHeader.should(visible).shouldHave(text(title));
        return this;
    }

//    @Step("Check museum photo")
//    public MuseumInfo checkPhoto(String imageName) {
//        contentInfo2.$$("img").find(photoFromClasspath(imageName))
//                .shouldHave(appear);
//        return this;
//    }

//    @Step("Check that page is disappear")
//    public ArtistInfo waitForNewArtistDisappear() {
//        getSelf().shouldNotBe(visible);
//        return this;
//    }
}

