package guru.qa.rococo.controller;


import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataClient;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserDataClient userDataClient;

  @Autowired
  public UserController(UserDataClient userDataClient) {
    this.userDataClient = userDataClient;
  }

  @PostMapping("/updateUserInfo")
  public UserJson updateUserInfo(@AuthenticationPrincipal Jwt principal,
                                 @Valid @RequestBody UserJson user) {
    String username = principal.getClaim("sub");
    return userDataClient.updateUserInfo(user.addUsername(username));
  }

  @GetMapping("/currentUser")
  public UserJson currentUser(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userDataClient.currentUser(username);
  }
}
