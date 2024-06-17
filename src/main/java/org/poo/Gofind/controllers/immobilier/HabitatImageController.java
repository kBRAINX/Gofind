package org.poo.Gofind.controllers.immobilier;

import org.poo.Gofind.dto.immobilier.HabitatImageDTO;
import org.poo.Gofind.services.immobilier.HabitatImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/habitats")
public class HabitatImageController {

    @Autowired
    private HabitatImageService habitatImageService;

    @PostMapping("/{habitatId}/images")
    public ResponseEntity<HabitatImageDTO> uploadImage(@PathVariable Long habitatId, @RequestParam("file") MultipartFile file) {
        try {
            HabitatImageDTO uploadedImage = habitatImageService.uploadImage(habitatId, file);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{habitatId}/images")
    public ResponseEntity<Iterable<HabitatImageDTO>> getImagesByHabitatId(@PathVariable Long habitatId) {
        Iterable<HabitatImageDTO> images = habitatImageService.getImages(habitatId);
        return ResponseEntity.ok(images);
    }

    @PatchMapping("/images/{imageId}")
    public ResponseEntity<HabitatImageDTO> updateImage(@PathVariable Long imageId, @RequestParam("file") MultipartFile file) {
        try {
            HabitatImageDTO updatedImage = habitatImageService.updateImage(imageId, file);
            return ResponseEntity.ok(updatedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        habitatImageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
