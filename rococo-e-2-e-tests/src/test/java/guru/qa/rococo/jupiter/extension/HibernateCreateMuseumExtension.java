package guru.qa.rococo.jupiter.extension;


import guru.qa.rococo.db.model.CountryEntity;
import guru.qa.rococo.db.model.GeoEntity;
import guru.qa.rococo.db.model.MuseumEntity;
import guru.qa.rococo.db.repository.MuseumRepository;
import guru.qa.rococo.db.repository.MuseumRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.utils.DataUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

public class HibernateCreateMuseumExtension extends CreateMuseumExtension {

    private final MuseumRepository museumRepository = new MuseumRepositoryHibernate();

    @Override
    public MuseumJson createMuseum(TestMuseum museum) throws IOException {
        String title = museum.title().isEmpty()
                ? DataUtils.generateRandomString(5)
                : museum.title();
        String description = museum.description().isEmpty()
                ? DataUtils.generateRandomString(11)
                : museum.description();
        String city = museum.city().isEmpty()
                ? DataUtils.generateRandomCity()
                : museum.city();
        String country = museum.country().isEmpty()
                ? DataUtils.generateRandomCountry()
                : museum.country();

        byte[] photo = museum.photoPath().isEmpty()
                ? encodedFileBytes("src/test/resources/images/museum.png")
                : encodedFileBytes(museum.photoPath());

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(country);

        GeoEntity geoEntity = new GeoEntity();
        geoEntity.setCity(city);
        geoEntity.setCountry(countryEntity);

        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setTitle(title);
        museumEntity.setDescription(description);
        museumEntity.setPhoto(photo);
        museumEntity.setGeo(geoEntity);

        MuseumEntity inMuseum = museumRepository.createInMuseum(museumEntity);

        return MuseumJson.fromEntity(inMuseum);
    }

    @Override
    public void deleteMuseum(UUID museumId) {
        museumRepository.deleteInMuseumById(museumId);
    }

    private byte[] encodedFileBytes(String path) throws IOException {
        File fi = new File(path);
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        String encodedFile = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        return encodedFile.getBytes(StandardCharsets.UTF_8);
    }
}
