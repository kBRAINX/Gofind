package org.poo.Gofind.services.driver;

import org.poo.Gofind.dto.driver.CarDTO;
import org.poo.Gofind.models.driver.Car;
import org.poo.Gofind.models.driver.Driver;
import org.poo.Gofind.repositories.driver.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Enregistrer une voiture
    public CarDTO create(CarDTO carDTO) {
        Car car = Car.builder()
            .matricule(carDTO.getMatricule())
            .marque(carDTO.getMarque())
            .places(carDTO.getPlaces())
            .chauffeur(Driver.builder().id(carDTO.getChauffeurId()).build())
            .build();
        car = carRepository.save(car);
        return mapToDTO(car);
    }

    // Sélectionner une voiture par son matricule
    public Optional<CarDTO> findOne(String matricule) {
        Optional<Car> carOpt = carRepository.findById(matricule);
        return carOpt.map(this::mapToDTO);
    }

    // Sélectionner toutes les voitures
    public Iterable<CarDTO> findAll() {
        return ((List<Car>) carRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Modifier une voiture par son matricule
    public Optional<CarDTO> update(String matricule, CarDTO updatedCarDTO) {
        Optional<Car> carOpt = carRepository.findById(matricule);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            car.setMarque(updatedCarDTO.getMarque());
            car.setPlaces(updatedCarDTO.getPlaces());
            car.setChauffeur(Driver.builder().id(updatedCarDTO.getChauffeurId()).build());
            carRepository.save(car);
            return Optional.of(mapToDTO(car));
        }
        return Optional.empty();
    }

    // Supprimer une voiture par son matricule
    public void delete(String matricule) {
        carRepository.deleteById(matricule);
    }

    // Mapper Car en CarDTO
    private CarDTO mapToDTO(Car car) {
        return CarDTO.builder()
            .matricule(car.getMatricule())
            .marque(car.getMarque())
            .places(car.getPlaces())
            .chauffeurId(car.getChauffeur().getId())
            .build();
    }
}
