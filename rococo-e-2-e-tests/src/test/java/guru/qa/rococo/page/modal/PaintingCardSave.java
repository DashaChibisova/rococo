package guru.qa.rococo.page.modal;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.PaintingPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static guru.qa.rococo.condition.PhotoCondition.photoFromClasspath;

public class PaintingCardSave extends BaseComponent<PaintingCardSave> {

    public PaintingCardSave() {
        super($(".card"));
    }

    private final SelenideElement inputTitle = $("input[name='title']");
    private final SelenideElement inputCity = $("input[name='city']");
    private final SelenideElement inputDescription = $("textarea[name='description']");
    private final ElementsCollection inputAuthorId = $$("[name='authorId'] option");
    private final ElementsCollection inputMuseumId = $$("[name='museumId'] option");
    private final SelenideElement photoInput = $("input[name='content']");
    private final SelenideElement addBtn = $(".card .variant-filled-primary");
    private final SelenideElement closeBtn = $(".card .variant-ringed");

    @Step("Check that page is loaded")
    public PaintingCardSave waitForNewPaintingLoaded() {
        getSelf().should(visible).shouldHave(text("Название картины"));
        getSelf().should(visible).shouldHave(text("Загрузите изображение картины"));
        getSelf().should(visible).shouldHave(text("Укажите автора картины"));
        getSelf().should(visible).shouldHave(text("Описание картины"));
        getSelf().should(visible).shouldHave(text("Укажите, где хранится оригинал"));
        return this;
    }

    @Step("Check that page is loaded")
    public PaintingCardSave waitForEditPaintingLoaded() {
//        getSelf().should(visible).shouldHave(text("Редактировать художника"));
//        getSelf().should(visible).shouldHave(text("Обновить изображение художника"));
        return this;
    }

    @Step("Set title")
    public PaintingCardSave setTitle(String title) {
        inputTitle.setValue(title);
        return this;
    }

    @Step("Set city")
    public PaintingCardSave setCity(String city) {
        inputCity.setValue(city);
        return this;
    }

    @Step("Select author")
    public PaintingCardSave selectAuthor(String author) {
        inputAuthorId.findBy(text(author)).click();
        return this;
    }

    @Step("Select museum")
    public PaintingCardSave selectMuseum(String museum) {
        inputMuseumId.findBy(text(museum)).click();
        return this;
    }

    @Step("Set description")
    public PaintingCardSave setDescription(String description) {
        inputDescription.setValue(description);
        return this;
    }

    @Step("Add photo from classpath")
    public PaintingCardSave addPhoto(String imagePath) {
        photoInput.uploadFromClasspath(imagePath);
        return this;
    }

    @Step("Click add button")
    public PaintingCardSave addBtnClick() {
        addBtn.click();
        return this;
    }


    @Step("Click close button")
    public PaintingCardSave closeBtnClick() {
        closeBtn.click();
        return this;
    }

    @Step("Check that page is disappear")
    public PaintingCardSave waitForNewPaintingDisappear() {
        getSelf().shouldNotBe(visible);
        return this;
    }

    @Step("Error registration")
    public PaintingCardSave errorPaintingLengthChar() {
        getSelf().should(visible).shouldHave(text("Название не может быть короче 3 символов"));
        getSelf().should(visible).shouldHave(text("Описание не может быть короче 10 символов"));
        return new PaintingCardSave();
    }

    @Step("Add painting")
    public PaintingCardSave checkAvatarPainting(String imageName) {
        getSelf().$$("li .rounded-lg")
                .find(photoFromClasspath(imageName))
                .shouldHave(appear);
        return this;
    }

    @Step("Check painting title")
    public PaintingCardSave titlePainting(String title) {
        getSelf().should(appear).shouldHave(text(title));
        return this;
    }
}

