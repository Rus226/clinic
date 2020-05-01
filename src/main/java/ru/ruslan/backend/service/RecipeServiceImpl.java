package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.RecipeDAO;
import ru.ruslan.backend.entity.Recipe;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeDAO recipeDAO;

    @Override
    public List<Recipe> findAll() {
        return (List<Recipe>) recipeDAO.findAll();
    }

    @Override
    public Recipe findById(Long id) {
        return recipeDAO.findById(id).get();
    }

    @Override
    public void delete(Recipe recipe) {
        recipeDAO.delete(recipe);
    }

    @Override
    public void save(Recipe recipe) {
        recipeDAO.save(recipe);
    }
}
