package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.rococo.condition.PhotoCondition.photoFromClasspath;

public class MainPage extends BasePage<MainPage> {
    public static final String PAGE_URL = CFG.frontUrl();

    private final SelenideElement pageContent = $("#page-content");

    private final SelenideElement shellHeaderContent = $("#shell-header");

    private final SelenideElement paintingBtn = $("#shell-header a[href*='/painting']");
    private final SelenideElement artistBtn = $("li:has(a[href*='/artist'])");
    private final SelenideElement museumBtn = $("#shell-header a[href*='/museum']");

    private final SelenideElement lightSwitch = $("a[title='Toggle Dark Mode']");
    private final SelenideElement submitBtn = $(".variant-filled-primary");
    private final SelenideElement avatarBtn = $(".btn-icon");
    private final SelenideElement avatarImage = $(".avatar-image");
    private final SelenideElement avatarNotSelected = $(".avatar-initials");

    @Step("Check that page is loaded")
    public MainPage waitForPageLoaded() {
        pageContent.should(visible).shouldHave(text("Ваши любимые картины и художники всегда рядом"));
        pageContent.should(visible).shouldHave(text("Картины"));
        pageContent.should(visible).shouldHave(text("Художники"));
        pageContent.should(visible).shouldHave(text("Музеи"));
        return this;
    }

    @Step("Open painting page from header")
    public PaintingPage toPaintingPageFromHeader() {
        paintingBtn.shouldHave(text("Картины")).click();
        return new PaintingPage();
    }

    @Step("Open artist page from header")
    public ArtistPage toArtistPageFromHeader() {
        artistBtn.shouldHave(text("Художники")).click();
        return new ArtistPage();
    }

    @Step("Open museum page from content")
    public MuseumPage toMuseumPageFromContent() {
        museumBtn.shouldHave(text("Музеи")).click();
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

    @Step("Login page from header")
    public LoginPage toLoginAppiar() {
        submitBtn.should(visible).shouldHave(text("Войти"));
        return new LoginPage();
    }

    @Step("Open login page from header")
    public MainPage toMainPageByLoginBtn() {
        submitBtn.shouldHave(text("Войти")).click();
        avatarBtn.shouldBe(visible);
        return this;
    }

    @Step("Open profile page after login")
    public MainPage goToProfile() {
        avatarBtn.click();
        return this;
    }

    @Step("Check avatar")
    public MainPage checkAvatar(String imageName) {
        avatarImage.shouldHave(photoFromClasspath(imageName));
        return this;
    }

    @Step("Avatar not selected")
    public MainPage avatarNotSelected() {
        avatarNotSelected.shouldHave(text("AB"));
        return this;
    }
}
