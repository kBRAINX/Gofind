package org.poo.Gofind.services.driver;

import org.poo.Gofind.dto.driver.TrajetDTO;
import org.poo.Gofind.dto.driver.PassagerDTO;
import org.poo.Gofind.models.driver.Trajet;
import org.poo.Gofind.models.driver.Driver;
import org.poo.Gofind.models.driver.Passager;
import org.poo.Gofind.repositories.driver.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository trajetRepository;

    // Enregistrer un trajet
    public TrajetDTO create(TrajetDTO trajetDTO) {
        Date date = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime hStart = LocalDateTime.parse(trajetDTO.getH_Start(), formatter);
        java.sql.Timestamp sqlHStart = java.sql.Timestamp.valueOf(hStart);

        Trajet trajet = Trajet.builder()
            .start(trajetDTO.getStart())
            .end(trajetDTO.getEnd())
            .H_Start(sqlHStart)
            .createdAt(date)
            .chauffeur(Driver.builder().id(trajetDTO.getChauffeurId()).build())
            .build();
        trajet = trajetRepository.save(trajet);
        return mapToDTO(trajet);
    }

    // Sélectionner un trajet par son id
    public Optional<TrajetDTO> findOne(Long id) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(id);
        return trajetOpt.map(this::mapToDTO);
    }

    // Sélectionner tous les trajets
    public List<TrajetDTO> findAll() {
        return ((List<Trajet>) trajetRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Modifier un trajet par son id
    public Optional<TrajetDTO> update(Long id, TrajetDTO updatedTrajetDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime hStart = LocalDateTime.parse(updatedTrajetDTO.getH_Start(), formatter);
        java.sql.Timestamp sqlHStart = java.sql.Timestamp.valueOf(hStart);
        Date date = new Date();

        Optional<Trajet> trajetOpt = trajetRepository.findById(id);
        if (trajetOpt.isPresent()) {
            Trajet trajet = trajetOpt.get();
            trajet.setStart(updatedTrajetDTO.getStart());
            trajet.setEnd(updatedTrajetDTO.getEnd());
            trajet.setH_Start(sqlHStart);
            trajet.setCreatedAt(date);
            trajet.setChauffeur(Driver.builder().id(updatedTrajetDTO.getChauffeurId()).build());
            trajetRepository.save(trajet);
            return Optional.of(mapToDTO(trajet));
        }
        return Optional.empty();
    }

    // Supprimer un trajet par son id
    public void delete(Long id) {
        trajetRepository.deleteById(id);
    }

    // Obtenir la liste des passagers d'un trajet
    public List<PassagerDTO> findPassagersByTrajetId(Long trajetId) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);
        return trajetOpt.map(trajet -> trajet.getPassagers().stream()
                .map(this::mapPassagerToDTO)
                .collect(Collectors.toList()))
            .orElseThrow(() -> new RuntimeException("Trajet not found"));
    }

    // Mapper Trajet en TrajetDTO
    private TrajetDTO mapToDTO(Trajet trajet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<PassagerDTO> passagerDTOs = trajet.getPassagers().stream()
            .map(this::mapPassagerToDTO)
            .collect(Collectors.toList());

        return TrajetDTO.builder()
            .id(trajet.getId())
            .start(trajet.getStart())
            .end(trajet.getEnd())
            .H_Start(trajet.getH_Start().toLocalDateTime().format(formatter))
            .createdAt(trajet.getCreatedAt().toString())
            .chauffeurId(trajet.getChauffeur().getId())
            .passagers(passagerDTOs)
            .build();
    }

    // Mapper Passager en PassagerDTO
    private PassagerDTO mapPassagerToDTO(Passager passager) {
        return PassagerDTO.builder()
            .id(passager.getId())
            .nom(passager.getNom())
            .email(passager.getEmail())
            .city(passager.getCity())
            .trajetId(passager.getTrajet().getId())
            .build();
    }
}
