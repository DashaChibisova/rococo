package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgAddArtist implements Msg {
    ARTIST_MSG("Добавлен художник: ");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
