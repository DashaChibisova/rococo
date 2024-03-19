package guru.qa.rococo.service.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.ArtistDao;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.CountryDao;
import guru.qa.rococo.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
}
