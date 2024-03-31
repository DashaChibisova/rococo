package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMsgAvatar implements Msg {
  PROFILE_MSG("size must be between 0 and 1048576");

  private final String msg;

  @Override
  public String getMessage() {
    return msg;
  }
}
