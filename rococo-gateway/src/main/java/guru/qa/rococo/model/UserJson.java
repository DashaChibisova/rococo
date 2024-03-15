package guru.qa.rococo.model;


import guru.qa.rococo.userdata.wsdl.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
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
        String avatar)

        //добавлять картины/музеи/художников
{

    //???

    public @Nonnull UserJson addUsername(@Nonnull String username) {
        return new UserJson(id, username, firstname, lastname, avatar);
    }

    public @jakarta.annotation.Nonnull
    User toJaxbUser() {
        User jaxbUser = new User();
        jaxbUser.setId(id != null ? id.toString() : null);
        jaxbUser.setUsername(username);
        jaxbUser.setFirstname(firstname);
        jaxbUser.setLastname(lastname);
        jaxbUser.setAvatar(avatar);
        return jaxbUser;
    }

    public static @jakarta.annotation.Nonnull
    UserJson fromJaxb(@jakarta.annotation.Nonnull User jaxbUser) {
        return new UserJson(
                jaxbUser.getId() != null ? UUID.fromString(jaxbUser.getId()) : null,
                jaxbUser.getUsername(),
                jaxbUser.getFirstname(),
                jaxbUser.getLastname(),
                jaxbUser.getAvatar()

        );
    }

}

//  public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity) {
//    return fromEntity(entity);
//  }
