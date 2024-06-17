package org.poo.Gofind.controllers.appareils;

import org.poo.Gofind.dto.appareils.DeviceDTO;
import org.poo.Gofind.dto.appareils.DeviceImageDTO;
import org.poo.Gofind.services.appareils.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.getAll();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{imei}")
    public ResponseEntity<DeviceDTO> getDeviceByImei(@PathVariable String imei) {
        DeviceDTO deviceDTO = deviceService.getByImei(imei);
        if (deviceDTO != null) {
            return ResponseEntity.ok(deviceDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO createdDevice = deviceService.create(deviceDTO);
        return ResponseEntity.ok(createdDevice);
    }

    @PatchMapping("/{imei}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable String imei, @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO updatedDevice = deviceService.update(imei, deviceDTO);
        if (updatedDevice != null) {
            return ResponseEntity.ok(updatedDevice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{imei}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String imei) {
        if (deviceService.deleteDevice(imei)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{imei}/images")
    public ResponseEntity<DeviceImageDTO> uploadImage(@PathVariable String imei, @RequestParam("file") MultipartFile file) {
        try {
            DeviceImageDTO uploadedImage = deviceService.uploadImage(imei, file);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping("/images/{imageId}")
    public ResponseEntity<DeviceImageDTO> updateImage(@PathVariable String imageId, @RequestParam("file") MultipartFile file) {
        try {
            DeviceImageDTO updatedImage = deviceService.updateImage(imageId, file);
            if (updatedImage != null) {
                return ResponseEntity.ok(updatedImage);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageId) {
        if (deviceService.deleteImage(imageId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{imei}/images")
    public ResponseEntity<List<DeviceImageDTO>> getAllImagesForDevice(@PathVariable String imei) {
        List<DeviceImageDTO> deviceImages = deviceService.getAllImages(imei);
        return ResponseEntity.ok(deviceImages);
    }
}
