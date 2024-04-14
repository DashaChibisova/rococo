package guru.qa.rococo.model;

import java.util.ArrayList;

public record ArtistDto(
        ArrayList<ArtistJson> content,
        PageDto pageable,
        int totalPages,
        int totalElements,
        boolean last,
        int size,
        int number,
        SortDao sort,
        int numberOfElements,
        boolean first,
        boolean empty
) {
}



