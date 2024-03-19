package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.CountryEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

public record MuseumJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("name")
    String name
) {
    public static @Nonnull
    MuseumJson fromEntity(@Nonnull CountryEntity entity) {
        return new MuseumJson(
                entity.getId(),
                entity.getName()
        );
    }
}
