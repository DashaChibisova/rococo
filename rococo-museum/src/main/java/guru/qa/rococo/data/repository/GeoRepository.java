package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {


}
