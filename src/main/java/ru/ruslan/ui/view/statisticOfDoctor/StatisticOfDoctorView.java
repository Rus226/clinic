package ru.ruslan.ui.view.statisticOfDoctor;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.service.DoctorService;
import ru.ruslan.ui.MainLayout;

@Route(value = "statisticOfDoctor", layout = MainLayout.class)
@PageTitle("Statistics")
public class StatisticOfDoctorView extends VerticalLayout {

    private DoctorService doctorService;
    private Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);

    public StatisticOfDoctorView(DoctorService doctorService) {
        this.doctorService = doctorService;

        addClassName("doctorStatistic-view");
        setSizeFull();
        configureGrid();
        add(gridDoctor);
        gridDoctor.setItems(doctorService.findAll());
    }

    private void configureGrid() {
        gridDoctor.addClassName("doctorStatistic");
        gridDoctor.setSizeFull();
        gridDoctor.setColumns("firstName", "secondName", "patronymic", "specialization");
        gridDoctor.addColumn(doctor -> doctor.getRecipes().size()).setHeader("Count of Recipe");

    }
}
