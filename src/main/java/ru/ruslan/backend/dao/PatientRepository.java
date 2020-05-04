package ru.ruslan.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.backend.entity.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByFirstNameContainingOrSecondNameContaining(String firstName, String secondName);
}
