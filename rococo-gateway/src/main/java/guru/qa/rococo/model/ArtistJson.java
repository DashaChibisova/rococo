package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ArtistJson(
        @JsonProperty("id")
        UUID id,
        @Size(min = 3, max = 12, message = "Name can`t be longer than 50 characters")
        @NotNull(message = "Name can not be null")
        @JsonProperty("name")
        String name,
        @Size(max = 255, message = " Biography can`t be longer than 255 characters")
        @NotNull(message = "Biography can not be null")
        @JsonProperty("biography")
        String biography,
        @NotNull(message = "Photo can not be null")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String photo) {

}
