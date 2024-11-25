package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/artist")
    public ArtistJson saveArtists(@RequestBody ArtistJson artist) {
        return artistService.save(artist);
    }

    @PatchMapping("/artist")
    public ArtistJson updateArtistInfo(@RequestBody ArtistJson artist) {
        return artistService.update(artist);
    }

    @GetMapping("/artist")
    public Page<ArtistJson> allArtists(@PageableDefault Pageable pageable,
                                       @RequestParam(defaultValue = "notSorted") String name) {
        return artistService.allArtists(pageable, name);
    }

    @GetMapping("/artist/{id}")
    public ArtistJson currentArtist(@PathVariable UUID id) {
        return artistService.getCurrentArtist(id);
    }
}
