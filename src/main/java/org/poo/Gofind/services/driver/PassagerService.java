package org.poo.Gofind.services.driver;

import org.poo.Gofind.dto.driver.PassagerDTO;
import org.poo.Gofind.models.driver.Passager;
import org.poo.Gofind.models.driver.Trajet;
import org.poo.Gofind.repositories.driver.PassagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassagerService {

    @Autowired
    private PassagerRepository passagerRepository;

    // Enregistrer un passager
    public PassagerDTO create(PassagerDTO passagerDTO) {
        Passager passager = Passager.builder()
            .nom(passagerDTO.getNom())
            .email(passagerDTO.getEmail())
            .city(passagerDTO.getCity())
            .trajet(Trajet.builder().id(passagerDTO.getTrajetId()).build())
            .build();
        passager = passagerRepository.save(passager);
        return mapToDTO(passager);
    }

    // Sélectionner un passager par son id
    public Optional<PassagerDTO> findOne(Long id) {
        Optional<Passager> passagerOpt = passagerRepository.findById(id);
        return passagerOpt.map(this::mapToDTO);
    }

    // Sélectionner tous les passagers
    public List<PassagerDTO> findAll() {
        return ((List<Passager>) passagerRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Modifier un passager par son id
    public Optional<PassagerDTO> update(Long id, PassagerDTO updatedPassagerDTO) {
        Optional<Passager> passagerOpt = passagerRepository.findById(id);
        if (passagerOpt.isPresent()) {
            Passager passager = passagerOpt.get();
            passager.setNom(updatedPassagerDTO.getNom());
            passager.setEmail(updatedPassagerDTO.getEmail());
            passager.setCity(updatedPassagerDTO.getCity());
            passager.setTrajet(Trajet.builder().id(updatedPassagerDTO.getTrajetId()).build());
            passagerRepository.save(passager);
            return Optional.of(mapToDTO(passager));
        }
        return Optional.empty();
    }

    // Supprimer un passager par son id
    public void delete(Long id) {
        passagerRepository.deleteById(id);
    }

    // Mapper Passager en PassagerDTO
    private PassagerDTO mapToDTO(Passager passager) {
        return PassagerDTO.builder()
            .id(passager.getId())
            .nom(passager.getNom())
            .email(passager.getEmail())
            .city(passager.getCity())
            .trajetId(passager.getTrajet().getId())
            .build();
    }
}
