package ru.ruslan.backend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ruslan.backend.entity.Doctor;

import java.util.List;

public interface DoctorDAO extends CrudRepository<Doctor, Long> {
    @Query("select c from Doctor c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.secondName) like lower(concat('%', :searchTerm, '%'))")
    List<Doctor> search(@Param("searchTerm") String searchTerm);
}
