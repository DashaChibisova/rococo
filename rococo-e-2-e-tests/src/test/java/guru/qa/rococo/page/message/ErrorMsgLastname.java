package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMsgLastname implements Msg {
  LASTNAME_MSG("Lastname can`t be longer than 50 characters");

  private final String msg;

  @Override
  public String getMessage() {
    return msg;
  }
}
