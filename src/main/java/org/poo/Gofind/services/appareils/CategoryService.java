package org.poo.Gofind.services.appareils;

import org.poo.Gofind.dto.appareils.CategoryDto;
import org.poo.Gofind.models.appareils.Category;
import org.poo.Gofind.repositories.appareils.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findOne(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category create(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getNom());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> update(Long id, CategoryDto updatedCategory) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(updatedCategory.getNom());
            categoryRepository.save(category);
        }
        return categoryOpt;
    }
    public CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setNom(category.getName());
        return categoryDto;
    }
}
