package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgProfile implements Msg {
    PROFILE_MSG("Профиль обновлен");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
