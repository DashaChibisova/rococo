package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.CountryEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record CountryJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("name")
    String name
) {
    public static @Nonnull
    CountryJson fromEntity(@Nonnull CountryEntity entity) {
        return new CountryJson(
                entity.getId(),
                entity.getName()
        );
    }

}
