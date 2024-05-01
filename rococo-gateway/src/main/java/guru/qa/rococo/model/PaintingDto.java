package guru.qa.rococo.model;

import java.util.ArrayList;

public record PaintingDto(
        ArrayList<PaintingJson> content,
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



