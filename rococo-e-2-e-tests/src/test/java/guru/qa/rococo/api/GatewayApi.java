package guru.qa.rococo.api;


import guru.qa.rococo.jupiter.model.ArtistList;
import guru.qa.rococo.jupiter.model.ArtistJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.UUID;

public interface GatewayApi {

    @PATCH("/api/artist")
    Call<ArtistJson> userUpdateInfoArtist(@Header("Authorization") String bearerToken,
                                          @Body ArtistJson artist);

    @POST("/api/artist")
    Call<ArtistJson> saveArtists(@Header("Authorization") String bearerToken,
                                 @Body ArtistJson artist);

    @GET("/api/artist")
    Call<ArtistList> getAllArtist(
                                 @Query("page") int page, @Query("size") int size,  @Query("name") String name);

    @GET("/api/artist/{id}")
    Call<ArtistJson> currentArtist(@Path("id") UUID id);
}

