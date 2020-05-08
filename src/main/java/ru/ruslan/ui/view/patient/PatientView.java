package ru.ruslan.ui.view.patient;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.ruslan.backend.entity.Patient;
import ru.ruslan.backend.service.PatientService;
import ru.ruslan.ui.MainLayout;


@Route(value = "patient", layout = MainLayout.class)
@PageTitle("Patients")
public class PatientView extends VerticalLayout {

    private PatientService patientService;


    private Grid<Patient> gridPatient = new Grid<>(Patient.class);

    private TextField filterText = new TextField();

    private PatientDialog patientDialog;


    public PatientView(PatientService patientService){
        this.patientService = patientService;

        addClassName("patient-view");
        setSizeFull();
        configureGrid();

        patientDialog = new PatientDialog();
        patientDialog.addListener(PatientDialog.SaveEvent.class, this::savePatient);
        patientDialog.addListener(PatientDialog.DeleteEvent.class, this::deletePatient);
        patientDialog.addListener(PatientDialog.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridPatient, patientDialog);
        content.addClassName("content");
        content.setSizeFull();

        add(configureToolBar(), content);
        updateList();
        closeEditor();
    }

    private void savePatient(PatientDialog.SaveEvent event) {
        patientService.save(event.getPatient());
        updateList();
        closeEditor();
    }

    private void deletePatient(PatientDialog.DeleteEvent event) {
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
        editPatient(new Patient());
    }

    private void updateList() {
        gridPatient.setItems(patientService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        gridPatient.addClassName("patientGrid");
        gridPatient.setSizeFull();
        gridPatient.setColumns("firstName", "secondName", "patronymic", "phoneNumber");

        gridPatient.addColumn(new NativeButtonRenderer<>("Edit", this::editPatient)).setHeader("Action");

        gridPatient.asSingleSelect().addValueChangeListener(event -> editPatient(event.getValue()));

    }

    private void editPatient(Patient patient) {
        if (patient == null){
            closeEditor();
        } else {
            patientDialog.setPatient(patient);
            patientDialog.open();
        }
    }
    private void closeEditor() {
        patientDialog.setPatient(null);
        patientDialog.close();
    }
}
