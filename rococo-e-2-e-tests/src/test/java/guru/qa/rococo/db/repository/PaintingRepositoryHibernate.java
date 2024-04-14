package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.PaintingEntity;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class PaintingRepositoryHibernate extends JpaService implements PaintingRepository {

  public PaintingRepositoryHibernate() {
    super(PAINTING, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(PAINTING)));
  }

  @Override
  public PaintingEntity createInPainting(PaintingEntity painting) {
    persist(PAINTING, painting);
    return painting;
  }

  @Override
  public void deleteInPaintingById(UUID id) {
    PaintingEntity toBeDeleted = Optional.of(entityManager(PAINTING).find(PaintingEntity.class, id)).get();
    remove(PAINTING, toBeDeleted);
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
