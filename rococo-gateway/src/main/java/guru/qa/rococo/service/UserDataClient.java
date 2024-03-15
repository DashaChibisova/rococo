package guru.qa.rococo.service;

import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface UserDataClient {
  @Nonnull
  UserJson updateUserInfo(@Nonnull UserJson user);

  @Nonnull
  UserJson currentUser(@Nonnull String username);
}
