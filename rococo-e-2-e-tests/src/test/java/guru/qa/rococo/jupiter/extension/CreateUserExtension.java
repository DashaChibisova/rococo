package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.Authority;
import guru.qa.rococo.db.model.AuthorityEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import guru.qa.rococo.db.repository.UserRepository;
import guru.qa.rococo.db.repository.UserRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public abstract class CreateUserExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

  public static final ExtensionContext.Namespace DB_CREATE_USER_NAMESPACE
          = ExtensionContext.Namespace.create(CreateUserExtension.class);

  private static UserRepository userRepository = new UserRepositoryHibernate();


  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    Optional<TestUser> dbUserAnnotation = AnnotationSupport.findAnnotation(
            extensionContext.getRequiredTestMethod(),
            TestUser.class
    );

    if (dbUserAnnotation.isPresent()) {
      TestUser dbUser = dbUserAnnotation.get();
      String username = dbUser.username().isEmpty()
              ? DataUtils.generateRandomUsername()
              : dbUser.username();
      String password = dbUser.password().isEmpty()
              ? "12345"
              : dbUser.password();

      UserAuthEntity userAuth = new UserAuthEntity();
      userAuth.setUsername(username);
      userAuth.setPassword(password);
      userAuth.setEnabled(true);
      userAuth.setAccountNonExpired(true);
      userAuth.setAccountNonLocked(true);
      userAuth.setCredentialsNonExpired(true);
      AuthorityEntity[] authorities = Arrays.stream(Authority.values()).map(
              a -> {
                AuthorityEntity ae = new AuthorityEntity();
                ae.setAuthority(a);
                return ae;
              }
      ).toArray(AuthorityEntity[]::new);

      userAuth.addAuthorities(authorities);

      UserEntity user = new UserEntity();
      user.setUsername(username);

      userRepository.createInAuth(userAuth);
      userRepository.createInUserdata(user);

      Map<String, Object> createdUser = Map.of(
              "auth", userAuth,
              "userdata", user
      );

      extensionContext.getStore(DB_CREATE_USER_NAMESPACE).put(extensionContext.getUniqueId(), createdUser);
    }
  }

  @Override
  public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
    Map createdUser = extensionContext.getStore(DB_CREATE_USER_NAMESPACE)
            .get(extensionContext.getUniqueId(), Map.class);
    userRepository.deleteInAuthById(((UserAuthEntity) createdUser.get("auth")).getId());
    userRepository.deleteInUserdataById(((UserEntity) createdUser.get("userdata")).getId());
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), TestUser.class)
            .isPresent() &&
            parameterContext.getParameter().getType().isAssignableFrom(UserAuthEntity.class);
  }

  @Override
  public UserAuthEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return (UserAuthEntity) extensionContext.getStore(DB_CREATE_USER_NAMESPACE).get(extensionContext.getUniqueId(), Map.class)
            .get("auth");
  }
}
