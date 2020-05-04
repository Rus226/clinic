package ru.ruslan.ui.view.doctor;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.service.DoctorService;
import ru.ruslan.backend.service.DoctorServiceImpl;
import ru.ruslan.backend.service.PatientServiceImpl;
import ru.ruslan.backend.service.RecipeServiceImpl;
import ru.ruslan.ui.MainLayout;


@Route(value = "doctor", layout = MainLayout.class)
@PageTitle("Doctors")
public class DoctorView extends VerticalLayout {

    private DoctorService doctorService;

    private Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);

    private TextField filterText = new TextField();
    private DoctorForm doctorForm;

    public DoctorView(DoctorService doctorService){
        this.doctorService = doctorService;

        addClassName("doctor-view");
        setSizeFull();
        configureGrid();

        doctorForm = new DoctorForm();
        doctorForm.addListener(DoctorForm.SaveEvent.class, this::saveDoctor);
        doctorForm.addListener(DoctorForm.DeleteEvent.class, this::deleteDoctor);
        doctorForm.addListener(DoctorForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridDoctor, doctorForm);
        content.addClassName("content");
        content.setSizeFull();

        add(configureToolBar(), content);
        updateList();
        closeEditor();
    }

    private void saveDoctor(DoctorForm.SaveEvent event) {
        doctorService.save(event.getDoctor());
        updateList();
        closeEditor();
    }

    private void deleteDoctor(DoctorForm.DeleteEvent event) {
        doctorService.delete(event.getDoctor());
        updateList();
        closeEditor();
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
        gridDoctor.asSingleSelect().clear();
        editDoctor(new Doctor());
    }

    private void updateList() {
        gridDoctor.setItems(doctorService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        gridDoctor.addClassName("doctorGrid");
        gridDoctor.setSizeFull();
        gridDoctor.setColumns("firstName", "secondName", "patronymic", "specialization");

        gridDoctor.asSingleSelect().addValueChangeListener(event -> editDoctor(event.getValue()));
    }

    private void editDoctor(Doctor doctor) {
        if (doctor == null){
            closeEditor();
        } else {
            doctorForm.setDoctor(doctor);
            doctorForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        doctorForm.setDoctor(null);
        doctorForm.setVisible(false);
        removeClassName("editing");
    }
}
