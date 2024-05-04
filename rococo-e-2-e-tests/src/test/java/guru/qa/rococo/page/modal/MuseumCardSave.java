package guru.qa.rococo.page.modal;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MuseumCardSave extends BaseComponent<MuseumCardSave> {

    public MuseumCardSave() {
        super($(".card"));
    }

    private final SelenideElement inputTitle = $("input[name='title']");
    private final SelenideElement inputCity = $("input[name='city']");
    private final SelenideElement inputDescription = $("textarea[name='description']");
    private final ElementsCollection inputCountry = $$("[name='countryId'] option");
    private final SelenideElement photoInput = $("input[name='photo']");
    private final SelenideElement addBtn = $(".card .variant-filled-primary");
    private final SelenideElement closeBtn = $(".card .variant-ringed");

    @Step("Check that page is loaded")
    public MuseumCardSave waitForNewMuseumLoaded() {
        getSelf().should(visible).shouldHave(text("Название музея"));
        getSelf().should(visible).shouldHave(text("Укажите страну"));
        getSelf().should(visible).shouldHave(text("Укажите город"));
        getSelf().should(visible).shouldHave(text("Изображение музея"));
        getSelf().should(visible).shouldHave(text("О музее"));
        return this;
    }

    @Step("Check that page is loaded")
    public MuseumCardSave waitForEditArtistLoaded() {
//        getSelf().should(visible).shouldHave(text("Редактировать художника"));
//        getSelf().should(visible).shouldHave(text("Обновить изображение художника"));
        return this;
    }

    @Step("Set title")
    public MuseumCardSave setTitle(String title) {
        inputTitle.setValue(title);
        return this;
    }

    @Step("Set city")
    public MuseumCardSave setCity(String city) {
        inputCity.setValue(city);
        return this;
    }

    @Step("Select country")
    public MuseumCardSave selectCountry(String country) {
        inputCountry.findBy(text(country)).click();
        return this;
    }

    @Step("Set description")
    public MuseumCardSave setDescription(String description) {
        inputDescription.setValue(description);
        return this;
    }

    @Step("Add photo from classpath")
    public MuseumCardSave addPhoto(String imagePath) {
        photoInput.uploadFromClasspath(imagePath);
        return this;
    }

    @Step("Click add button")
    public MuseumCardSave addBtnClick() {
        addBtn.click();
        return this;
    }


    @Step("Click close button")
    public MuseumCardSave closeBtnClick() {
        closeBtn.click();
        return this;
    }

    @Step("Check that page is disappear")
    public MuseumCardSave waitForNewMuseumDisappear() {
        getSelf().shouldNotBe(visible);
        return this;
    }

    @Step("Error registration")
    public MuseumCardSave errorMuseumLengthChar() {
        getSelf().should(visible).shouldHave(text("Название не может быть короче 3 символов"));
        getSelf().should(visible).shouldHave(text("Описание не может быть короче 10 символов"));
        getSelf().should(visible).shouldHave(text("Город не может быть короче 3 символов"));
        return new MuseumCardSave();
    }
}

