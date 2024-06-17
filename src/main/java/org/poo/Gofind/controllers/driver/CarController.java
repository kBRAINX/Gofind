package org.poo.Gofind.controllers.driver;

import org.poo.Gofind.dto.driver.CarDTO;
import org.poo.Gofind.services.driver.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{matricule}")
    public ResponseEntity<CarDTO> getCar(@PathVariable String matricule) {
        Optional<CarDTO> carOpt = carService.findOne(matricule);
        return carOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<Iterable<CarDTO>> getAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
        CarDTO createdCar = carService.create(carDTO);
        return ResponseEntity.ok(createdCar);
    }

    @PatchMapping("/{matricule}")
    public ResponseEntity<CarDTO> update(@PathVariable String matricule, @RequestBody CarDTO updatedCarDTO) {
        Optional<CarDTO> updatedCar = carService.update(matricule, updatedCarDTO);
        return updatedCar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricule}")
    public ResponseEntity<Void> delete(@PathVariable String matricule) {
        carService.delete(matricule);
        return ResponseEntity.ok().build();
    }
}
