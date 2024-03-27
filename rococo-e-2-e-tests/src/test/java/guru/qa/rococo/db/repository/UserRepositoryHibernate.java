package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.AUTH;
import static guru.qa.rococo.db.Database.USERDATA;

public class UserRepositoryHibernate extends JpaService implements UserRepository {

  private final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  public UserRepositoryHibernate() {
    super(
        Map.of(
            AUTH, EmfProvider.INSTANCE.emf(AUTH).createEntityManager(),
            USERDATA, EmfProvider.INSTANCE.emf(USERDATA).createEntityManager()
        )
    );
  }

  @Override
  public UserAuthEntity createInAuth(UserAuthEntity user) {
    String originalPassword = user.getPassword();
    user.setPassword(pe.encode(originalPassword));
    persist(AUTH, user);
    return user;
  }

  @Override
  public Optional<UserAuthEntity> findByIdInAuth(UUID id) {
    return Optional.of(entityManager(AUTH).find(UserAuthEntity.class, id));
  }

  @Override
  public UserEntity createInUserdata(UserEntity user) {
    persist(USERDATA, user);
    return user;
  }

  @Override
  public Optional<UserEntity> findByIdInUserdata(UUID id) {
    return Optional.of(entityManager(USERDATA).find(UserEntity.class, id));
  }

  @Override
  public void deleteInAuthById(UUID id) {
    UserAuthEntity toBeDeleted = findByIdInAuth(id).get();
    remove(AUTH, toBeDeleted);
  }

  @Override
  public void deleteInUserdataById(UUID id) {
    UserEntity toBeDeleted = Optional.of(entityManager(USERDATA).find(UserEntity.class, id)).get();
    remove(USERDATA, toBeDeleted);
  }
}
