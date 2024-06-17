package org.poo.Gofind.controllers.immobilier;

import org.poo.Gofind.dto.immobilier.HabitatDTO;
import org.poo.Gofind.dto.immobilier.ProprietaireDTO;
import org.poo.Gofind.services.immobilier.HabitatService;
import org.poo.Gofind.services.immobilier.ProprietaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proprietaires")
public class ProprietaireController {

    @Autowired
    private ProprietaireService proprietaireService;

    @Autowired
    private HabitatService habitatService;


    @GetMapping
    public ResponseEntity<Iterable<ProprietaireDTO>> getAll() {
        return ResponseEntity.ok(proprietaireService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietaireDTO> getProprietaire(@PathVariable Long id) {
        Optional<ProprietaireDTO> proprietaire = proprietaireService.findById(id);
        return proprietaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProprietaireDTO> create(@RequestBody ProprietaireDTO proprietaireDTO) {
        ProprietaireDTO createdProprietaire = proprietaireService.create(proprietaireDTO);
        return ResponseEntity.ok(createdProprietaire);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProprietaireDTO> update(@PathVariable Long id, @RequestBody ProprietaireDTO updatedProprietaireDTO) {
        Optional<ProprietaireDTO> updatedProprietaire = proprietaireService.update(id, updatedProprietaireDTO);
        return updatedProprietaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/habitats")
    public ResponseEntity<List<HabitatDTO>> getHabitatsByProprietaire(@PathVariable Long id) {
        List<HabitatDTO> habitats = habitatService.findByProprietaireId(id);
        return ResponseEntity.ok(habitats);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietaire(@PathVariable Long id) {
        proprietaireService.delete(id);
        return ResponseEntity.ok().build();
    }
}
