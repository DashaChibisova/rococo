package guru.qa.rococo.jupiter.model;

import java.util.ArrayList;

public record CountryList(
        ArrayList<CountryJson> content,
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


