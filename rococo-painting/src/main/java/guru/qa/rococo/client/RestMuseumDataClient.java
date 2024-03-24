package guru.qa.rococo.client;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.CountryDao;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumDao;
import guru.qa.rococo.model.MuseumJson;
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
public class RestMuseumDataClient {

  private final String rococoMuseumBaseUri;

  @Autowired
  public RestMuseumDataClient(
                              @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri) {
    this.rococoMuseumBaseUri = rococoMuseumBaseUri;
  }

  public @Nonnull
  MuseumJson getCurrentMuseum(@Nonnull UUID id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/museum/" + id).build().toUri();

    return Optional.ofNullable(
            WebClient.create().get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(MuseumJson.class)
                    .block()
    ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/currentMuseum Route]"));
  }
}
