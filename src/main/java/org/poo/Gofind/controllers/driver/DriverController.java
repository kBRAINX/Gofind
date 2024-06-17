package org.poo.Gofind.controllers.driver;

import org.poo.Gofind.dto.driver.DriverDTO;
import org.poo.Gofind.dto.driver.TrajetDTO;
import org.poo.Gofind.services.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("")
    public ResponseEntity<Iterable<DriverDTO>> getAll() {
        return ResponseEntity.ok(driverService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Long id) {
        Optional<DriverDTO> driverOpt = driverService.findOne(id);
        return driverOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/trajets")
    public ResponseEntity<List<TrajetDTO>> getTrajetsByDriverId(@PathVariable Long id) {
        List<TrajetDTO> trajets = driverService.findTrajetByDriverId(id);
        return ResponseEntity.ok(trajets);
    }

    @PostMapping("")
    public ResponseEntity<DriverDTO> create(@RequestBody DriverDTO driverDTO) {
        DriverDTO createdDriver = driverService.create(driverDTO);
        return ResponseEntity.ok(createdDriver);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverDTO> update(@PathVariable Long id, @RequestBody DriverDTO updatedDriverDTO) {
        Optional<DriverDTO> updatedDriver = driverService.update(id, updatedDriverDTO);
        return updatedDriver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.delete(id);
        return ResponseEntity.ok().build();
    }
}
