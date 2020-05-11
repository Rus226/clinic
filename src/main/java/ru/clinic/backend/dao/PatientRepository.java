package ru.clinic.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clinic.backend.entity.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByFirstNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(String firstName, String secondName);
}
