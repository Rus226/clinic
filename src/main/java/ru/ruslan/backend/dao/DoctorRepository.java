package ru.ruslan.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.backend.entity.Doctor;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    List<Doctor> findByFirstNameContainingOrSecondNameContaining(String firstName, String secondName);
}
