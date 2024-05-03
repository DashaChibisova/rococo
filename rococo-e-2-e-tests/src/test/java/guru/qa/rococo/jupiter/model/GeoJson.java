package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record GeoJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("city")
        String city,
        @JsonProperty("country")
        CountryJson country
) {
}
