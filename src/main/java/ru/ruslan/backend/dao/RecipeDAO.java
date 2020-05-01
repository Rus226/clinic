package ru.ruslan.backend.dao;

import org.springframework.data.repository.CrudRepository;
import ru.ruslan.backend.entity.Recipe;

public interface RecipeDAO extends CrudRepository<Recipe, Long> {
}
