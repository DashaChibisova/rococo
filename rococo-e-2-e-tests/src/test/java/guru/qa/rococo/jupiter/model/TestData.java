package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record TestData(
        @JsonIgnore String password,
        @JsonIgnore UUID authId

) {
}
