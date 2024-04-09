package guru.qa.rococo.service.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.*;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestMuseumDataClient {

  private final WebClient webClient;
  private final String rococoMuseumBaseUri;

  @Autowired
  public RestMuseumDataClient(WebClient webClient,
                              @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri) {
    this.webClient = webClient;
    this.rococoMuseumBaseUri = rococoMuseumBaseUri;
  }

  public @Nonnull
  Page<CountryJson> getCountries(@Nonnull Pageable pageable, @Nonnull String name) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("size", String.valueOf(pageable.getPageSize()));
    params.add("page", String.valueOf(pageable.getPageNumber()));
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/country").queryParams(params).build().toUri();

    CountryDao artistDao = Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(CountryDao.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST List<CountryJson> response is given [/country Route]"
    ));
    return new PageImpl<>(artistDao.content());
  }

  public @Nonnull
  Page<MuseumJson> getAllMuseum(@Nonnull Pageable pageable, @Nonnull String title) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("size", String.valueOf(pageable.getPageSize()));
    params.add("page", String.valueOf(pageable.getPageNumber()));
    if(title != null) {
      params.add("title", title);
    }
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/museum").queryParams(params).build().toUri();

    MuseumDao museumDao = Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(MuseumDao.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST List<MuseumJson> response is given [/museum Route]"
    ));
    return new PageImpl<>(museumDao.content(), pageable, museumDao.totalElements());
  }

  public @Nonnull
  MuseumJson updateMuseumInfo(@Nonnull MuseumJson museum) {
    return Optional.ofNullable(
            webClient.patch()
                    .uri(rococoMuseumBaseUri + "/museum")
                    .body(Mono.just(museum), MuseumJson.class)
                    .retrieve()
                    .bodyToMono(MuseumJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/updateMuseumInfo Route]"));
  }

  public @Nonnull
  MuseumJson saveMuseum(@Nonnull MuseumJson museum) {
    return Optional.ofNullable(
            webClient.post()
                    .uri(rococoMuseumBaseUri + "/museum")
                    .body(Mono.just(museum), MuseumJson.class)
                    .retrieve()
                    .bodyToMono(MuseumJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST ArtistJson response is given [/Artist Route]"
    ));
  }


  public @Nonnull
  MuseumJson getCurrentMuseum(@Nonnull UUID id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/museum/" + id).build().toUri();

    return Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(MuseumJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/currentMuseum Route]"));
  }
}
