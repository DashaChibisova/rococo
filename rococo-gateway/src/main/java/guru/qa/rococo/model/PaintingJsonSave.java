package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PaintingJsonSave(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("content")
        String content,
        @JsonProperty("museum")
        UUID museumId,
        @JsonProperty("artist")
        UUID artistId) {
}
