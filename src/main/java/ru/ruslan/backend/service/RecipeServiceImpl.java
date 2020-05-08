package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.RecipeRepository;
import ru.ruslan.backend.entity.Priority;
import ru.ruslan.backend.entity.Recipe;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        log.info("emptyConstructor");
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
            return recipeRepository.findByDescriptionContainingAndPatientFirstNameContaining(
                    filterTextByDescription, filterTextByPatient);
        } else {
            return recipeRepository.findByDescriptionContainingAndPatientFirstNameContainingAndPriority(
                    filterTextByDescription, filterTextByPatient, filterPriority);
        }
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).get();
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
