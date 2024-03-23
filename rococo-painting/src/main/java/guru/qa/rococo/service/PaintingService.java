package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
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
public class PaintingService {

    private final PaintingRepository paintingRepository;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @Transactional
    public @Nonnull
    Page<PaintingJson> getAll(@Nonnull Pageable pageable
                           ) {
            List<PaintingJson> paintingJsons = paintingRepository.findAll(pageable)
                    .stream()
                    .map(PaintingJson::fromEntity)
                    .toList();
            return new PageImpl<>(paintingJsons);
    }

    @Transactional
    public @Nonnull
    PaintingJson getCurrentPainting(@Nonnull UUID id) {
        return PaintingJson.fromEntity(paintingRepository.getReferenceById(id));

    }

    @Transactional
    public @Nonnull
    PaintingJson save(@Nonnull PaintingJson painting) {
//        PaintingEntity paintingEntity = new PaintingEntity();
//        paintingEntity.setDescription(painting.description());
//        paintingEntity.setTitle(painting.title());
//
//        CountryJson country = museum.geo().country();
//        CountryEntity referenceById = сountryRepository.getReferenceById(country.id());
//
//        GeoJson geo = museum.geo();
//        GeoEntity geoEntity = new GeoEntity();
//        geoEntity.setCity(geo.city());
//        geoEntity.setCountry(referenceById);
//
//        paintingEntity.setGeo(geoEntity);
//        paintingEntity.setPhoto(museum.photo() != null ? museum.photo().getBytes(StandardCharsets.UTF_8) : null);
//        return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
        return null;
    }

    @Transactional
    public @Nonnull
    PaintingJson update(@Nonnull PaintingJson painting) {
//        Optional<PaintingEntity> museumById = paintingRepository.findById(painting.id());
//        if (museumById.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find museumById by given id: " + museum.id());
//        } else {
//            PaintingEntity paintingEntity = new PaintingEntity();
//            paintingEntity.setId(painting.id());
//            paintingEntity.setDescription(painting.description());
//            paintingEntity.setTitle(painting.title());
//
//            MuseumJson museumJson = painting.museum();
//            MuseumEntity museumEntity = new MuseumEntity();
//            museumEntity.setId(museumJson.id());
//            museumEntity.setTitle(museumJson.title());
//            museumEntity.setDescription(museumJson.description());
//            museumEntity.setPhoto(museumJson.photo() != null ? museumJson.photo().getBytes(StandardCharsets.UTF_8) : null);
//
//
//            CountryJson country = painting.museum().geo().country();
//            CountryEntity referenceById = сountryRepository.getReferenceById(country.id());
//
//            GeoJson geo = museum.geo();
//            GeoEntity geoEntity = new GeoEntity();
//            geoEntity.setCity(geo.city());
//            geoEntity.setCountry(referenceById);
//
//            museumEntity.setGeo(geoEntity);
//            museumEntity.setPhoto(museum.photo() != null ? museum.photo().getBytes(StandardCharsets.UTF_8) : null);
//
//            paintingEntity.setContent(painting.content() != null ? painting.content().getBytes(StandardCharsets.UTF_8) : null);
            return null;
//        }
    }
}
