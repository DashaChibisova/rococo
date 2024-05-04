package guru.qa.rococo.api;

import guru.qa.rococo.api.interceptor.CodeInterceptor;
import guru.qa.rococo.jupiter.model.*;
import retrofit2.Response;

import java.util.UUID;

public class GatewayApiClient extends RestClient {
    private final GatewayApi gatewayApi;

    public GatewayApiClient() {
        super(
                CFG.gatewayUrl(),
                true,
                new CodeInterceptor()
        );
        gatewayApi = retrofit.create(GatewayApi.class);
    }

    public Response<ArtistJson> userUpdateInfoArtist(String bearerToken, ArtistJson request) throws Exception {
        return gatewayApi.userUpdateInfoArtist(bearerToken, request).execute();
    }

    public ArtistList getAllArtist(int page, int size, String name) throws Exception {
        return gatewayApi.getAllArtist(page, size, name).execute()
                .body();
    }

    public Response<ArtistJson> saveArtists(String bearerToken, ArtistJson request) throws Exception {
        return gatewayApi.saveArtists(bearerToken, request).execute();
    }

    public ArtistJson currentArtist(UUID idArtist) throws Exception {
        return gatewayApi.currentArtist(idArtist).execute()
                .body();
    }

    public Response<PaintingResponce> updateInfoPainting(String bearerToken, PaintingResponce request) throws Exception {
        return gatewayApi.updateInfoPainting(bearerToken, request).execute();
    }

    public PaintingList getAllPainting(int page, int size, String name) throws Exception {
        return gatewayApi.getAllPainting(page, size, name).execute()
                .body();
    }

    public Response<PaintingResponce> savePainting(String bearerToken, PaintingResponce request) throws Exception {
        return gatewayApi.savePainting(bearerToken, request).execute();
    }

    public PaintingResponce currentPainting(UUID idPainting) throws Exception {
        return gatewayApi.currentPainting(idPainting).execute()
                .body();
    }

    public PaintingList getPaintingByAuthor(UUID idAuthor, int page, int size) throws Exception {
        return gatewayApi.getPaintingByAuthor(idAuthor, page, size).execute()
                .body();
    }

    public Response<MuseumResponce> updateMuseumInfo(String bearerToken, MuseumResponce request) throws Exception {
        return gatewayApi.updateMuseumInfo(bearerToken, request).execute();
    }

    public MuseumList getAllMuseum(int page, int size, String title) throws Exception {
        return gatewayApi.getAllMuseum(page, size, title).execute()
                .body();
    }

    public CountryList getAllCountry(int page, int size) throws Exception {
        return gatewayApi.getAllCountry(page, size).execute()
                .body();
    }

    public Response<MuseumResponce> saveMuseum(String bearerToken, MuseumResponce request) throws Exception {
        return gatewayApi.saveMuseum(bearerToken, request).execute();
    }

    public MuseumResponce currentMuseum(UUID idPainting) throws Exception {
        return gatewayApi.getCurrentMuseum(idPainting).execute()
                .body();
    }


}
