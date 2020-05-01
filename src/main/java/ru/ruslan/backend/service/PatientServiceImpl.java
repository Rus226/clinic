package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.PatientDAO;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.entity.Patient;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientDAO patientDAO;

    @Override
    public List<Patient> findAll() {
        return (List<Patient>) patientDAO.findAll();
    }

    @Override
    public List<Patient> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()){
            return (List<Patient>) patientDAO.findAll();
        } else {
            return patientDAO.search(filterText);
        }
    }

    @Override
    public Patient findById(Long id) {
        return patientDAO.findById(id).get();
    }

    @Override
    public void delete(Patient patient) {
        patientDAO.delete(patient);
    }

    @Override
    public void save(Patient patient) {
        patientDAO.save(patient);
    }
}
