package ru.ruslan.ui.view.doctor;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.service.DoctorService;
import ru.ruslan.ui.MainLayout;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@Route(value = "doctor", layout = MainLayout.class)
@PageTitle("Doctors")
public class DoctorView extends VerticalLayout {

    private DoctorService doctorService;

    private Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);

    private TextField filterText = new TextField();

    private DoctorDialog doctorDialog;

    public DoctorView(DoctorService doctorService){
        this.doctorService = doctorService;

        addClassName("doctor-view");
        setSizeFull();
        configureGrid();

        doctorDialog = new DoctorDialog();
        doctorDialog.addListener(DoctorDialog.SaveEvent.class, this::saveDoctor);
        doctorDialog.addListener(DoctorDialog.DeleteEvent.class, this::deleteDoctor);
        doctorDialog.addListener(DoctorDialog.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridDoctor, doctorDialog);
        content.addClassName("content");
        content.setSizeFull();

        add(configureToolBar(), content);
        updateList();
        closeEditor();
    }

    private void saveDoctor(DoctorDialog.SaveEvent event) {
        doctorService.save(event.getDoctor());
        updateList();
        closeEditor();
    }

    private void deleteDoctor(DoctorDialog.DeleteEvent event) {
        try {
        doctorService.delete(event.getDoctor());
        updateList();
        } catch (DataIntegrityViolationException e){
            Notification notification = new Notification(
                    "Failed to complete the request, perhaps you are trying to remove a Doctor who has recipes",
                    10000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            log.error(e.toString());
        } finally {
            closeEditor();
        }
    }

    private HorizontalLayout configureToolBar() {
        filterText.setPlaceholder("Filter by Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addDoctorButton = new Button("Add Doctor");
        addDoctorButton.addClickListener(click -> addDoctor());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addDoctorButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addDoctor() {
        editDoctor(new Doctor());
    }

    private void updateList() {
        gridDoctor.setItems(doctorService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        gridDoctor.addClassName("doctorGrid");
        gridDoctor.setSizeFull();
        gridDoctor.setColumns("firstName", "secondName", "patronymic", "specialization");
        gridDoctor.addColumn(new NativeButtonRenderer<>("Edit", this::editDoctor)).setHeader("Action");
        gridDoctor.asSingleSelect().addValueChangeListener(event -> editDoctor(event.getValue()));
    }

    private void editDoctor(Doctor doctor) {
        if (doctor == null){
            closeEditor();
        } else {
            doctorDialog.setDoctor(doctor);
            doctorDialog.open();
        }
    }
    private void closeEditor() {
        doctorDialog.setDoctor(null);
        doctorDialog.close();
    }
}
