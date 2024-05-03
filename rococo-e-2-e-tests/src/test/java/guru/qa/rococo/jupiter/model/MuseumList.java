package guru.qa.rococo.jupiter.model;

import java.util.ArrayList;

public record MuseumList(
        ArrayList<MuseumResponce> content,
        PageDto pageable,
        int totalPages,
        int totalElements,
        boolean last,
        int size,
        int number,
        SortDto sort,
        int numberOfElements,
        boolean first,
        boolean empty
) {
}



