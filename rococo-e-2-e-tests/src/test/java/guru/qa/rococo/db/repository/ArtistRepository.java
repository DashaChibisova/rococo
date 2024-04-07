package guru.qa.rococo.db.repository;


import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository {

    ArtistEntity createInArtist(ArtistEntity user);

    void deleteInArtistById(UUID id);

}
