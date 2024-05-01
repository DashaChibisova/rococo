package guru.qa.rococo.jupiter.extension;


import guru.qa.rococo.db.model.CountryEntity;
import guru.qa.rococo.db.model.GeoEntity;
import guru.qa.rococo.db.model.MuseumEntity;
import guru.qa.rococo.db.repository.MuseumRepository;
import guru.qa.rococo.db.repository.MuseumRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.utils.DataUtils;
import guru.qa.rococo.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
                ? "Азербайджан"
                : museum.country();

        byte[] photo = museum.photoPath().isEmpty()
                ?  FileUtils.encodedFileBytes("images/museum.png")
                :  FileUtils.encodedFileBytes(museum.photoPath());

        CountryEntity countryEntity = museumRepository.findCountryByName(country);

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
}
