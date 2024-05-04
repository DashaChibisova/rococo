package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgAddPaintingArtist implements Msg {
    PAINTING_MSG("Добавлена картина: ");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
