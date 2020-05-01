package ru.ruslan.backend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruslan.backend.dao.DoctorDAO;
import ru.ruslan.backend.entity.Doctor;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorDAO doctorDAO;

    @Override
    public List<Doctor> findAll() {
        log.info("we Found");
        return (List<Doctor>) doctorDAO.findAll();
    }

    @Override
    public List<Doctor> findAll(String filterText) {
        log.info("we FoundFilter");
        if (filterText == null || filterText.isEmpty()){
            return (List<Doctor>) doctorDAO.findAll();
        } else {
            return doctorDAO.search(filterText);
        }
    }

    @Override
    public Doctor findById(Long id) {
        return doctorDAO.findById(id).get();
    }

    @Override
    public void delete(Doctor doctor) {
        doctorDAO.delete(doctor);
    }

    @Override
    public void save(Doctor doctor) {
        doctorDAO.save(doctor);
    }
}
