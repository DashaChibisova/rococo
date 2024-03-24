package guru.qa.rococo.service.api;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataClient;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "rococo-userdata", name = "client", havingValue = "rest")
public class RestUserDataClient implements UserDataClient {

    private final WebClient webClient;
    private final String rococoUserdataBaseUri;

    @Autowired
    public RestUserDataClient(WebClient webClient,
                              @Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.webClient = webClient;
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
    }

    @Override
    public @Nonnull
    UserJson userUpdateInfo(@Nonnull UserJson user) {
        return Optional.ofNullable(
                webClient.patch()
                        .uri(rococoUserdataBaseUri + "/user")
                        .body(Mono.just(user), UserJson.class)
                        .retrieve()
                        .bodyToMono(UserJson.class)
                        .block()
        ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/user Route]"));
    }

    @Override
    public @Nonnull
    UserJson user(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoUserdataBaseUri + "/user").queryParams(params).build().toUri();

        return Optional.ofNullable(
                webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(UserJson.class)
                        .block()
        ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/user Route]"));
    }
}
