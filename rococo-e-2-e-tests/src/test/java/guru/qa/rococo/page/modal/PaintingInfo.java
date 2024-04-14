package guru.qa.rococo.page.modal;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PaintingInfo extends BaseComponent<PaintingInfo>{

    public PaintingInfo() {
        super($(".card"));
    }

    private final SelenideElement editBtn = $("button[data-testid='edit-painting']");
    private final SelenideElement addPaintingUnderPhoto = $(".card .flex .variant-filled-primary");
    private final SelenideElement addPaintingUnderPainting = $(".card .rounded-container-token");
    private final SelenideElement cardHeader = $(".card .card-header");
    private final SelenideElement contentInfo = $(".card .grid");
    private final SelenideElement contentInfo2 = $(".card .grid");
    protected final PaintingCardSave paintingCardSave = new PaintingCardSave();


    @Step("Edit button click")
    public PaintingInfo editClick() {
        editBtn.click();
        return this;
    }

    @Step("Add painting click")
    public PaintingInfo addPaintingClick() {
        addPaintingUnderPainting.shouldHave(text("Добавить картину")).click();
        return this;
    }

    @Step("Check name painting")
    public PaintingInfo nameMuseum(String title) {
        cardHeader.should(visible).shouldHave(text(title));
        return this;
    }

    @Step("Check description about painting")
    public PaintingInfo descriptionMuseum(String description) {
        contentInfo.should(visible).shouldHave(text(description));

        return this;
    }

    @Step("Get new artist fragment")
    public PaintingCardSave getCardPainting() {
        return paintingCardSave;
    }

    @Step("Check card without authorization")
    public PaintingInfo checkCardPaintingWithoutAuthorization(String title, String description) {
        editBtn.shouldNotBe(visible);
        nameMuseum(title);
        descriptionMuseum(description);
        return this;
    }

    @Step("Empty list painting")
    public PaintingInfo checkEmptyListPainting() {
        getSelf().should(visible).shouldHave(text("Пока что список картин этого художника пуст."));
        return this;
    }

    @Step("Check title painting")
    public PaintingInfo titlePainting(String title) {
        cardHeader.should(visible).shouldHave(text(title));
        return this;
    }
}

