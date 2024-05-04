package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.CountryEntity;
import guru.qa.rococo.db.model.MuseumEntity;
import io.qameta.allure.Step;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class MuseumRepositoryHibernate extends JpaService implements MuseumRepository {

    public MuseumRepositoryHibernate() {
        super(MUSEUM, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(MUSEUM)));
    }

    @Step("Create museum")
    @Override
    public MuseumEntity createInMuseum(MuseumEntity museum) {
        persist(MUSEUM, museum);
        return museum;
    }

    @Step("Delete museum by id")
    @Override
    public void deleteInMuseumById(UUID id) {
        MuseumEntity toBeDeleted = Optional.of(entityManager(MUSEUM).find(MuseumEntity.class, id)).get();
        remove(MUSEUM, toBeDeleted);
    }

    @Step("Find museum by title")
    @Override
    public MuseumEntity findMuseumByTitle(String title) {
        return entityManager(MUSEUM)
                .createQuery("""
                        FROM MuseumEntity 
                        WHERE title = :title
                        """, MuseumEntity.class)
                .setParameter("title", title)
                .getSingleResult();

    }

    @Override
    public MuseumEntity findMuseumById(UUID id) {
        return entityManager(MUSEUM).find(MuseumEntity.class, id);
    }

    @Step("Find museum by title")
    @Override
    public CountryEntity findCountryByName(String name) {
        return entityManager(MUSEUM)
                .createQuery("""
                        FROM CountryEntity
                        WHERE name = :name
                        """, CountryEntity.class)
                .setParameter("name", name)
                .getSingleResult();

    }

    @Step("Delete museum by title")
    @Override
    public void deleteMuseumByTitle(String title) {
        deleteInMuseumById(findMuseumByTitle(title).getId());
    }
}
