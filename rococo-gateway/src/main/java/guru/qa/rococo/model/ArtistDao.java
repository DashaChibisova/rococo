package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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



