package org.poo.Gofind.repositories.driver;

import org.poo.Gofind.models.driver.Trajet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends CrudRepository<Trajet, Long> {
}
