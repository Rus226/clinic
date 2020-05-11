package ru.clinic.backend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clinic.backend.dao.DoctorRepository;
import ru.clinic.backend.entity.Doctor;

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
        if (StringUtils.isBlank(filterText)){
            return (List<Doctor>) doctorRepository.findAll();
        } else {
            return doctorRepository.findByFirstNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(
                    filterText, filterText);
        }
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
