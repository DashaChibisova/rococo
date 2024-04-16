package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.MuseumEntity;

import java.util.UUID;

public interface MuseumRepository {

    MuseumEntity createInMuseum(MuseumEntity museum);

    void deleteInMuseumById(UUID id);

    MuseumEntity findMuseumByTitle(String title);

    void deleteMuseumByTitle(String title);
}
