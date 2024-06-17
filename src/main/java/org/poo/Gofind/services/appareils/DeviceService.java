package org.poo.Gofind.services.appareils;

import jakarta.persistence.EntityNotFoundException;
import org.poo.Gofind.dto.DeviceDTO;
import org.poo.Gofind.dto.appareils.DeviceImageDTO;
import org.poo.Gofind.models.User;
import org.poo.Gofind.models.appareils.Category;
import org.poo.Gofind.models.appareils.Device;
import org.poo.Gofind.models.appareils.DeviceImage;
import org.poo.Gofind.repositories.appareils.DeviceImageRepository;
import org.poo.Gofind.repositories.appareils.DeviceRepository;
import org.poo.Gofind.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceImageRepository deviceImageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public List<DeviceDTO> getAll() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
            .map(device -> DeviceDTO.builder()
                .imei(device.getImei())
                .marque(device.getMarque())
                .model(device.getModel())
                .userId(device.getUsers().getId())
                .categorieId(device.getCategories().getId())
                .build())
            .collect(Collectors.toList());
    }

    public DeviceDTO getByImei(String imei) {
        Optional<Device> device = deviceRepository.findById(imei);
        if (device.isPresent()) {
            Device dev = device.get();
            return DeviceDTO.builder()
                .imei(dev.getImei())
                .marque(dev.getMarque())
                .model(dev.getModel())
                .userId(dev.getUsers().getId())
                .categorieId(dev.getCategories().getId())
                .build();
        } else {
            return null;
        }
    }


    public DeviceDTO create(DeviceDTO deviceDTO) {
        User user = userService.findOne(deviceDTO.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé pour l'ID : " + deviceDTO.getUserId()));

        Category category = categoryService.findOne(deviceDTO.getCategorieId())
            .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée pour l'ID : " + deviceDTO.getCategorieId()));

        Device device = Device.builder()
            .imei(deviceDTO.getImei())
            .marque(deviceDTO.getMarque())
            .model(deviceDTO.getModel())
            .users(user)
            .categories(category)
            .build();

        deviceRepository.save(device);

        return deviceDTO;
    }

    public DeviceDTO update(String imei, DeviceDTO deviceDTO) {
        Optional<Device> existingDevice = deviceRepository.findById(imei);
        User user = userService.findOne(deviceDTO.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé pour l'ID : " + deviceDTO.getUserId()));

        Category category = categoryService.findOne(deviceDTO.getCategorieId())
            .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée pour l'ID : " + deviceDTO.getCategorieId()));
        if (existingDevice.isPresent()) {
            Device device = existingDevice.get();
            device.setMarque(deviceDTO.getMarque());
            device.setModel(deviceDTO.getModel());
            device.setUsers(user);
            device.setCategories(category);
            device = deviceRepository.save(device);
            return deviceDTO;
        } else {
            return null;
        }
    }

    public boolean deleteDevice(String imei) {
        if (deviceRepository.existsById(imei)) {
            deviceRepository.deleteById(imei);
            return true;
        } else {
            return false;
        }
    }

    public DeviceImageDTO uploadImage(String imei, MultipartFile file) throws IOException {
        Optional<Device> optionalDevice = deviceRepository.findById(imei);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            DeviceImage deviceImage = DeviceImage.builder()
                .device(device)
                .imageData(file.getBytes())
                .build();
            deviceImage = deviceImageRepository.save(deviceImage);
            return DeviceImageDTO.builder()
                .imageId(deviceImage.getImageId())
                .deviceImei(device.getImei())
                .imageData(deviceImage.getImageData())
                .build();
        } else {
            return null;
        }
    }

    public DeviceImageDTO updateImage(String imageId, MultipartFile file) throws IOException {
        Optional<DeviceImage> optionalDeviceImage = deviceImageRepository.findById(imageId);
        if (optionalDeviceImage.isPresent()) {
            DeviceImage deviceImage = optionalDeviceImage.get();
            deviceImage.setImageData(file.getBytes());
            deviceImage = deviceImageRepository.save(deviceImage);
            return DeviceImageDTO.builder()
                .imageId(deviceImage.getImageId())
                .deviceImei(deviceImage.getDevice().getImei())
                .imageData(deviceImage.getImageData())
                .build();
        } else {
            return null;
        }
    }

    public boolean deleteImage(String imageId) {
        if (deviceImageRepository.existsById(imageId)) {
            deviceImageRepository.deleteById(imageId);
            return true;
        } else {
            return false;
        }
    }

    public List<DeviceImageDTO> getAllImages(String imei) {
        List<DeviceImage> deviceImages = deviceImageRepository.findByDevice_Imei(imei);
        return deviceImages.stream()
            .map(deviceImage -> DeviceImageDTO.builder()
                .imageId(deviceImage.getImageId())
                .deviceImei(deviceImage.getDevice().getImei())
                .imageData(deviceImage.getImageData())
                .build())
            .collect(Collectors.toList());
    }
}
