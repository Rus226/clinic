package ru.ruslan.backend.service;

import ru.ruslan.backend.entity.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    List<Patient> findAll(String filterText);
    Patient findById(Long id);
    void delete(Patient patient);
    void save(Patient patient);
}
