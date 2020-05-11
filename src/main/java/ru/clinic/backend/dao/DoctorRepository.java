package ru.clinic.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clinic.backend.entity.Doctor;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    List<Doctor> findByFirstNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(String firstName, String secondName);
}
