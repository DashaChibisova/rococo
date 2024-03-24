package guru.qa.rococo.controller;

import guru.qa.rococo.model.*;
import guru.qa.rococo.service.api.RestPaintingDataClient;
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
public class PaintingController {
    private static final Logger LOG = LoggerFactory.getLogger(PaintingController.class);

    private final RestPaintingDataClient paintingDataClient;

    @Autowired
    public PaintingController(RestPaintingDataClient paintingDataClient) {
        this.paintingDataClient = paintingDataClient;
    }

    @GetMapping("/painting")
    public Page<PaintingJson> getAllPainting(@RequestParam(required = false) String name,
                                             @PageableDefault Pageable pageable) {
        return paintingDataClient.getPaintings(pageable, name);
    }


    @GetMapping("/painting/{id}")
    public PaintingJson getCurrentPainting(@PathVariable UUID id) {
        return paintingDataClient.getCurrentPainting(id);
    }

    @PostMapping("/painting")
    public PaintingJson savePainting(@RequestBody PaintingJson painting) {
        return paintingDataClient.savePainting(painting);
    }

    @PatchMapping("/painting")
    public PaintingJson updatePaintingInfo(@RequestBody PaintingJson painting) {
        return paintingDataClient.updatePaintingInfo(painting);
    }

    @GetMapping("/painting/author/{id}")
    public Page<PaintingJson> getPaintingByAuthor(@PathVariable UUID id, @PageableDefault Pageable pageable) {
        return paintingDataClient.getPaintingByAuthorId(id, pageable);
    }

}
