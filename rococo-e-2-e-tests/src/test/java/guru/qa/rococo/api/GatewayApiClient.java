package guru.qa.rococo.api;

import guru.qa.rococo.api.interceptor.CodeInterceptor;
import guru.qa.rococo.jupiter.model.ArtistList;
import guru.qa.rococo.jupiter.model.ArtistJson;

import java.util.UUID;

public class GatewayApiClient  extends RestClient {
    private final GatewayApi gatewayApi;

    public GatewayApiClient() {
        super(
                CFG.gatewayUrl(),
                true,
                new CodeInterceptor()
        );
        gatewayApi = retrofit.create(GatewayApi.class);
    }

    public ArtistJson userUpdateInfoArtist(String bearerToken, ArtistJson request) throws Exception {
        return gatewayApi.userUpdateInfoArtist(bearerToken, request).execute()
                .body();
    }

    public ArtistList getAllArtist(int page, int size, String name) throws Exception {
        return gatewayApi.getAllArtist( page, size, name).execute()
                .body();
    }

    public ArtistJson saveArtists(String bearerToken, ArtistJson request) throws Exception {
        return gatewayApi.saveArtists(bearerToken, request).execute()
                .body();
    }

    public ArtistJson currentArtist(UUID idArtist) throws Exception {
        return gatewayApi.currentArtist(idArtist).execute()
                .body();
    }
}