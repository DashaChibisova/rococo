package guru.qa.rococo.service;

import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class MuseumService {

    private final MuseumRepository museumRepository;

    @Autowired
    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    @Transactional
    public @Nonnull
    List<MuseumJson> getAll() {
        return museumRepository.findAll()
                .stream()
                .map(MuseumJson::fromEntity)
                .toList();
    }

}
