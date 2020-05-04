package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.PatientRepository;
import ru.ruslan.backend.entity.Patient;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Override
    public List<Patient> findAll() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public List<Patient> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()){
            return (List<Patient>) patientRepository.findAll();
        } else {
            return patientRepository.findByFirstNameContainingOrSecondNameContaining(filterText, filterText);
        }
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    @Override
    public void save(Patient patient) {
        patientRepository.save(patient);
    }
}
