package org.poo.Gofind.controllers.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.dto.immobilier.LocataireDTO;
import org.poo.Gofind.services.immobilier.LocataireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locataires")
public class LocataireController {

    @Autowired
    private LocataireService locataireService;

    @GetMapping("/{id}")
    public ResponseEntity<LocataireDTO> getLocataire(@PathVariable Long id) {
        Optional<LocataireDTO> locataireOpt = locataireService.findOne(id);
        return locataireOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/commandes")
    public ResponseEntity<List<CommandeDTO>> getCommandesByLocataire(@PathVariable Long id) {
        Optional<LocataireDTO> locataireOpt = locataireService.findOne(id);
        if (locataireOpt.isPresent()) {
            return ResponseEntity.ok(locataireOpt.get().getCommandes());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<Iterable<LocataireDTO>> getAll() {
        return ResponseEntity.ok(locataireService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<LocataireDTO> create(@RequestBody LocataireDTO locataireDTO) {
        LocataireDTO createdLocataire = locataireService.create(locataireDTO);
        return ResponseEntity.ok(createdLocataire);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocataireDTO> update(@PathVariable Long id, @RequestBody LocataireDTO updatedLocataireDTO) {
        Optional<LocataireDTO> updatedLocataire = locataireService.update(id, updatedLocataireDTO);
        return updatedLocataire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locataireService.delete(id);
        return ResponseEntity.ok().build();
    }
}
