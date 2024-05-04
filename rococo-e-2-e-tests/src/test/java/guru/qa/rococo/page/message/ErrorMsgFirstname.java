package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMsgFirstname implements Msg {
    FIRSTNAME_MSG("First name can`t be longer than 30 characters");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
