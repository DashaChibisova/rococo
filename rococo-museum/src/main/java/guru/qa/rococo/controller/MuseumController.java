package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MuseumController {

//  private final GeoService geoService;
//
//  @Autowired
//  public MuseumController(GeoService geoService) {
//    this.geoService = geoService;
//  }
//
//  @GetMapping("/getAllCountry")
//  public List<CountryJson> getAllCountry() {
//    return geoService.getAllCurrencies();
//  }
//
//  @GetMapping("/getAllCity")
//  public List<String> getAllCity() {
//    return geoService.getAllCurrencies();
//  }
}
