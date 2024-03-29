package guru.qa.rococo.api;


import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.model.UserJson;


public class UserApiClient extends RestClient {

    private final UserApi userApi;

    public UserApiClient() {
        super(Config.getInstance().userdataUrl());
        this.userApi = retrofit.create(UserApi.class);
    }

    public UserJson getCurrentUser(String username) throws Exception {
        return userApi.currentUser(username).execute().body();
    }
}
