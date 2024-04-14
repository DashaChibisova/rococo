package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.db.model.PaintingEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record PaintingJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("content")
        String content,
        @JsonProperty("museum")
        String museum,
        @JsonProperty("artist")
        String artist) {


    public static @Nonnull
    PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
        return new PaintingJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent() != null && entity.getContent().length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null,
                entity.getMuseum().toString(),
                entity.getArtist().toString()
        );
    }
}
