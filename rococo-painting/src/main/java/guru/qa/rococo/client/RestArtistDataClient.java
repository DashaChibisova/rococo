package guru.qa.rococo.client;

import guru.qa.rococo.ex.NoRestResponseException;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

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
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/currentArtist Route]"));
    }
}
