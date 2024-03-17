package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
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
    return artistService.allArtists(pageable,name);
  }



//  @GetMapping("/currentArtist") //id on end url????
//  public ArtistJson currentArtist(@RequestParam UUID idArtist) {
//    return artistService.getCurrentArtist();
//  }








}
