package guru.qa.rococo.model;

import java.util.ArrayList;

public record ArtistDao(
        ArrayList<ArtistJson> content,
        String pageable,
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



