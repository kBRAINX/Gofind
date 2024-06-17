package org.poo.Gofind.repositories.driver;

import org.poo.Gofind.models.driver.Passager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagerRepository extends CrudRepository<Passager, Long> {
}
