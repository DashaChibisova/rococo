package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GeoController {

  private final GeoService geoService;

  @Autowired
  public GeoController(GeoService geoService) {
    this.geoService = geoService;
  }

  @GetMapping("/geo{id}")
  public GeoJson getCurrentGeo(@PathVariable UUID id) {
    return geoService.getGeo(id);
  }
}
