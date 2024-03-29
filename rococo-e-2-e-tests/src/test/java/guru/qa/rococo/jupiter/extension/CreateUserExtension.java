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

public abstract class CreateUserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace CREATE_USER_NAMESPACE
            = ExtensionContext.Namespace.create(CreateUserExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<User.Point, List<TestUser>> usersForTest = extractUsersForTest(extensionContext);

        Map<User.Point, List<UserJson>> createdUsers = new HashMap<>();
        for (Map.Entry<User.Point, List<TestUser>> userInfo : usersForTest.entrySet()) {
            List<UserJson> usersForPoint = new ArrayList<>();
            for (TestUser testUser : userInfo.getValue()) {
                UserJson user = createUser(testUser);
                usersForPoint.add(user);

            }
            createdUsers.put(userInfo.getKey(), usersForPoint);
        }

        extensionContext.getStore(CREATE_USER_NAMESPACE).put(extensionContext.getUniqueId(), createdUsers);
    }

    public abstract UserJson createUser(TestUser user);


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(parameterContext.getParameter(), User.class).isPresent() &&
                (parameterContext.getParameter().getType().isAssignableFrom(UserJson.class) ||
                        parameterContext.getParameter().getType().isAssignableFrom(UserJson[].class));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User user = AnnotationSupport.findAnnotation(parameterContext.getParameter(), User.class).get();
        Map<User.Point, List<UserJson>> createdUsers= extensionContext.getStore(CREATE_USER_NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        List<UserJson> userJsons = createdUsers.get(user.value());
        if (parameterContext.getParameter().getType().isAssignableFrom(UserJson[].class)) {
            return userJsons.stream().toList().toArray(new UserJson[0]);
        } else {
            return userJsons.get(0);
        }
    }

    private Map<User.Point, List<TestUser>> extractUsersForTest(ExtensionContext context) {
        Map<User.Point, List<TestUser>> result = new HashMap<>();
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ApiLogin.class).ifPresent(
                apiLogin -> {
                    TestUser user = apiLogin.user();
                    if (!user.fake()) {
                        result.put(User.Point.INNER, List.of(user));
                    }
                }
        );
        List<TestUser> outerUsers = new ArrayList<>();
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), TestUser.class).ifPresent(
                tu -> {
                    if (!tu.fake()) outerUsers.add(tu);
                }
        );
        result.put(User.Point.OUTER, outerUsers);
        return result;
    }

}
