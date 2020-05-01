package ru.ruslan.backend.service;


import ru.ruslan.backend.entity.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();
    List<Doctor> findAll(String filterText);
    Doctor findById(Long id);
    void delete(Doctor doctor);
    void save(Doctor doctor);
}
