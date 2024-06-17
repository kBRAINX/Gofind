package org.poo.Gofind.controllers.driver;

import org.poo.Gofind.dto.driver.PassagerDTO;
import org.poo.Gofind.services.driver.PassagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passagers")
public class PassagerController {

    @Autowired
    private PassagerService passagerService;

    @PostMapping("")
    public ResponseEntity<PassagerDTO> createPassager(@RequestBody PassagerDTO passagerDTO) {
        PassagerDTO createdPassager = passagerService.create(passagerDTO);
        return ResponseEntity.ok(createdPassager);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassagerDTO> updatePassager(@PathVariable Long id, @RequestBody PassagerDTO updatedPassagerDTO) {
        Optional<PassagerDTO> updatedPassager = passagerService.update(id, updatedPassagerDTO);
        return updatedPassager.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerDTO> getPassager(@PathVariable Long id) {
        Optional<PassagerDTO> passagerOpt = passagerService.findOne(id);
        return passagerOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<PassagerDTO>> getAllPassagers() {
        return ResponseEntity.ok(passagerService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassager(@PathVariable Long id) {
        passagerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
