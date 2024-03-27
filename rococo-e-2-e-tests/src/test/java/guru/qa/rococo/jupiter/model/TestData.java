package guru.qa.rococo.jupiter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TestData(
    @JsonIgnore String password
) {
}
