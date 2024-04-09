package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.model.MuseumEntity;

import java.util.UUID;

public interface MuseumRepository {

    MuseumEntity createInMuseum(MuseumEntity museum);

    void deleteInMuseumById(UUID id);
}
