package guru.qa.rococo.controller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.model.PaintingJsonSave;
import guru.qa.rococo.service.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class PaintingController {

  private final PaintingService paintingService;

  @Autowired
  public PaintingController(PaintingService paintingService) {
    this.paintingService = paintingService;
  }

  @GetMapping("/painting")
  public Page<PaintingJson> getAllPainting(@PageableDefault Pageable pageable) {
    return paintingService.getAll(pageable);
  }

  @GetMapping("/painting/{id}")
  public PaintingJson getCurrentPainting(@PathVariable UUID id) {
    return paintingService.getCurrentPainting(id);
  }

  @PostMapping("/painting")
  public PaintingJson savePainting(@RequestBody PaintingJson painting) {
    return paintingService.save(painting);
  }

  @PatchMapping("/painting")
  public PaintingJson updatePaintingInfo(@RequestBody PaintingJson painting) {
    return paintingService.update(painting);
  }

//  @GetMapping("/painting/author/{id}")
//  public Page<PaintingJson> getPaintingByAuthor(@PathVariable UUID id, @PageableDefault Pageable pageable) {
//    return paintingService.getPaintingByAuthorId(id,pageable);
//  }
}
