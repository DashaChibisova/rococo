package guru.qa.rococo.model;

import java.util.ArrayList;

public record CountryDao(
        ArrayList<CountryJson> content,
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



