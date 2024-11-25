package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name
) {
}
