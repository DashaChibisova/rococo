package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ArtistService {

  private final ArtistRepository artistRepository;

  @Autowired
  public ArtistService(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }
  @Transactional
  public @Nonnull
  ArtistJson save(@Nonnull ArtistJson artist) {
      ArtistEntity artistEntity = new ArtistEntity();
      artistEntity.setBiography(artist.biography());
      artistEntity.setName(artist.name());
      artistEntity.setPhoto(artist.photo() != null ? artist.photo().getBytes(StandardCharsets.UTF_8) : null);
      return ArtistJson.fromEntity(artistRepository.save(artistEntity));
    }

  @Transactional
  public @Nonnull
  List<ArtistJson> allArtists() {
    return artistRepository.findAll()
            .stream()
            .map(ArtistJson::fromEntity)
            .toList();
  }
}
