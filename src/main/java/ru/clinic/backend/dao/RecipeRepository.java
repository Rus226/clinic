package ru.clinic.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clinic.backend.entity.Priority;
import ru.clinic.backend.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByDescriptionContainingIgnoreCaseAndPatientFirstNameContainingIgnoreCaseAndPriority(
            String description,
            String patientFirstName,
            Priority priority
    );
    List<Recipe> findByDescriptionContainingIgnoreCaseAndPatientFirstNameContainingIgnoreCase(
            String description,
            String patientFirstName
    );
}
