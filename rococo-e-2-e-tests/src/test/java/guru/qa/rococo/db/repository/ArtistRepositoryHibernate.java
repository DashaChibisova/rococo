package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class ArtistRepositoryHibernate extends JpaService implements ArtistRepository {

  public ArtistRepositoryHibernate() {
    super(AUTH, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(AUTH)));
  }

  @Override
  public ArtistEntity createInArtist(ArtistEntity artist) {
    persist(ARTIST, artist);
    return artist;
  }

  @Override
  public void deleteInArtistById(UUID id) {
    ArtistEntity toBeDeleted = Optional.of(entityManager(ARTIST).find(ArtistEntity.class, id)).get();
    remove(ARTIST, toBeDeleted);
  }

//  @Override
//  public ArtistEntity findByName(String name) {
//    return entityManager(ARTIST).find(ArtistEntity.class, name);
//  }
//
//
//  @Override
//  public void deleteInArtistByName(String name) {
//    ArtistEntity byName = findByName(name);
//    ArtistEntity toBeDeleted = Optional.of(entityManager(ARTIST).find(ArtistEntity.class, byName.getId())).get();
//    remove(ARTIST, toBeDeleted);
//  }
}
