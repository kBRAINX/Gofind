package org.poo.Gofind.services.driver;

import org.poo.Gofind.dto.driver.DriverDTO;
import org.poo.Gofind.dto.driver.TrajetDTO;
import org.poo.Gofind.models.driver.Car;
import org.poo.Gofind.models.driver.Driver;
import org.poo.Gofind.models.driver.Trajet;
import org.poo.Gofind.repositories.driver.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    // Enregistrer un chauffeur
    public DriverDTO create(DriverDTO driverDTO) {
        Driver driver = Driver.builder()
            .nom(driverDTO.getNom())
            .email(driverDTO.getEmail())
            .city(driverDTO.getCity())
            .permis(driverDTO.getPermis())
            .build();
        driver = driverRepository.save(driver);
        return mapToDTO(driver);
    }

    // Sélectionner un chauffeur par son id
    public Optional<DriverDTO> findOne(Long id) {
        Optional<Driver> driverOpt = driverRepository.findById(id);
        return driverOpt.map(this::mapToDTO);
    }

    // Sélectionner tous les chauffeurs
    public Iterable<DriverDTO> findAll() {
        return ((List<Driver>) driverRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Modifier un chauffeur par son id
    public Optional<DriverDTO> update(Long id, DriverDTO updatedDriverDTO) {
        Optional<Driver> driverOpt = driverRepository.findById(id);
        if (driverOpt.isPresent()) {
            Driver driver = driverOpt.get();
            driver.setNom(updatedDriverDTO.getNom());
            driver.setEmail(updatedDriverDTO.getEmail());
            driver.setCity(updatedDriverDTO.getCity());
            driver.setPermis(updatedDriverDTO.getPermis());
            driverRepository.save(driver);
            return Optional.of(mapToDTO(driver));
        }
        return Optional.empty();
    }

    // Supprimer un chauffeur par son id
    public void delete(Long id) {
        driverRepository.deleteById(id);
    }

    // Obtenir la liste des trajets d'un chauffeur
    public List<TrajetDTO> findTrajetByDriverId(Long driverId) {
        Optional<Driver> driverOpt = driverRepository.findById(driverId);
        return driverOpt.map(driver -> driver.getTrajetlist().stream()
                .map(this::mapTrajetToDTO)
                .collect(Collectors.toList()))
            .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    // conversion Trajet en TrajetDTO
    private TrajetDTO mapTrajetToDTO(Trajet trajet) {
        return TrajetDTO.builder()
            .id(trajet.getId())
            .start(trajet.getStart())
            .end(trajet.getEnd())
            .H_Start(String.valueOf(trajet.getH_Start()))
            .createdAt(trajet.getCreatedAt().toString())
            .chauffeurId(trajet.getChauffeur().getId())
            .build();
    }

    // conversion Driver en DriverDTO
    private DriverDTO mapToDTO(Driver driver) {
        return DriverDTO.builder()
            .id(driver.getId())
            .nom(driver.getNom())
            .email(driver.getEmail())
            .city(driver.getCity())
            .permis(driver.getPermis())
            .build();
    }
}
