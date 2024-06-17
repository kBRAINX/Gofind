package org.poo.Gofind.repositories.driver;

import org.poo.Gofind.models.driver.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, String> {
}
