package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgAddPainting implements Msg {
    PAINTING_MSG("Добавлена картины: ");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
