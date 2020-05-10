package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.DoctorRepository;
import ru.ruslan.backend.entity.Doctor;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> findAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public List<Doctor> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()){
            return (List<Doctor>) doctorRepository.findAll();
        } else {
            return doctorRepository.findByFirstNameContainingOrSecondNameContaining(filterText, filterText);
        }
    }

    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }
}
