package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.ArtistEntity;
import io.qameta.allure.Step;
import jakarta.persistence.NoResultException;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class ArtistRepositoryHibernate extends JpaService implements ArtistRepository {

    public ArtistRepositoryHibernate() {
        super(ARTIST, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(ARTIST)));
    }

    @Step("Create artist")
    @Override
    public ArtistEntity createInArtist(ArtistEntity artist) {
        persist(ARTIST, artist);
        return artist;
    }

    @Step("Delete artist")
    @Override
    public void deleteInArtistById(UUID id) {
        ArtistEntity toBeDeleted = Optional.of(entityManager(ARTIST).find(ArtistEntity.class, id)).get();
        remove(ARTIST, toBeDeleted);
    }

    @Step("Find artist by name")
    @Override
  public ArtistEntity findArtistByName(String name) {
          return entityManager(ARTIST)
                  .createQuery("""
            FROM ArtistEntity 
            WHERE name = :name
            """, ArtistEntity.class)
                  .setParameter("name", name)
                  .getSingleResult();

    }

    @Step("Delete artist by name")
    @Override
    public void deleteArtistByName(String name) {
        deleteInArtistById(findArtistByName(name).getId());
    }
}
