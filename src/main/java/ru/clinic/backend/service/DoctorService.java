package ru.clinic.backend.service;

import ru.clinic.backend.entity.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();
    List<Doctor> findAll(String filterText);
    void delete(Doctor doctor);
    void save(Doctor doctor);
}
