package guru.qa.rococo.service.api;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.*;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestPaintingDataClient {

  private final WebClient webClient;
  private final String rococoPaintingBaseUri;

  @Autowired
  public RestPaintingDataClient(WebClient webClient,
                                @Value("${rococo-painting.base-uri}") String rococoPaintingBaseUri) {
    this.webClient = webClient;
    this.rococoPaintingBaseUri = rococoPaintingBaseUri;
  }

  public @Nonnull
  Page<PaintingJson> getPaintings(@Nonnull Pageable pageable, @Nonnull String title) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("size", String.valueOf(pageable.getPageSize()));
    params.add("page", String.valueOf(pageable.getPageNumber()));
    if(title != null) {
      params.add("title", title);
    }
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/painting").queryParams(params).build().toUri();

    PaintingDao paintingDao = Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(PaintingDao.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST List<PaintingJson> response is given [/painting Route]"
    ));
    return new PageImpl<>(paintingDao.content(), pageable, paintingDao.totalElements());
  }

  public @Nonnull
  PaintingJson updatePaintingInfo(@Nonnull PaintingJson painting) {
    return Optional.ofNullable(
            webClient.patch()
                    .uri(rococoPaintingBaseUri + "/painting")
                    .body(Mono.just(painting), PaintingJson.class)
                    .retrieve()
                    .bodyToMono(PaintingJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/updatePaintingInfo Route]"));
  }

  public @Nonnull
  PaintingJson savePainting(@Nonnull PaintingJson painting) {
    return Optional.ofNullable(
            webClient.post()
                    .uri(rococoPaintingBaseUri + "/painting")
                    .body(Mono.just(painting), PaintingJson.class)
                    .retrieve()
                    .bodyToMono(PaintingJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST ArtistJson response is given [/Artist Route]"
    ));
  }


  public @Nonnull
  PaintingJson getCurrentPainting(@Nonnull UUID id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/painting/" + id).build().toUri();

    return Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(PaintingJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/currentMuseum Route]"));
  }

    public @Nonnull
  Page<PaintingJson> getPaintingByAuthorId(@PathVariable UUID id, @Nonnull Pageable pageable) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("size", String.valueOf(pageable.getPageSize()));
      params.add("page", String.valueOf(pageable.getPageNumber()));
      URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/painting/author/" + id).build().toUri();

    PaintingDao paintingDao = Optional.ofNullable(
            webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(PaintingDao.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException(
            "No REST List<MuseumJson> response is given [/museum Route]"
    ));
    return new PageImpl<>(paintingDao.content(), pageable, paintingDao.totalElements());

    }
}

