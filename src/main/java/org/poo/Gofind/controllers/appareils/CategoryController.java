package org.poo.Gofind.controllers.appareils;

import org.poo.Gofind.dto.DeviceDTO;
import org.poo.Gofind.dto.appareils.CategoryDto;
import org.poo.Gofind.models.appareils.Category;
import org.poo.Gofind.models.appareils.Device;
import org.poo.Gofind.services.appareils.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public Iterable<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findOne(id)
            .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByCategoryId(@PathVariable Long id) {
        Optional<Category> categoryOpt = categoryService.findOne(id);
        if (categoryOpt.isPresent()) {
            List<Device> devices = categoryOpt.get().getDeviceList();
            // Conversion List<Device> en List<DeviceDTO>
            List<DeviceDTO> deviceDTOs = devices.stream()
                .map(device -> DeviceDTO.builder()
                    .imei(device.getImei())
                    .marque(device.getMarque())
                    .model(device.getModel())
                    .userId(device.getUsers() != null ? device.getUsers().getId() : null)
                    .categorieId(device.getCategories() != null ? device.getCategories().getId() : null)
                    .build())
                .collect(Collectors.toList());
            return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> update(@RequestBody CategoryDto updatedCategory, @PathVariable Long id) {
        return categoryService.update(id, updatedCategory)
            .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
