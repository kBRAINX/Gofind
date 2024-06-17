package org.poo.Gofind.repositories.appareils;

import org.poo.Gofind.models.appareils.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
