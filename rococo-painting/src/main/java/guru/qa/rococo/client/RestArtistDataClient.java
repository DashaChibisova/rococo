package guru.qa.rococo.client;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.ArtistDao;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestArtistDataClient {

  private final String rococoArtistBaseUri;

  @Autowired
  public RestArtistDataClient(
                              @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
    this.rococoArtistBaseUri = rococoArtistBaseUri;
  }

  public @Nonnull
  ArtistJson getCurrentArtist(@Nonnull UUID id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/artist/" + id).build().toUri();

    return Optional.ofNullable(
            WebClient.create().get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(ArtistJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/currentUser Route]"));
  }
}
