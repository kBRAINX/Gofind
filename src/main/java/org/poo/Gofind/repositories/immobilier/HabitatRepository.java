package org.poo.Gofind.repositories.immobilier;

import org.poo.Gofind.models.immobilier.Habitat;
import org.poo.Gofind.models.immobilier.HabitatImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitatRepository extends CrudRepository<Habitat, Long> {
    List<Habitat> findByProprietaireId(Long proprietaireId);
}
