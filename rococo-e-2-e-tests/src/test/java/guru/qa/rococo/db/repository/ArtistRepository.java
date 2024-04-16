package guru.qa.rococo.db.repository;


import guru.qa.rococo.db.model.ArtistEntity;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository {

    ArtistEntity createInArtist(ArtistEntity user);

    void deleteInArtistById(UUID id);

    ArtistEntity findArtistByName(String name);

    void deleteArtistByName(String name);
    //
//    ArtistEntity findByName(String name);


}
