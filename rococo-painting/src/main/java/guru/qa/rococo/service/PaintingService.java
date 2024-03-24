package guru.qa.rococo.service;


import guru.qa.rococo.client.RestArtistDataClient;
import guru.qa.rococo.client.RestMuseumDataClient;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.PaintingRepository;

import guru.qa.rococo.model.PaintingJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


@Component
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final RestMuseumDataClient restMuseumDataClient;
    private final RestArtistDataClient restArtistDataClient;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository,
    RestMuseumDataClient restMuseumDataClient,RestArtistDataClient restArtistDataClient) {
        this.paintingRepository = paintingRepository;
        this.restMuseumDataClient = restMuseumDataClient;
        this.restArtistDataClient = restArtistDataClient;
    }

    @Transactional
    public @Nonnull
    Page<PaintingJson> getAll(@Nonnull Pageable pageable
                           ) {

        List<PaintingJson> paintingJsons = paintingRepository.findAll(pageable)
                    .stream()
                    .map(this::fromEntity)
                    .toList();
            return new PageImpl<>(paintingJsons);
    }

//    @Transactional
//    public @Nonnull
//    PaintingJson getCurrentPainting(@Nonnull UUID id) {
//        return PaintingJson.fromEntity(paintingRepository.getReferenceById(id));
//
//    }

        public PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
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
