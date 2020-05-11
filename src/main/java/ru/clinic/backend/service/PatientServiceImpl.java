package ru.clinic.backend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clinic.backend.dao.PatientRepository;
import ru.clinic.backend.entity.Patient;

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
        if (StringUtils.isBlank(filterText)){
            return (List<Patient>) patientRepository.findAll();
        } else {
            return patientRepository.findByFirstNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(
                    filterText, filterText);
        }
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
