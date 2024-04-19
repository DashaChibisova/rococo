package guru.qa.rococo.service;

import guru.qa.rococo.data.repository.CountryRepository;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Component
public class CountryService {

    private final CountryRepository сountryRepository;

    @Autowired
    public CountryService(CountryRepository сountryRepository) {
        this.сountryRepository = сountryRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull
    Page<CountryJson> getAll(@Nonnull Pageable pageable,
                             String name) {

        if (name.equals("notSorted")) {
            List<CountryJson> countryJsons = сountryRepository.findAll(pageable)
                    .stream()
                    .map(CountryJson::fromEntity)
                    .toList();
            return new PageImpl<>(countryJsons);

        } else {

            List<CountryJson> countryJsons = сountryRepository.findAllByNameContainsIgnoreCase(name, pageable).stream()
                    .map(CountryJson::fromEntity)
                    .toList();
            return new PageImpl<>(countryJsons);
        }
    }

}
