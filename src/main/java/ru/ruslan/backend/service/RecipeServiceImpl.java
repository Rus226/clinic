package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.NotNullExpression;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.RecipeRepository;
import ru.ruslan.backend.entity.Recipe;

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
    public List<Recipe> findAll(String filterTextByDescription, String filterTextByDoctor, String filterTextByPatient) {
        if (
                StringUtils.isBlank(filterTextByDescription) &&
                StringUtils.isBlank(filterTextByDoctor) &&
                StringUtils.isBlank(filterTextByPatient)
        ){
            return (List<Recipe>) recipeRepository.findAll();
        } else {
            return recipeRepository.findByDescriptionContainingAndDoctorFirstNameContainingAndPatientFirstNameContaining(
                    filterTextByDescription, filterTextByDoctor, filterTextByPatient);
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
