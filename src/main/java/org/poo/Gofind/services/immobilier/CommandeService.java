package org.poo.Gofind.services.immobilier;

import org.poo.Gofind.dto.immobilier.CommandeDTO;
import org.poo.Gofind.models.immobilier.Commande;
import org.poo.Gofind.models.immobilier.Locataire;
import org.poo.Gofind.models.immobilier.Habitat;
import org.poo.Gofind.repositories.immobilier.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LocataireService locataireService;

    @Autowired
    private HabitatService habitatService;

    // Sélectionner une commande par son id
    public Optional<CommandeDTO> findOne(Long id) {
        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        return commandeOpt.map(this::mapToDTO);
    }

    // Sélectionner toutes les commandes
    public Iterable<CommandeDTO> findAll() {
        return ((List<Commande>) commandeRepository.findAll()).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Enregistrer une commande
    public CommandeDTO create(CommandeDTO commandeDTO) {
        Date date = Date.from(Instant.now());
        Commande commande = Commande.builder()
            .locataire(Locataire.builder().id(commandeDTO.getLocataireId()).build())
            .habitat(Habitat.builder().id(commandeDTO.getHabitatId()).build())
            .createdAt(date)
            .build();
        commande = commandeRepository.save(commande);
        commandeDTO.setId(commande.getId());
        return commandeDTO;
    }

    // Modifier une commande par son id
    public Optional<CommandeDTO> update(Long id, CommandeDTO updatedCommandeDTO) {
        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        Date date = (Date) Date.from(Instant.now());
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            commande.setLocataire(Locataire.builder().id(updatedCommandeDTO.getLocataireId()).build());
            commande.setHabitat(Habitat.builder().id(updatedCommandeDTO.getHabitatId()).build());
            commande.setCreatedAt(date);
            commandeRepository.save(commande);
            return Optional.of(mapToDTO(commande));
        }
        return Optional.empty();
    }

    // Supprimer une commande par son id
    public void delete(Long id) {
        commandeRepository.deleteById(id);
    }

    // Sélectionner toutes les commandes d'un locataire
    public Iterable<CommandeDTO> findByLocataireId(long locataireId) {
        return ((List<Commande>) commandeRepository.findByLocataireId(locataireId)).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    //selectionner toutes les commandes d'un habitat
    public Iterable<CommandeDTO> findByHabitatId(long habitatId){
        return ((List<Commande>) commandeRepository.findByHabitatId(habitatId)).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Mapper Commande en CommandeDTO
    private CommandeDTO mapToDTO(Commande commande) {
        return CommandeDTO.builder()
            .id(commande.getId())
            .locataireId(commande.getLocataire().getId())
            .habitatId(commande.getHabitat().getId())
            .createdAt(commande.getCreatedAt())
            .build();
    }
}
