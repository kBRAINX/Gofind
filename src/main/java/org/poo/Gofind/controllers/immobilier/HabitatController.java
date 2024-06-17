package org.poo.Gofind.controllers.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.dto.immobilier.HabitatDTO;
import org.poo.Gofind.dto.immobilier.LocataireDTO;
import org.poo.Gofind.models.immobilier.Habitat;
import org.poo.Gofind.services.immobilier.HabitatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitats")
public class HabitatController {

    @Autowired
    private HabitatService habitatService;

    @GetMapping("/{id}")
    public ResponseEntity<HabitatDTO> getHabitat(@PathVariable Long id) {
        Optional<HabitatDTO> habitatOpt = habitatService.findOne(id);
        return habitatOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<HabitatDTO>> getAll() {
        return ResponseEntity.ok(habitatService.getAll());
    }

    @GetMapping("/{id}/commandes")
    public ResponseEntity<List<CommandeDTO>> getCommandesByHabitat(@PathVariable Long id) {
        Optional<HabitatDTO> habitatOpt = habitatService.findOne(id);
        if (habitatOpt.isPresent()) {
            return ResponseEntity.ok(habitatOpt.get().getCommandes());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<HabitatDTO> create(@RequestBody HabitatDTO habitatDTO) {
        HabitatDTO createdHabitat = habitatService.create(habitatDTO);
        return ResponseEntity.ok(createdHabitat);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HabitatDTO> update(@PathVariable Long id, @RequestBody HabitatDTO updatedHabitatDTO) {
        Optional<HabitatDTO> updatedHabitat = habitatService.update(id, updatedHabitatDTO);
        return updatedHabitat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitatService.delete(id);
        return ResponseEntity.ok().build();
    }
}
