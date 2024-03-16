package guru.qa.rococo.service;

import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;

public interface UserDataClient {
  @Nonnull
  UserJson userUpdateInfo(@Nonnull UserJson user);

  @Nonnull
  UserJson user(@Nonnull String username);

}
