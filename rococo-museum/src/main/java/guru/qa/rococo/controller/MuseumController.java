package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.GeoService;
import guru.qa.rococo.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MuseumController {

  private final MuseumService museumService;

  @Autowired
  public MuseumController(MuseumService museumService) {
    this.museumService = museumService;
  }

  @GetMapping("/museum")
  public List<MuseumJson> getAllCountry() {
    return museumService.getAll();
  }
}
