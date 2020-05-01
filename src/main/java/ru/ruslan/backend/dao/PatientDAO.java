package ru.ruslan.backend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.entity.Patient;

import java.util.List;

public interface PatientDAO extends CrudRepository<Patient, Long> {
    @Query("select c from Patient c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.secondName) like lower(concat('%', :searchTerm, '%'))")
    List<Patient> search(@Param("searchTerm") String searchTerm);
}
