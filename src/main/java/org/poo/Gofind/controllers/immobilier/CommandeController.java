package org.poo.Gofind.controllers.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.services.immobilier.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getCommande(@PathVariable Long id) {
        Optional<CommandeDTO> commandeOpt = commandeService.findOne(id);
        return commandeOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<Iterable<CommandeDTO>> getAll() {
        return ResponseEntity.ok(commandeService.findAll());
    }

    @GetMapping("/locataire/{locataireId}")
    public ResponseEntity<Iterable<CommandeDTO>> getCommandesByLocataire(@PathVariable Long locataireId) {
        return ResponseEntity.ok(commandeService.findByLocataireId(locataireId));
    }

    @PostMapping("")
    public ResponseEntity<CommandeDTO> create(@RequestBody CommandeDTO commandeDTO) {
        CommandeDTO createdCommande = commandeService.create(commandeDTO);
        return ResponseEntity.ok(createdCommande);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommandeDTO> update(@PathVariable Long id, @RequestBody CommandeDTO updatedCommandeDTO) {
        Optional<CommandeDTO> updatedCommande = commandeService.update(id, updatedCommandeDTO);
        return updatedCommande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
