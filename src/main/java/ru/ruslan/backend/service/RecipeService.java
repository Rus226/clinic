package ru.ruslan.backend.service;

import ru.ruslan.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();
    Recipe findById(Long id);
    void delete(Recipe recipe);
    void save(Recipe recipe);
}
