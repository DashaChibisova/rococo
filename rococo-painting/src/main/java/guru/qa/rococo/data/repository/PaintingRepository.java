package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {
    @Nonnull
    Page<PaintingEntity> findAllByArtist(
            @Nonnull UUID artist,
            @Nonnull Pageable pageable
    );


    @Nonnull
    Page<PaintingEntity> findAllByTitleContainsIgnoreCase(
            @Nonnull String title,
            @Nonnull Pageable pageable
    );

//    @Nonnull
//    List<PaintingEntity> findAllByIdArtist(
//            @Nonnull UUID artist
//    );
}
