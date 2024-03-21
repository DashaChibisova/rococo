package guru.qa.rococo.controller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class MuseumController {

  private final MuseumService museumService;

  @Autowired
  public MuseumController(MuseumService museumService) {
    this.museumService = museumService;
  }

  @GetMapping("/museum")
  public Page<MuseumJson> getAllMuseum(@PageableDefault Pageable pageable) {
    return museumService.getAll(pageable);
  }

  @GetMapping("/museum/{id}")
  public MuseumJson getCurrentMuseum(@PathVariable UUID id) {
    return museumService.getCurrentAMuseum(id);
  }

  @PostMapping("/artist")
  public MuseumJson saveMuseum(@RequestBody MuseumJson museum) {
    return museumService.save(museum);
  }

  @PatchMapping("/artist")
  public MuseumJson updateMuseumInfo(@RequestBody MuseumJson museum) {
    return museumService.update(museum);
  }
}
