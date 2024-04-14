package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage<ProfilePage> {

    public static final String PAGE_URL = CFG.frontUrl() + "/profile";

    private final SelenideElement inputFirstname = $("input[name='firstname']");
    private final SelenideElement inputSurname = $("input[name='surname']");
    private final SelenideElement closeBtn = $(".variant-ringed");
    private final SelenideElement updateBtn = $(".variant-filled-primary");
    private final SelenideElement notAuthorizationBtn = $(".variant-ghost");

    private final SelenideElement avatarInput = $("input[type='file']");

    private final SelenideElement profileCard = $(".card");//??

    @Step("Check that page is loaded")
    public ProfilePage waitForPageLoaded() {
        profileCard.should(visible).shouldHave(text("Профиль"));
        profileCard.should(visible).shouldHave(text("Обновить фото профиля"));
        return this;
    }

    @Step("Add avatar from classpath")
    public ProfilePage addAvatar(String imagePath) {
        avatarInput.uploadFromClasspath(imagePath);
        return this;
    }

    @Step("Set firstname")
    public ProfilePage setFirstnameUser(String firstname) {
        inputFirstname.setValue(firstname);
        return this;
    }

    @Step("Set surname")
    public ProfilePage setSurnameUser(String surname) {
        inputSurname.setValue(surname);
        return this;
    }

    @Step("Update click")
    public ProfilePage updateClick() {
        updateBtn.click();
        return this;
    }

    @Step("Close click")
    public MainPage closeProfile() {
        closeBtn.click();
        return new MainPage();
    }

    @Step("Unauthorization click")
    public ProfilePage notAuthorization() {
        notAuthorizationBtn.click();
        return this;
    }

}
