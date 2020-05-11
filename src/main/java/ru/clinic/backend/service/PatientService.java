package ru.clinic.backend.service;

import ru.clinic.backend.entity.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    List<Patient> findAll(String filterText);
    void delete(Patient patient);
    void save(Patient patient);
}
