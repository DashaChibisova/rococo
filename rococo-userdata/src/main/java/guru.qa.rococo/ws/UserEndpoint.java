package guru.qa.rococo.ws;

import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataService;
import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataService;
import rococo_userdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UserEndpoint {

  private static final String NAMESPACE_URI = "rococo-userdata";
  private final UserDataService userService;

  @Autowired
  public UserEndpoint(UserDataService userService) {
    this.userService = userService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserInfoRequest")
  @ResponsePayload
  public UpdateUserInfoResponse updateUserInfoRequest(@RequestPayload UpdateUserInfoRequest request) {
    UpdateUserInfoResponse response = new UpdateUserInfoResponse();
    response.setUser(userService.update(UserJson.fromJaxb(request.getUser())).toJaxbUser());
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "currentUserRequest")
  @ResponsePayload
  public CurrentUserResponse currentUserRequest(@RequestPayload CurrentUserRequest request) {
    CurrentUserResponse response = new CurrentUserResponse();
    response.setUser(userService.getCurrentUser(request.getUsername()).toJaxbUser());
    return response;
  }
}
