package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.*;
import guru.qa.rococo.db.repository.ArtistRepository;
import guru.qa.rococo.db.repository.ArtistRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.utils.DataUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

public class HibernateCreateArtistExtension extends CreateArtistExtension {

    private final ArtistRepository artistRepository = new ArtistRepositoryHibernate();

    @Override
    public ArtistJson createArtist(Artist artist) throws IOException {
        String name = artist.name().isEmpty()
                ? DataUtils.generateRandomUsername()
                : artist.name();
        String biography = artist.biography().isEmpty()
                ? DataUtils.generateRandomString(11)
                : artist.biography();


        byte[] photo = artist.photoPath().isEmpty()
                ? encodedFileBytes("src/test/resources/images/artist.png")
                : encodedFileBytes(artist.photoPath());

        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(name);
        artistEntity.setBiography(biography);
        artistEntity.setPhoto(photo);

        ArtistEntity inArtist = artistRepository.createInArtist(artistEntity);

        return ArtistJson.fromEntity(inArtist);
    }

    @Override
    public void deleteArtist(UUID artistId) {
        artistRepository.deleteInArtistById(artistId);
    }

    private byte[] encodedFileBytes(String path) throws IOException {
        File fi = new File(path);
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        String encodedFile = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        return encodedFile.getBytes(StandardCharsets.UTF_8);
    }


}
