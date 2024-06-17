package org.poo.Gofind.controllers.driver;

import org.poo.Gofind.dto.driver.TrajetDTO;
import org.poo.Gofind.dto.driver.PassagerDTO;
import org.poo.Gofind.services.driver.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trajets")
public class TrajetController {

    @Autowired
    private TrajetService trajetService;

    @GetMapping("/{id}")
    public ResponseEntity<TrajetDTO> getTrajet(@PathVariable Long id) {
        Optional<TrajetDTO> trajetOpt = trajetService.findOne(id);
        return trajetOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<TrajetDTO>> getAll() {
        return ResponseEntity.ok(trajetService.findAll());
    }

    @GetMapping("/{id}/passagers")
    public ResponseEntity<List<PassagerDTO>> getPassagerByTrajetId(@PathVariable Long id) {
        List<PassagerDTO> passagers = trajetService.findPassagersByTrajetId(id);
        return ResponseEntity.ok(passagers);
    }

    @PostMapping("")
    public ResponseEntity<TrajetDTO> create(@RequestBody TrajetDTO trajetDTO) {
        TrajetDTO createdTrajet = trajetService.create(trajetDTO);
        return ResponseEntity.ok(createdTrajet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrajetDTO> update(@PathVariable Long id, @RequestBody TrajetDTO updatedTrajetDTO) {
        Optional<TrajetDTO> updatedTrajet = trajetService.update(id, updatedTrajetDTO);
        return updatedTrajet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trajetService.delete(id);
        return ResponseEntity.ok().build();
    }
}
