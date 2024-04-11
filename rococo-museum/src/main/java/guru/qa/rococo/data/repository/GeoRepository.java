package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    String findByCity(String id);
    UUID findByCountryId(UUID id);

}
