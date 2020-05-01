package ru.ruslan.ui.view.patient;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.ruslan.backend.entity.Patient;
import ru.ruslan.backend.service.DoctorServiceImpl;
import ru.ruslan.backend.service.PatientServiceImpl;
import ru.ruslan.backend.service.RecipeServiceImpl;
import ru.ruslan.ui.MainLayout;


@Route(value = "patient", layout = MainLayout.class)
@PageTitle("Patients")
public class PatientView extends VerticalLayout {

    private DoctorServiceImpl doctorService;
    private PatientServiceImpl patientService;
    private RecipeServiceImpl recipeService;


    private Grid<Patient> gridPatient = new Grid<>(Patient.class);

    private TextField filterText = new TextField();
    private PatientForm patientForm;


    public PatientView(DoctorServiceImpl doctorService,
                      PatientServiceImpl patientService,
                      RecipeServiceImpl recipeService){

        this.doctorService = doctorService;
        this.patientService = patientService;
        this.recipeService = recipeService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        patientForm = new PatientForm();
        patientForm.addListener(PatientForm.SaveEvent.class, this::savePatient);
        patientForm.addListener(PatientForm.DeleteEvent.class, this::deletePatient);
        patientForm.addListener(PatientForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridPatient, patientForm);
        content.addClassName("content");
        content.setSizeFull();


        add(configureToolBar(), content);
        updateList();
        closeEditor();
    }

    private void savePatient(PatientForm.SaveEvent event) {
        patientService.save(event.getPatient());
        updateList();
        closeEditor();
    }

    private void deletePatient(PatientForm.DeleteEvent event) {
        patientService.delete(event.getPatient());
        updateList();
        closeEditor();
    }


    private HorizontalLayout configureToolBar() {
        filterText.setPlaceholder("Filter by Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPatientButton = new Button("Add Patient");
        addPatientButton.addClickListener(click -> addPatient());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPatientButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addPatient() {
        gridPatient.asSingleSelect().clear();
        editPatient(new Patient());
    }

    private void updateList() {
        gridPatient.setItems(patientService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        gridPatient.addClassName("Patient-grid");
        gridPatient.setSizeFull();
        gridPatient.setColumns("firstName", "secondName", "patronymic", "phoneNumber");

        gridPatient.asSingleSelect().addValueChangeListener(event -> editPatient(event.getValue()));

    }

    private void editPatient(Patient patient) {
        if (patient == null){
            closeEditor();
        } else {
            patientForm.setPatient(patient);
            patientForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        patientForm.setPatient(null);
        patientForm.setVisible(false);
        removeClassName("editing");
    }
}
