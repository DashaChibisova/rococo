package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage<RegisterPage> {

    private final SelenideElement inputUsername = $("input[name='username']");
    private final SelenideElement inputPassword = $("input[name='password']");
    private final SelenideElement inputPasswordSubmit = $("input[name='passwordSubmit']");
    private final SelenideElement signUpBtn = $("button[type='submit']");
    private final SelenideElement goInSystem = $(".form__submit");
    private final SelenideElement registerForm = $("#register-form");

    @Step("Set username")
    public RegisterPage setUsername(String username) {
        inputUsername.setValue(username);
        return this;
    }

    @Step("Set password")
    public RegisterPage setPassword(String password) {
        inputPassword.setValue(password);
        return this;
    }

    @Step("Set password repeat")
    public RegisterPage setPasswordRepeat(String password) {
        inputPasswordSubmit.setValue(password);
        return this;
    }

    @Step("Click on button sign")
    public void goInSystemClick() {
        goInSystem.shouldHave(text("Войти в систему")).click();
    }

    @Step("Click on button submit")
    public RegisterPage submitClick() {
        signUpBtn.click();
        return this;
    }

    @Step("Error registration")
    public LoginPage errorRegistrationLengthChar() {
        registerForm.should(visible).shouldHave(text("Username can`t be longer than 50 characters"));
        registerForm.should(visible).shouldHave(text("Allowed password length should be from 3 to 12 characters"));
        return new LoginPage();
    }

    @Step("Error registration different password")
    public LoginPage errorRegistrationDifferentPassword() {
        registerForm.should(visible).shouldHave(text("Passwords should be equal"));
        return new LoginPage();
    }
}
