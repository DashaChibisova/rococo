package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgEditMuseum implements Msg {
  MUSEUM_MSG("Обновлен музей: ");

  private final String msg;

  @Override
  public String getMessage() {
    return msg;
  }
}
