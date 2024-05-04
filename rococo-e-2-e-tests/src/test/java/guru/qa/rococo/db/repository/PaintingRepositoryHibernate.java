package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.EmfProvider;
import guru.qa.rococo.db.jpa.JpaService;
import guru.qa.rococo.db.jpa.ThreadLocalEntityManager;
import guru.qa.rococo.db.model.PaintingEntity;
import io.qameta.allure.Step;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.rococo.db.Database.*;

public class PaintingRepositoryHibernate extends JpaService implements PaintingRepository {

    public PaintingRepositoryHibernate() {
        super(PAINTING, new ThreadLocalEntityManager(EmfProvider.INSTANCE.emf(PAINTING)));
    }

    @Step("Create painting")
    @Override
    public PaintingEntity createInPainting(PaintingEntity painting) {
        persist(PAINTING, painting);
        return painting;
    }

    @Step("Delete painting")
    @Override
    public void deleteInPaintingById(UUID id) {
        PaintingEntity toBeDeleted = Optional.of(entityManager(PAINTING).find(PaintingEntity.class, id)).get();
        remove(PAINTING, toBeDeleted);
    }

    @Step("Find painting by title")
    @Override
    public PaintingEntity findPaintingByTitle(String title) {
        return entityManager(PAINTING)
                .createQuery("""
                        FROM PaintingEntity 
                        WHERE title = :title
                        """, PaintingEntity.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Step("Delete painting by title")
    @Override
    public void deletePaintingByTitle(String title) {
        deleteInPaintingById(findPaintingByTitle(title).getId());
    }
}
