package guru.qa.rococo.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsgEditArtist implements Msg {
    ARTIST_MSG("Обновлен художник: ");

    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
