package ru.ruslan.backend.service;

import ru.ruslan.backend.entity.Priority;
import ru.ruslan.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();
    List<Recipe> findAll(String x, String c, Priority e);
    Recipe findById(Long id);
    void delete(Recipe recipe);
    void save(Recipe recipe);
}
