package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    private final SelenideElement loginInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitBtn = $("button[type='submit']");
    private final SelenideElement registerBtn = $("a[href*='/register']");
    private final SelenideElement formLogin = $("[action= '/login']");

    @Step("Check that page is loaded")
    public LoginPage waitForPageLoaded() {
        formLogin.should(visible).shouldHave(text("Нет аккаунта?"));
        formLogin.should(visible).shouldHave(text("Зарегистрироваться"));
        formLogin.should(visible).shouldHave(text("Войти"));
        formLogin.should(visible).shouldHave(text("Имя пользователя"));
        formLogin.should(visible).shouldHave(text("Пароль"));
        return this;
    }


    @Step("Set login")
    public LoginPage setLogin(String login) {
        loginInput.setValue(login);
        return this;
    }

    @Step("Set password")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Click submit")
    public void submit() {
        submitBtn.click();
    }


    @Step("Click register")
    public RegisterPage goToRegister() {
        registerBtn.click();
        return new RegisterPage();
    }

    @Step("Error login bad password")
    public LoginPage errorLogin() {
        formLogin.should(visible).shouldHave(text("Bad credentials"));
        return new LoginPage();
    }


}
