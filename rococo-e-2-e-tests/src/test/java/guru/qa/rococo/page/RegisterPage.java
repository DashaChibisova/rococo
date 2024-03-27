package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage<RegisterPage> {

    private final SelenideElement inputUsername = $("input[name='username']");
    private final SelenideElement inputPassword = $("input[name='password']");
    private final SelenideElement inputPasswordSubmit = $("input[name='passwordSubmit']");
    private final SelenideElement signUpBtn = $("button[type='submit']");

    @Step("Set username")
    public RegisterPage setUsername(String username) {
        inputUsername.setValue(username);
        return this;
    }

    @Step("Set password")
    public RegisterPage setPassword(String password) {
        inputPassword.setValue(password);
        inputPasswordSubmit.setValue(password);
        return this;
    }

    @Step("Click on button sign")
    public void signUpClick() {
        signUpBtn.click();
    }

    @Step("Click on button submit")
    public LoginPage submitClick() {
        signUpBtn.click();
        return new LoginPage();

    }
}
