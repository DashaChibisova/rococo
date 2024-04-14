package guru.qa.rococo.service;


import guru.qa.rococo.client.RestArtistDataClient;
import guru.qa.rococo.client.RestMuseumDataClient;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.PaintingRepository;

import guru.qa.rococo.model.*;
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
    private final RestMuseumDataClient restMuseumDataClient;
    private final RestArtistDataClient restArtistDataClient;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository,
                           RestMuseumDataClient restMuseumDataClient, RestArtistDataClient restArtistDataClient) {
        this.paintingRepository = paintingRepository;
        this.restMuseumDataClient = restMuseumDataClient;
        this.restArtistDataClient = restArtistDataClient;
    }

    @Transactional
    public @Nonnull
    Page<PaintingJson> getAll(@Nonnull Pageable pageable
            , String title) {
        if (title.equals("notSorted")) {

            List<PaintingJson> paintingJsons = paintingRepository.findAll(pageable)
                    .stream()
                    .map(this::fromEntity)
                    .toList();
            return new PageImpl<>(paintingJsons, pageable, paintingRepository.findAll().size());
        } else {
            List<PaintingJson> paintingJsons = paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable).stream()
                    .map(this::fromEntity)
                    .toList();
            return new PageImpl<>(paintingJsons, pageable, paintingRepository.findAll().size());
        }
    }

    @Transactional
    public @Nonnull
    PaintingJson getCurrentPainting(@Nonnull UUID id) {
        return fromEntity(paintingRepository.getReferenceById(id));

    }

    @Transactional
    public @Nonnull
    PaintingJson save(@Nonnull PaintingJson painting) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(painting.title());
        paintingEntity.setDescription(painting.description());
        paintingEntity.setArtist(painting.artist().id());
        paintingEntity.setMuseum(painting.museum().id());
        paintingEntity.setContent(painting.content() != null ? painting.content().getBytes(StandardCharsets.UTF_8) : null);

        return fromEntity(paintingRepository.save(paintingEntity));
    }

    @Transactional
    public @Nonnull
    PaintingJson update(@Nonnull PaintingJson painting) {
        Optional<PaintingEntity> museumById = paintingRepository.findById(painting.id());
        if (museumById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find paintingById by given id: " + painting.id());
        } else {
            PaintingEntity paintingEntity = new PaintingEntity();
            paintingEntity.setId(painting.id());
            paintingEntity.setTitle(painting.title());
            paintingEntity.setDescription(painting.description());
            paintingEntity.setArtist(painting.artist().id());
            paintingEntity.setMuseum(painting.museum().id());
            paintingEntity.setContent(painting.content() != null ? painting.content().getBytes(StandardCharsets.UTF_8) : null);

            return fromEntity(paintingRepository.save(paintingEntity));
        }
    }

    @Transactional
    public @Nonnull
    Page<PaintingJson> getPaintingByAuthorId(@Nonnull UUID id, @Nonnull Pageable pageable) {
        List<PaintingJson> paintingJsons = paintingRepository.findAllByArtist(id, pageable)
                .stream()
                .map(this::fromEntity)
                .toList();
        return new PageImpl<>(paintingJsons, pageable, paintingRepository.findAllByArtist(id).size());
    }


    private PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
        return new PaintingJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent() != null && entity.getContent().length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null,
                restMuseumDataClient.getCurrentMuseum(entity.getMuseum()),
                restArtistDataClient.getCurrentArtist(entity.getArtist())
        );
    }
}
