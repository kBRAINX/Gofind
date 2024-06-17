package org.poo.Gofind.services.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.dto.immobilier.LocataireDTO;
import org.poo.Gofind.models.immobilier.Commande;
import org.poo.Gofind.models.immobilier.Locataire;
import org.poo.Gofind.repositories.immobilier.LocataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocataireService {

    @Autowired
    private LocataireRepository locataireRepository;

    // Enregistrer un locataire
    public LocataireDTO create(LocataireDTO locataireDTO) {
        Locataire locataire = Locataire.builder()
            .nom(locataireDTO.getNom())
            .email(locataireDTO.getEmail())
            .city(locataireDTO.getCity())
            .number(locataireDTO.getNumber())
            .build();
        locataire = locataireRepository.save(locataire);
        locataireDTO.setId(locataire.getId());
        return locataireDTO;
    }

    // Sélectionner un locataire par son id
    public Optional<LocataireDTO> findOne(Long id) {
        Optional<Locataire> locataireOpt = locataireRepository.findById(id);
        if (locataireOpt.isPresent()) {
            Locataire locataire = locataireOpt.get();
            LocataireDTO locataireDTO = mapToDTO(locataire);
            return Optional.of(locataireDTO);
        }
        return Optional.empty();
    }

    // Sélectionner tous les locataires
    public Iterable<LocataireDTO> findAll() {
        return ((List<Locataire>) locataireRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Modifier un locataire par son id
    public Optional<LocataireDTO> update(Long id, LocataireDTO updatedLocataireDTO) {
        Optional<Locataire> locataireOpt = locataireRepository.findById(id);
        if (locataireOpt.isPresent()) {
            Locataire locataire = locataireOpt.get();
            locataire.setNom(updatedLocataireDTO.getNom());
            locataire.setEmail(updatedLocataireDTO.getEmail());
            locataire.setCity(updatedLocataireDTO.getCity());
            locataire.setNumber(updatedLocataireDTO.getNumber());
            locataireRepository.save(locataire);
            return Optional.of(mapToDTO(locataire));
        }
        return Optional.empty();
    }

    // Supprimer un locataire par son id
    public void delete(Long id) {
        locataireRepository.deleteById(id);
    }

    // Mapper Locataire en LocataireDTO
    private LocataireDTO mapToDTO(Locataire locataire) {
        return LocataireDTO.builder()
            .id(locataire.getId())
            .nom(locataire.getNom())
            .email(locataire.getEmail())
            .city(locataire.getCity())
            .number(locataire.getNumber())
            .commandes(locataire.getCommandes().stream()
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

}
