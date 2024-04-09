package guru.qa.rococo.page.modal;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtistCard extends BaseComponent<ArtistCard>{

    public ArtistCard() {
        super($(".card"));
    }

    private final SelenideElement inputName = $("input[name='name']");
    private final SelenideElement inputBiography = $("textarea[name='biography']");
    private final SelenideElement photoInput = $("input[name='photo']");
    private final SelenideElement addBtn = $(".card .variant-filled-primary");
    private final SelenideElement closeBtn = $(".card .variant-ringed");

    @Step("Check that page is loaded")
    public ArtistCard waitForNewArtistLoaded() {
        getSelf().should(visible).shouldHave(text("Новый художник"));
        getSelf().should(visible).shouldHave(text("Заполните форму, чтобы добавить нового художника"));
        return this;
    }

    @Step("Check that page is loaded")
    public ArtistCard waitForEditArtistLoaded() {
        getSelf().should(visible).shouldHave(text("Редактировать художника"));
        getSelf().should(visible).shouldHave(text("Обновить изображение художника"));
        return this;
    }

    @Step("Set name")
    public ArtistCard setName(String name) {
        inputName.setValue(name);
        return this;
    }

    @Step("Set biography")
    public ArtistCard setBiography(String biography) {
        inputBiography.setValue(biography);
        return this;
    }

    @Step("Add photo from classpath")
    public ArtistCard addPhoto(String imagePath) {
        photoInput.uploadFromClasspath(imagePath);
        return this;
    }

    @Step("Click add button")
    public ArtistCard addBtnClick() {
        addBtn.click();
    return this;
    }

    @Step("Click close button")
    public ArtistCard closeBtnClick() {
        closeBtn.click();
    return this;
    }

    @Step("Check that page is disappear")
    public ArtistCard waitForNewArtistDisappear() {
        getSelf().shouldNotBe(visible);
        return this;
    }

    @Step("Error registration")
    public ArtistCard errorArtistLengthChar() {
        getSelf().should(visible).shouldHave(text("Имя не может быть короче 3 символов"));
        getSelf().should(visible).shouldHave(text("Биография не может быть короче 10 символов"));
        return new ArtistCard();
    }
}

