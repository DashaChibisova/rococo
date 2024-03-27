package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

  public static final String PAGE_URL = CFG.frontUrl() + "/profile";

  private final SelenideElement loginInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement registerBtn = $("a[href*='/register']"); //*???
//  open(ProfilePage.PAGE_URL, ProfilePage.class)



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
}
