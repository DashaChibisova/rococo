package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgEditPainting implements Msg {
    PAINTING_MSG("Обновлена картина: ");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
