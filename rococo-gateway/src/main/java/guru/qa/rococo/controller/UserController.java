package guru.qa.rococo.controller;


import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataClient;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "api")
@RestController
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserDataClient userDataClient;

  @Autowired
  public UserController(UserDataClient userDataClient) {
    this.userDataClient = userDataClient;
  }

  @PatchMapping("/user")
  public UserJson userUpdateInfo(@AuthenticationPrincipal Jwt principal,
                                 @Valid @RequestBody UserJson user) {
    String username = principal.getClaim("sub");
    return userDataClient.userUpdateInfo(user.addUsername(username));
  }

  @GetMapping("/user")
  public UserJson user(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return userDataClient.user(username);
  }
}
