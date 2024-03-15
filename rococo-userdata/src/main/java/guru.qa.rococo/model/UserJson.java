package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.UserEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import rococo_userdata.User;


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

  public @Nonnull
  User toJaxbUser() {
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

  public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity) {
    return new UserJson(
        entity.getId(),
        entity.getUsername(),
        entity.getFirstname(),
        entity.getLastname(),
        entity.getAvatar() != null && entity.getAvatar().length > 0 ? new String(entity.getAvatar(), StandardCharsets.UTF_8) : null
    );
  }

//  public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity) {
//    return fromEntity(entity);
//  }
}
