package org.poo.Gofind.repositories.driver;

import org.poo.Gofind.models.driver.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {
}
