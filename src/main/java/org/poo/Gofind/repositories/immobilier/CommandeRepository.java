package org.poo.Gofind.repositories.immobilier;

import org.poo.Gofind.models.immobilier.Commande;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends CrudRepository<Commande, Long> {
    Iterable<Commande> findByLocataireId(long locataireId);
    Iterable<Commande> findByHabitatId(long HabitatId);
}
