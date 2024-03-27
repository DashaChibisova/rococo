package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage<MainPage> {

  private final SelenideElement pageContent = $("#page-content"); //id

  private final SelenideElement shellHeaderContent = $("#shell-header"); //id $(".app-bar")

//  private final SelenideElement paintingBtn = $("a[href*='/painting']"); //*???
//  private final SelenideElement artistBtn = $("a[href*='/artist']");
//  private final SelenideElement museumBtn = $("a[href*='/museum']");

  private final SelenideElement lightSwitch = $("//a[@title='Toggle Dark Mode']");
  private final SelenideElement submitBtn = $(".btn variant-filled-primary");
  private final SelenideElement avatarBtn = $(".btn-icon");

  @Step("Open painting page from header")
  public PaintingPage toPaintingPageFromHeader() {
    shellHeaderContent.shouldHave(text("Картины")).click();
    return new PaintingPage();
  }

  @Step("Open artist page from heade")
  public ArtistPage toArtistPageFromHeader() {
    shellHeaderContent.shouldHave(text("Художники")).click();
    return new ArtistPage();
  }

  @Step("Open museum page from content")
  public MuseumPage toMuseumPageFromContent() {
    shellHeaderContent.shouldHave(text("Музеи")).click();
    return new MuseumPage();
  }

  @Step("Open painting page from content")
  public PaintingPage toPaintingPageFromContent() {
    pageContent.shouldHave(text("Картины")).click();
    return new PaintingPage();
  }

  @Step("Open artist page from content")
  public ArtistPage toArtistPageFromContent() {
    pageContent.shouldHave(text("Художники")).click();
    return new ArtistPage();
  }

  @Step("Open museum page from header")
  public MuseumPage toMuseumPageFromHeader() {
    pageContent.shouldHave(text("Музеи")).click();
    return new MuseumPage();
  }

  @Step("Open login page from header")
  public LoginPage toLoginPage() {
    submitBtn.shouldHave(text("Войти")).click();
    return new LoginPage();
  }


  @Step("Light switch click")
  public MainPage lightSwitchClick() {
    lightSwitch.click();
    return this;
  }

  @Step("Open profile page after login")
  public MainPage goToProfile() {
    avatarBtn.click();
    return this;
  }
}
