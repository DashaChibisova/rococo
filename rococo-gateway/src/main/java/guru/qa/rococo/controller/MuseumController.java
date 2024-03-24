package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.api.RestMuseumDataClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "api")
@RestController
public class MuseumController {
    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);

    private final RestMuseumDataClient museumDataClient;

    @Autowired
    public MuseumController(RestMuseumDataClient museumDataClient) {
        this.museumDataClient = museumDataClient;
    }

    @GetMapping("/country")
    public Page<CountryJson> getAllCountry(@RequestParam(required = false) String name,
                                           @PageableDefault Pageable pageable) {
        return museumDataClient.getCountries(pageable, name);
    }

    @GetMapping("/museum")
    public Page<MuseumJson> getAllMuseum(@PageableDefault Pageable pageable) {
        return museumDataClient.getAllMuseum(pageable);
    }

    @GetMapping("/museum/{id}")
    public MuseumJson getCurrentMuseum(@PathVariable UUID id) {
        return museumDataClient.getCurrentMuseum(id);
    }

    @PostMapping("/museum")
    public MuseumJson saveMuseum(@RequestBody MuseumJson museum) {
        return museumDataClient.saveMuseum(museum);
    }

    @PatchMapping("/museum")
    public MuseumJson updateMuseumInfo(@RequestBody MuseumJson museum) {
        return museumDataClient.updateMuseumInfo(museum);
    }

}
