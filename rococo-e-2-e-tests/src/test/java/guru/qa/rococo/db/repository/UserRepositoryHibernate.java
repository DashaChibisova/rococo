package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import io.qameta.allure.Step;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class UserRepositoryHibernate extends JpaService implements UserRepository {

    private final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public UserRepositoryHibernate() {
        super(
                Map.of(
                        AUTH, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(AUTH)),
                        USERDATA, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(USERDATA))
                )
        );
    }

    @Step("Create user in auth")
    @Override
    public UserAuthEntity createInAuth(UserAuthEntity user) {
        String originalPassword = user.getPassword();
        user.setPassword(pe.encode(originalPassword));
        persist(AUTH, user);
        return user;
    }

    @Step("Find user in auth")
    @Override
    public Optional<UserAuthEntity> findByIdInAuth(UUID id) {
        return Optional.of(entityManager(AUTH).find(UserAuthEntity.class, id));
    }

    @Step("Create user in userdata")
    @Override
    public UserEntity createInUserdata(UserEntity user) {
        persist(USERDATA, user);
        return user;
    }

    @Step("Find user in userdata")
    @Override
    public Optional<UserEntity> findByIdInUserdata(UUID id) {
        return Optional.of(entityManager(USERDATA).find(UserEntity.class, id));
    }

    @Step("Delete user in auth")
    @Override
    public void deleteInAuthById(UUID id) {
        UserAuthEntity toBeDeleted = findByIdInAuth(id).get();
        remove(AUTH, toBeDeleted);
    }

    @Step("Delete user in userdata")
    @Override
    public void deleteInUserdataById(UUID id) {
        UserEntity toBeDeleted = Optional.of(entityManager(USERDATA).find(UserEntity.class, id)).get();
        remove(USERDATA, toBeDeleted);
    }

    @Override
    public UserAuthEntity findUserAuthByName(String username) {
        return entityManager(AUTH)
                .createQuery("""
                        FROM UserAuthEntity 
                        WHERE username = :username
                        """, UserAuthEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void deleteUserAuthByName(String username) {
        deleteInAuthById(findUserAuthByName(username).getId());
    }

    @Override
    public UserEntity findUserdataByName(String username) {
        return entityManager(USERDATA)
                .createQuery("""
                        FROM UserEntity 
                        WHERE username = :username
                        """, UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void deleteUserdataByName(String username) {
        deleteInUserdataById(findUserdataByName(username).getId());
    }
}
