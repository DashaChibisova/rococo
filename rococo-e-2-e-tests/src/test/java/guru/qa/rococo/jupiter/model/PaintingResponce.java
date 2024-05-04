package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.db.model.PaintingEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record PaintingResponce(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("content")
        String content,
        @JsonProperty("museum")
        MuseumResponce museum,
        @JsonProperty("artist")
        ArtistJson artist) {
}
