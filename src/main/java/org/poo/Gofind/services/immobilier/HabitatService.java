package org.poo.Gofind.services.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.dto.immobilier.HabitatDTO;
import org.poo.Gofind.models.immobilier.*;
import org.poo.Gofind.repositories.immobilier.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitatService {

    @Autowired
    private HabitatRepository habitatRepository;

    // Enregistrer un habitat
    public HabitatDTO create(HabitatDTO habitatDTO) {
        Habitat habitat = Habitat.builder()
            .superficie(habitatDTO.getSuperficie())
            .pieces(habitatDTO.getPieces())
            .localisation(habitatDTO.getLocalisation())
            .operation(Operation.valueOf(habitatDTO.getOperation()))
            .status(Statut.valueOf(habitatDTO.getStatus()))
            .etage(habitatDTO.getEtage())
            .proprietaire(Proprietaire.builder().id(habitatDTO.getProprietaireId()).build())
            .montant(habitatDTO.getMontant())
            .build();
        habitat = habitatRepository.save(habitat);
        habitatDTO.setId(habitat.getId());
        return habitatDTO;
    }

    // Sélectionner un habitat par son id
    public Optional<HabitatDTO> findOne(Long id) {
        Optional<Habitat> habitatOpt = habitatRepository.findById(id);
        if (habitatOpt.isPresent()){
            Habitat habitat = habitatOpt.get();
            HabitatDTO habitatDTO = mapToDTO(habitat);
            return Optional.of(habitatDTO);
        }
        return Optional.empty();
    }

    // Sélectionner tous les habitats
    public List<HabitatDTO> getAll() {
        List<Habitat> habitats = (List<Habitat>) habitatRepository.findAll();
        return habitats.stream()
            .map(habitat -> HabitatDTO.builder()
                .id(habitat.getId())
                .superficie(habitat.getSuperficie())
                .pieces(habitat.getPieces())
                .localisation(habitat.getLocalisation())
                .operation(String.valueOf(Operation.valueOf(String.valueOf(habitat.getOperation()))))
                .status(String.valueOf(Statut.valueOf(String.valueOf(habitat.getStatus()))))
                .etage(habitat.getEtage())
                .proprietaireId(habitat.getProprietaire().getId())
                .montant(habitat.getMontant())
                .build())
            .collect(Collectors.toList());
    }

    // modifier un habitat par son id
    public Optional<HabitatDTO> update(Long id, HabitatDTO updatedHabitatDTO) {
        Optional<Habitat> HabitatOpt = habitatRepository.findById(id);
        if (HabitatOpt.isPresent()) {
            Habitat habitat = HabitatOpt.get();
            habitat.setSuperficie(updatedHabitatDTO.getSuperficie());
            habitat.setPieces(updatedHabitatDTO.getPieces());
            habitat.setLocalisation(updatedHabitatDTO.getLocalisation());
            habitat.setOperation(Operation.valueOf(updatedHabitatDTO.getOperation()));
            habitat.setStatus(Statut.valueOf(updatedHabitatDTO.getStatus()));
            habitat.setEtage(updatedHabitatDTO.getEtage());
            habitat.setProprietaire(Proprietaire.builder().id(updatedHabitatDTO.getProprietaireId()).build());
            habitat.setMontant(updatedHabitatDTO.getMontant());

            habitatRepository.save(habitat);
            return Optional.of(updatedHabitatDTO);
        } else {
            return Optional.empty();
        }
    }

    // Supprimer un habitat par son id
    public void delete(Long id) {
        habitatRepository.deleteById(id);
    }

    //converter un habitat en DTO
    private HabitatDTO mapToDTO(Habitat habitat) {
        return HabitatDTO.builder()
            .id(habitat.getId())
            .superficie(habitat.getSuperficie())
            .pieces(habitat.getPieces())
            .localisation(habitat.getLocalisation())
            .operation(habitat.getOperation().toString())
            .status(habitat.getStatus().toString())
            .etage(habitat.getEtage())
            .proprietaireId(habitat.getProprietaire().getId())
            .montant(habitat.getMontant())
            .commandes(habitat.getCommandes().stream()
                .map(this::mapCommandeToDTO)
                .collect(Collectors.toList()))
            .build();
    }

    // Mapper Commande en CommandeDTO
    private CommandeDTO mapCommandeToDTO(Commande commande) {
        return CommandeDTO.builder()
            .id(commande.getId())
            .locataireId(commande.getLocataire().getId())
            .habitatId(commande.getHabitat().getId())
            .createdAt(commande.getCreatedAt())
            .build();
    }

    // Trouver les habitats par l'ID du propriétaire
    public List<HabitatDTO> findByProprietaireId(Long proprietaireId) {
        List<Habitat> habitats = habitatRepository.findByProprietaireId(proprietaireId);
        return habitats.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
}
