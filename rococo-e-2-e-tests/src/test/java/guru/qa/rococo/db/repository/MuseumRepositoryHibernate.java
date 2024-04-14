package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.MuseumEntity;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class MuseumRepositoryHibernate extends JpaService implements MuseumRepository {

    public MuseumRepositoryHibernate() {
        super(MUSEUM, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(MUSEUM)));
    }

    @Override
    public MuseumEntity createInMuseum(MuseumEntity museum) {
        persist(MUSEUM, museum);
        return museum;
    }

    @Override
    public void deleteInMuseumById(UUID id) {
        MuseumEntity toBeDeleted = Optional.of(entityManager(MUSEUM).find(MuseumEntity.class, id)).get();
        remove(MUSEUM, toBeDeleted);
    }
}
