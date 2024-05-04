package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record MuseumResponce(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("geo")
        GeoJson geo) {
}
