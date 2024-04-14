package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.MuseumEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record MuseumJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("geo")
        String city,
        String country) {

    public static @Nonnull
    MuseumJson fromEntity(@Nonnull MuseumEntity entity) {
        return new MuseumJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPhoto() != null && entity.getPhoto().length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null,
                entity.getGeo().getCity(),
                entity.getGeo().getCountry().getName()
        );
    }
}