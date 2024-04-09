package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgAddMuseum implements Msg {
  MUSEUM_MSG("Добавлен музей: ");

  private final String msg;

  @Override
  public String getMessage() {
    return msg;
  }
}
