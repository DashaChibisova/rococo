package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("lastname")
        String lastname,
        @JsonProperty("avatar")
        String avatar,
        @JsonIgnore
        TestData testData) {
}
