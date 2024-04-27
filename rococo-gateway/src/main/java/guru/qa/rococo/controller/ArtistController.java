package guru.qa.rococo.controller;


import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.api.RestArtistDataClient;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "api")
@RestController
public class ArtistController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);

    private final RestArtistDataClient artistDataClient;

    @Autowired
    public ArtistController(RestArtistDataClient artistDataClient) {
        this.artistDataClient = artistDataClient;
    }

    @PatchMapping("/artist")
    public ArtistJson userUpdateInfo(@RequestBody ArtistJson artist) {
        return artistDataClient.updateArtistInfo(artist);
    }

    @PostMapping("/artist")
    public ArtistJson saveArtists(@RequestBody ArtistJson artist) {
        return artistDataClient.saveArtists(artist);
    }


    @GetMapping("/artist")
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistDataClient.getArtists(pageable, name);
    }

    @GetMapping("/artist/{id}")
    public ArtistJson currentArtist(@PathVariable UUID id) {
        return artistDataClient.getCurrentArtist(id);
    }
}
