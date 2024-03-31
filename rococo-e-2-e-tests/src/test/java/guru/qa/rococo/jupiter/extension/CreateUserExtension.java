package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.Authority;
import guru.qa.rococo.db.model.AuthorityEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import guru.qa.rococo.db.repository.UserRepository;
import guru.qa.rococo.db.repository.UserRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.DbUser;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.annotation.User;
import guru.qa.rococo.jupiter.model.UserJson;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.*;

public abstract class CreateUserExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace CREATE_USER_NAMESPACE
            = ExtensionContext.Namespace.create(CreateUserExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<ApiLogin> dbUserAnnotation = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                ApiLogin.class
        );

        if (dbUserAnnotation.isPresent()) {
            TestUser dbUser = dbUserAnnotation.get().user();

            UserJson createdUser = createUser(dbUser);

            extensionContext.getStore(CREATE_USER_NAMESPACE).put(extensionContext.getUniqueId(), createdUser);
        }

    }

    public abstract UserJson createUser(TestUser user);
    public abstract void deleteUser(UUID authId, UUID userdataId);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), ApiLogin.class)
                .isPresent() &&
                parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CREATE_USER_NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        UserJson createdUser = extensionContext.getStore(CREATE_USER_NAMESPACE)
                .get(extensionContext.getUniqueId(), UserJson.class);
        deleteUser(createdUser.testData().authId(), createdUser.id());
    }

}
