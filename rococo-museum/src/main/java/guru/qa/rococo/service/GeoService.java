package guru.qa.rococo.service;


import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
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


//@Component
//public class GeoService {

//    private final GeoRepository geoRepository;
//
//    @Autowired
//    public GeoService(GeoRepository geoRepository) {
//        this.geoRepository = geoRepository;
//    }
//
//    @Transactional
//    public @Nonnull
//    List<CountryJson> getAll(@Nonnull CountryJson country) {
//
//        return CountryJson.fromEntity(geoRepository.findAll());
//    }

//}
