package ru.clinic.backend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clinic.backend.dao.RecipeRepository;
import ru.clinic.backend.entity.Priority;
import ru.clinic.backend.entity.Recipe;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findAll(
            String filterTextByDescription,
            String filterTextByPatient,
            Priority filterPriority
    ) {
        if (
                StringUtils.isBlank(filterTextByDescription) &&
                StringUtils.isBlank(filterTextByPatient) &&
                filterPriority == null){
            return (List<Recipe>) recipeRepository.findAll();
        } else if (filterPriority == null){
            return recipeRepository.findByDescriptionContainingIgnoreCaseAndPatientFirstNameContainingIgnoreCase(
                    filterTextByDescription, filterTextByPatient);
        } else {
            return recipeRepository.findByDescriptionContainingIgnoreCaseAndPatientFirstNameContainingIgnoreCaseAndPriority(
                    filterTextByDescription, filterTextByPatient, filterPriority);
        }
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    @Override
    public void save(Recipe recipe)  {
            recipeRepository.save(recipe);
    }
}
