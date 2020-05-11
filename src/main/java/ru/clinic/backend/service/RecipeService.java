package ru.clinic.backend.service;

import ru.clinic.backend.entity.Priority;
import ru.clinic.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();
    List<Recipe> findAll(String filterTextByDescription, String filterTextByPatient, Priority filterPriority);
    void delete(Recipe recipe);
    void save(Recipe recipe);
}
