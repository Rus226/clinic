package ru.ruslan.ui.view.patient;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.ruslan.backend.entity.Patient;

public class PatientForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField secondName = new TextField("Second name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber = new TextField("Phone Number");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Patient> binder = new BeanValidationBinder<>(Patient.class);

    public void setPatient(Patient Patient) {
        binder.setBean(Patient);
    }

    public PatientForm() {
        addClassName("patient-form");
        binder.bindInstanceFields(this);

        add(firstName,
                secondName,
                patronymic,
                phoneNumber,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }


    // Events
    public static abstract class PatientFormEvent extends ComponentEvent<PatientForm> {
        private Patient patient;

        protected PatientFormEvent(PatientForm source, Patient patient) {
            super(source, false);
            this.patient = patient;
        }

        public Patient getPatient() {
            return patient;
        }
    }

    public static class SaveEvent extends PatientFormEvent {
        SaveEvent(PatientForm source, Patient patient) {
            super(source, patient);
        }
    }

    public static class DeleteEvent extends PatientFormEvent {
        DeleteEvent(PatientForm source, Patient patient) {
            super(source, patient);
        }

    }

    public static class CloseEvent extends PatientFormEvent {
        CloseEvent(PatientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}