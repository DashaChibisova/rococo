package guru.qa.rococo.service;

import org.springframework.data.domain.Page;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Pageable;
import java.util.List;

public interface ArtistDataClient {
  @Nonnull
  ArtistJson saveArtists(@Nonnull ArtistJson artist);

  @Nonnull
  ArtistJson updateArtistInfo(@Nonnull ArtistJson artist);

  @Nonnull
  Page<ArtistJson> allArtists(@Nonnull Pageable pageable, String name);

}
