package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.model.PaintingEntity;

import java.util.UUID;

public interface PaintingRepository {

    PaintingEntity createInPainting(PaintingEntity painting);

    void deleteInPaintingById(UUID id);
}
