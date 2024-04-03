package guru.qa.rococo.service.api;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.ArtistDto;
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

  private final WebClient webClient;
  private final String rococoArtistBaseUri;

  @Autowired
  public RestArtistDataClient(WebClient webClient,
                              @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
    this.webClient = webClient;
    this.rococoArtistBaseUri = rococoArtistBaseUri;
  }

  public @Nonnull
  ArtistJson updateArtistInfo(@Nonnull ArtistJson artist) {
    return Optional.ofNullable(
        webClient.patch()
            .uri(rococoArtistBaseUri + "/artist")
            .body(Mono.just(artist), ArtistJson.class)
            .retrieve()
            .bodyToMono(ArtistJson.class)
            .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/updateUserInfo Route]"));
  }

  public @Nonnull
  ArtistJson saveArtists(@Nonnull ArtistJson artist) {
    return Optional.ofNullable(
            webClient.post()
                    .uri(rococoArtistBaseUri + "/artist")
                    .body(Mono.just(artist), ArtistJson.class)
                    .retrieve()
                    .bodyToMono(ArtistJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST ArtistJson response is given [/Artist Route]"
    ));
  }

  public @Nonnull
  Page<ArtistJson> getArtists(@Nonnull Pageable pageable, @Nonnull String name) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("size", String.valueOf(pageable.getPageSize()));
    params.add("page", String.valueOf(pageable.getPageNumber()));
    if(name != null) {
      params.add("name", name);
    }
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/artist").queryParams(params).build().toUri();

    ArtistDto artistDto = Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ArtistDto.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST List<ArtistJson> response is given [/artist Route]"
    ));

    return new PageImpl<>(artistDto.content(), pageable, artistDto.totalElements());
  }


  public @Nonnull
  ArtistJson getCurrentArtist(@Nonnull UUID id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/artist/" + id).build().toUri();

    return Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(ArtistJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/currentUser Route]"));
  }
}
