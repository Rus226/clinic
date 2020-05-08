package ru.ruslan.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.backend.entity.Priority;
import ru.ruslan.backend.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByDescriptionContainingAndPatientFirstNameContainingAndPriority(
            String description,
            String patientFirstName,
            Priority priority
    );
    List<Recipe> findByDescriptionContainingAndPatientFirstNameContaining(
            String description,
            String patientFirstName
    );

}
