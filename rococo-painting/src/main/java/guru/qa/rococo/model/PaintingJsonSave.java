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



//    public static @Nonnull
//    PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
//        return new PaintingJson(
//                entity.getId(),
//                entity.getTitle(),
//                entity.getDescription(),
//                entity.getContent() != null && entity.getContent().length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null,
//                entity.getMuseum(),
//                entity.getArtist()
//        );
//    }
}
