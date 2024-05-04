package guru.qa.rococo.db.repository;


import guru.qa.rococo.db.model.ArtistEntity;

import java.util.UUID;

public interface ArtistRepository {

    ArtistEntity createInArtist(ArtistEntity user);

    void deleteInArtistById(UUID id);

    ArtistEntity findArtistByName(String name);

    ArtistEntity findArtistById(UUID id);

    void deleteArtistByName(String name);

}
