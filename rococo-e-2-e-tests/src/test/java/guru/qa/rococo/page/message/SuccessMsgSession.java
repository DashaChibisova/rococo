package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgSession implements Msg {
    SESSION_MSG("Сессия завершена");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
