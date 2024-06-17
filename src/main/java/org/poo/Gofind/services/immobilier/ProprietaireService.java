package org.poo.Gofind.services.immobilier;

import org.poo.Gofind.dto.immobilier.HabitatDTO;
import org.poo.Gofind.dto.immobilier.ProprietaireDTO;
import org.poo.Gofind.models.immobilier.Habitat;
import org.poo.Gofind.models.immobilier.Proprietaire;
import org.poo.Gofind.repositories.immobilier.ProprietaireRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProprietaireService {

    @Autowired
    private ProprietaireRepository proprietaireRepository;

    @Autowired
    private HabitatService habitatService;

    public ProprietaireDTO create(ProprietaireDTO proprietaireDTO) {
        Proprietaire proprietaire = Proprietaire.builder()
            .nom(proprietaireDTO.getNom())
            .email(proprietaireDTO.getEmail())
            .city(proprietaireDTO.getCity())
            .number(proprietaireDTO.getNumber())
            .address(proprietaireDTO.getAddress())
            .build();
        proprietaire = proprietaireRepository.save(proprietaire);
        proprietaireDTO.setId(proprietaire.getId());
        return proprietaireDTO;
    }

    public Optional<ProprietaireDTO> update(Long id, ProprietaireDTO updatedProprietaireDTO) {
        Optional<Proprietaire> proprietaireOpt = proprietaireRepository.findById(id);
        if (proprietaireOpt.isPresent()) {
            Proprietaire proprietaire = proprietaireOpt.get();
            proprietaire.setNom(updatedProprietaireDTO.getNom());
            proprietaire.setEmail(updatedProprietaireDTO.getEmail());
            proprietaire.setCity(updatedProprietaireDTO.getCity());
            proprietaire.setNumber(updatedProprietaireDTO.getNumber());
            proprietaire.setAddress(updatedProprietaireDTO.getAddress());
            proprietaireRepository.save(proprietaire);
            return Optional.of(updatedProprietaireDTO);
        } else {
            return Optional.empty();
        }
    }

    public Optional<ProprietaireDTO> findById(Long id) {
        return proprietaireRepository.findById(id)
            .map(this::mapToDTO);
    }

    public Iterable<ProprietaireDTO> findAll() {
        return proprietaireRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public void delete(Long id) {
        proprietaireRepository.deleteById(id);
    }

    //convervion de proprietaire en dto
    private ProprietaireDTO mapToDTO(Proprietaire proprietaire) {
        List<HabitatDTO> habitatDTOs = habitatService.findByProprietaireId(proprietaire.getId());
        return ProprietaireDTO.builder()
            .id(proprietaire.getId())
            .nom(proprietaire.getNom())
            .email(proprietaire.getEmail())
            .city(proprietaire.getCity())
            .number(proprietaire.getNumber())
            .address(proprietaire.getAddress())
            .habitats(habitatDTOs)
            .build();
    }
}
