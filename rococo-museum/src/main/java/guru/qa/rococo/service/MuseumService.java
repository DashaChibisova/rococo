package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class MuseumService {

    private final MuseumRepository museumRepository;

    @Autowired
    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    @Transactional
    public @Nonnull
    Page<MuseumJson> getAll(@Nonnull Pageable pageable
                           ) {
            List<MuseumJson> museumJsons = museumRepository.findAll(pageable)
                    .stream()
                    .map(MuseumJson::fromEntity)
                    .toList();
            return new PageImpl<>(museumJsons);
    }

    @Transactional
    public @Nonnull
    MuseumJson getCurrentAMuseum(@Nonnull UUID id) {
        return MuseumJson.fromEntity(museumRepository.getReferenceById(id));

    }

    @Transactional
    public @Nonnull
    MuseumJson save(@Nonnull MuseumJson museum) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setDescription(museum.description());
        museumEntity.setTitle(museum.title());

        CountryJson country = museum.geo().country();
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(country.name());

        GeoJson geo = museum.geo();
        GeoEntity geoEntity = new GeoEntity();
        geoEntity.setCity(geo.city());
        museumEntity.setGeo(geoEntity);

        museumEntity.setPhoto(museum.photo() != null ? museum.photo().getBytes(StandardCharsets.UTF_8) : null);
        return MuseumJson.fromEntity(museumRepository.save(museumEntity));
    }

    @Transactional
    public @Nonnull
    MuseumJson update(@Nonnull MuseumJson museum) {
        Optional<MuseumEntity> museumById = museumRepository.findById(museum.id());
        if (museumById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find museumById by given id: " + museum.id());
        } else {
            MuseumEntity museumEntity = new MuseumEntity();
            museumEntity.setDescription(museum.description());
            museumEntity.setTitle(museum.title());

            CountryJson country = museum.geo().country();
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setName(country.name());

            GeoJson geo = museum.geo();
            GeoEntity geoEntity = new GeoEntity();
            geoEntity.setCity(geo.city());
            museumEntity.setGeo(geoEntity);

            museumEntity.setPhoto(museum.photo() != null ? museum.photo().getBytes(StandardCharsets.UTF_8) : null);
            return MuseumJson.fromEntity(museumRepository.save(museumEntity));
        }
    }
}