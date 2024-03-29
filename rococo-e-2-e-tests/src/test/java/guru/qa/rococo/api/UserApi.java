package guru.qa.rococo.api;

import guru.qa.rococo.jupiter.model.UserJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/currentUser")
    Call<UserJson> currentUser(@Query("username") String username);

}
