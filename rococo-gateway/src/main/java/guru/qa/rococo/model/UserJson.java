package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import guru.qa.rococo.userdata.wsdl.User;
import jakarta.validation.constraints.Size;

import javax.annotation.Nonnull;
import java.util.UUID;

public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        @Size(max = 30, message = "First name can`t be longer than 30 characters")
        String firstname,
        @JsonProperty("lastname")
        @Size(max = 50, message = "Surname can`t be longer than 50 characters")
        String lastname,
        @JsonProperty("avatar")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String avatar) {

    public @Nonnull UserJson addUsername(@Nonnull String username) {
        return new UserJson(id, username, firstname, lastname, avatar);
    }

    public @Nonnull User toJaxbUser() {
        User jaxbUser = new User();
        jaxbUser.setId(id != null ? id.toString() : null);
        jaxbUser.setUsername(username);
        jaxbUser.setFirstname(firstname);
        jaxbUser.setLastname(lastname);
        jaxbUser.setAvatar(avatar);
        return jaxbUser;
    }

    public static @Nonnull UserJson fromJaxb(@Nonnull User jaxbUser) {
        return new UserJson(
                jaxbUser.getId() != null ? UUID.fromString(jaxbUser.getId()) : null,
                jaxbUser.getUsername(),
                jaxbUser.getFirstname(),
                jaxbUser.getLastname(),
                jaxbUser.getAvatar()
        );
    }
}
