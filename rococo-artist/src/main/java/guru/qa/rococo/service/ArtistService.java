package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional
    public @Nonnull
    ArtistJson save(@Nonnull ArtistJson artist) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setBiography(artist.biography());
        artistEntity.setName(artist.name());
        artistEntity.setPhoto(artist.photo() != null ? artist.photo().getBytes(StandardCharsets.UTF_8) : null);
        return ArtistJson.fromEntity(artistRepository.save(artistEntity));
    }

    @Transactional
    public @Nonnull
    ArtistJson update(@Nonnull ArtistJson artist) {
        Optional<ArtistEntity> artistById = artistRepository.findById(artist.id());
        if (artistById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: " + artist.id());
        } else {
            final ArtistEntity artistEntity = artistById.get();
            artistEntity.setBiography(artist.biography());
            artistEntity.setName(artist.name());
            artistEntity.setPhoto(artist.photo() != null ? artist.photo().getBytes(StandardCharsets.UTF_8) : null);
            return ArtistJson.fromEntity(artistRepository.save(artistEntity));
        }
    }

    @Transactional(readOnly = true)
    public @Nonnull
    Page<ArtistJson> allArtists(@Nonnull Pageable pageable,
                                String name) {
        if (name.equals("notSorted")) {
            List<ArtistJson> artistJsons = artistRepository.findAll(pageable)
                    .stream()
                    .map(ArtistJson::fromEntity)
                    .toList();

            return new PageImpl<>(artistJsons, pageable, artistRepository.findAll().size());

        } else {
            List<ArtistJson> artistJsons = artistRepository.findAllByNameContainsIgnoreCase(name, pageable).stream()
                    .map(ArtistJson::fromEntity)
                    .toList();
            return new PageImpl<>(artistJsons, pageable, artistRepository.findAll().size());
        }
    }


    @Transactional(readOnly = true)
    public @Nonnull
    ArtistJson getCurrentArtist(@Nonnull UUID id) {
        return ArtistJson.fromEntity(artistRepository.getReferenceById(id));
    }
}
