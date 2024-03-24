package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ArtistJson(
        @JsonProperty("id")
        UUID id,
        @Size(max = 50, message = " Name can`t be longer than 50 characters")
        @JsonProperty("name")
        String name,
        @Size(max = 255, message = " Biography can`t be longer than 255 characters")
        @JsonProperty("biography")
        String biography,
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String photo) {
}
