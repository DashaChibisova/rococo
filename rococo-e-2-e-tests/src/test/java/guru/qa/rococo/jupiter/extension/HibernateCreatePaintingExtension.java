package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.ArtistEntity;
import guru.qa.rococo.db.model.PaintingEntity;
import guru.qa.rococo.db.repository.*;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.annotation.TestPainting;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.jupiter.model.PaintingJson;
import guru.qa.rococo.utils.DataUtils;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

public class HibernateCreatePaintingExtension extends CreatePaintingExtension {

    private final PaintingRepository paintingRepository = new PaintingRepositoryHibernate();
    private final HibernateCreateArtistExtension hibernateCreateArtistExtension = new HibernateCreateArtistExtension();
    private final HibernateCreateMuseumExtension hibernateCreateMuseumExtension = new HibernateCreateMuseumExtension();

    @Override
    public PaintingJson createPainting(TestPainting painting) throws IOException {
        Artist artistData = painting.artist();
        ArtistJson artist = hibernateCreateArtistExtension.createArtist(artistData);

        TestMuseum museumData = painting.museum();
        MuseumJson museum = hibernateCreateMuseumExtension.createMuseum(museumData);


        String title = painting.title().isEmpty()
                ? DataUtils.generateRandomSentence(1)
                : painting.title();
        String description = painting.description().isEmpty()
                ? DataUtils.generateRandomSentence(5)
                : painting.description();

        byte[] content = painting.content().isEmpty()
                ? encodedFileBytes("src/test/resources/images/profile/duck.jpg")
                : encodedFileBytes(painting.content());

        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(title);
        paintingEntity.setDescription(description);
        paintingEntity.setContent(content);
        paintingEntity.setArtist(artist.id());
        paintingEntity.setMuseum(museum.id());

        PaintingEntity inPainting = paintingRepository.createInPainting(paintingEntity);

        return PaintingJson.fromEntity(inPainting);
    }

    @Override
    public void deletePainting(PaintingJson painting) {
        paintingRepository.deleteInPaintingById(painting.id());
        hibernateCreateArtistExtension.deleteArtist(UUID.fromString(painting.artist()));
        hibernateCreateMuseumExtension.deleteMuseum(UUID.fromString(painting.museum()));
    }

    private byte[] encodedFileBytes(String path) throws IOException {
        File fi = new File(path);
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        String encodedFile = "data:image/png;base64," +Base64.getEncoder().encodeToString(fileContent);
        return encodedFile.getBytes(StandardCharsets.UTF_8);
    }

}
