package guru.qa.rococo.api;


import guru.qa.rococo.jupiter.model.*;
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
            @Query("page") int page, @Query("size") int size, @Query("name") String name);

    @GET("/api/artist/{id}")
    Call<ArtistJson> currentArtist(@Path("id") UUID id);

    @PATCH("/api/painting")
    Call<PaintingResponce> updateInfoPainting(@Header("Authorization") String bearerToken,
                                              @Body PaintingResponce painting);

    @POST("/api/painting")
    Call<PaintingResponce> savePainting(@Header("Authorization") String bearerToken,
                                        @Body PaintingResponce painting);

    @GET("/api/painting")
    Call<PaintingList> getAllPainting(
            @Query("page") int page, @Query("size") int size, @Query("name") String name);

    @GET("/api/painting/{id}")
    Call<PaintingResponce> currentPainting(@Path("id") UUID id);

    @GET("/api/painting/author/{id}")
    Call<PaintingList> getPaintingByAuthor(@Path("id") UUID id, @Query("page") int page, @Query("size") int size);

    @GET("/api/museum")
    Call<MuseumList> getAllMuseum(@Query("page") int page, @Query("size") int size, @Query("name") String title);

    @GET("/api/museum/{id}")
    Call<MuseumResponce> getCurrentMuseum(@Path("id") UUID id);

    @POST("/api/museum")
    Call<MuseumResponce> saveMuseum(@Header("Authorization") String bearerToken, @Body MuseumResponce museum);

    @PATCH("/api/museum")
    Call<MuseumResponce> updateMuseumInfo(@Header("Authorization") String bearerToken, @Body MuseumResponce museum);

    @GET("/api/country")
    Call<CountryList> getAllCountry(@Query("page") int page, @Query("size") int size);
}

