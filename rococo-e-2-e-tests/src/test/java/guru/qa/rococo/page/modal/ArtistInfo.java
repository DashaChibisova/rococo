package guru.qa.rococo.page.modal;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ArtistInfo extends BaseComponent<ArtistInfo> {

    public ArtistInfo() {
        super($(".card"));
    }

    private final SelenideElement editBtn = $("button[data-testid='edit-artist']");
    private final SelenideElement addPaintingUnderPhoto = $(".card .flex .variant-filled-primary");
    private final SelenideElement addPaintingUnderPainting = $(".card .rounded-container-token");
    private final SelenideElement cardHeader = $(".card .card-header");
    private final SelenideElement biographyInfo = $(".card .grid");
    protected final ArtistCard artistCard = new ArtistCard();


    @Step("Edit button click")
    public ArtistInfo editClick() {
        editBtn.click();
        return this;
    }

    @Step("Add painting click")
    public ArtistInfo addPaintingClick() {
        addPaintingUnderPainting.shouldHave(text("Добавить картину")).click();
        return this;
    }

    @Step("Check name artist")
    public ArtistInfo nameArtist(String name) {
        cardHeader.should(visible).shouldHave(text(name));
        return this;
    }

    @Step("Check biography artist")
    public ArtistInfo biographyArtist(String biography) {
        biographyInfo.should(visible).shouldHave(text(biography));
        return this;
    }

    @Step("Get new artist fragment")
    public ArtistCard getCardArtist() {
        return artistCard;
    }

    @Step("Check card without authorization")
    public ArtistInfo checkCardArtistWithoutAuthorization(String name, String biography) {
        editBtn.shouldNotBe(visible);
        addPaintingUnderPhoto.shouldNotBe(visible);
        addPaintingUnderPainting.shouldNotBe(visible);
        nameArtist(name);
        biographyArtist(biography);
        return this;
    }

    @Step("Empty list painting")
    public ArtistInfo checkEmptyListPainting() {
        getSelf().should(visible).shouldHave(text("Пока что список картин этого художника пуст."));
        return this;
    }

//    @Step("Check that page is disappear")
//    public ArtistInfo waitForNewArtistDisappear() {
//        getSelf().shouldNotBe(visible);
//        return this;
//    }
}

