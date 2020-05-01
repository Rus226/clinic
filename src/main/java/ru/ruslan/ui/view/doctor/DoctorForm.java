package ru.ruslan.ui.view.doctor;

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
import ru.ruslan.backend.entity.Doctor;

public class DoctorForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField secondName = new TextField("Second name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Doctor> binder = new BeanValidationBinder<>(Doctor.class);

    public void setDoctor(Doctor doctor) {
        binder.setBean(doctor);
    }

    public DoctorForm() {
        addClassName("doctor-form");
        binder.bindInstanceFields(this);

        add(firstName,
                secondName,
                patronymic,
                specialization,
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
    public static abstract class DoctorFormEvent extends ComponentEvent<DoctorForm> {
        private Doctor doctor;

        protected DoctorFormEvent(DoctorForm source, Doctor doctor) {
            super(source, false);
            this.doctor = doctor;
        }

        public Doctor getDoctor() {
            return doctor;
        }
    }

    public static class SaveEvent extends DoctorFormEvent {
        SaveEvent(DoctorForm source, Doctor doctor) {
            super(source, doctor);
        }
    }

    public static class DeleteEvent extends DoctorFormEvent {
        DeleteEvent(DoctorForm source, Doctor doctor) {
            super(source, doctor);
        }

    }

    public static class CloseEvent extends DoctorFormEvent {
        CloseEvent(DoctorForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}