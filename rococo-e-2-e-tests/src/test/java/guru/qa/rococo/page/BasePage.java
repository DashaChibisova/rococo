package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.page.message.Msg;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {

    protected static final Config CFG = Config.getInstance();

    protected final SelenideElement toaster = $(".snackbar");

    @SuppressWarnings("unchecked")
    @Step("Check that success message appears")
    public T checkMessage(Msg msg) {
        toaster.shouldHave(text(msg.getMessage()));
        return (T) this;
    }

    @Step("Check that success message appears")
    public T checkMessageName(Msg msg, String name) {
        toaster.shouldHave(text(msg.getMessage() + name));
        return (T) this;
    }

}
