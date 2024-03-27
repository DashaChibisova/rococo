package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.message.SuccessMsg;

import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage<ProfilePage> {

  public static final String PAGE_URL = CFG.frontUrl() + "/profile";

  private final SelenideElement inputFirstname = $("input[type='firstname']");
  private final SelenideElement inputSurname= $("input[type='surname']");
  private final SelenideElement closeBtn= $(".btn variant-ringed");
  private final SelenideElement updateBtn= $(".btn variant-filled-primary");

  private final SelenideElement avatar= $(".btn variant-filled-primary");//??


  public ProfilePage addAvatar(String imagePath) {
    avatar.click();
//    avatarInput.uploadFromClasspath(imagePath);
    checkMessage(SuccessMsg.PROFILE_MSG);
    return this;
  }

  public ProfilePage updateNameUser(String firstname, String surname) {
    inputFirstname.setValue(firstname);
    inputSurname.setValue(surname);
    return this;
  }

}
