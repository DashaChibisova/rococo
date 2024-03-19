package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.api.RestArtistDataClient;
import guru.qa.rococo.service.api.RestMuseumDataClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<CountryJson> getAll(@RequestParam(required = false) String name,
                                    @PageableDefault Pageable pageable) {
        return museumDataClient.getCountries(pageable, name);
    }
}
