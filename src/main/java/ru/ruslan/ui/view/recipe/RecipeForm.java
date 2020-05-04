package ru.ruslan.ui.view.recipe;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.entity.Patient;
import ru.ruslan.backend.entity.Priority;
import ru.ruslan.backend.entity.Recipe;

import java.time.LocalDate;
import java.util.List;

public class RecipeForm extends FormLayout {

    private TextField description = new TextField("Description");

    ComboBox<Patient> patient = new ComboBox<>("Patient");
    ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    ComboBox<Priority> priority = new ComboBox<>("Priority");

//    DatePicker dateCreation = new DatePicker("Date of Creation");
    DatePicker dateTermination = new DatePicker("Termination Date");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Recipe> binder = new BeanValidationBinder<>(Recipe.class);

    public void setRecipe(Recipe recipe) {
        binder.setBean(recipe);
    }

    public RecipeForm(List<Doctor> doctors, List<Patient> patients) {
        addClassName("recipe-form");
        binder.bindInstanceFields(this);
        patient.setItems(patients);
        patient.setItemLabelGenerator(Patient::getFirstAndSecondName);
        doctor.setItems(doctors);
        doctor.setItemLabelGenerator(Doctor::getFirstAndSecondName);
        priority.setItems(Priority.values());
        dateTermination.setInitialPosition(LocalDate.now());

        add(description,
                patient,
                doctor,
                priority,
//                dateCreation,
                dateTermination,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new RecipeForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new RecipeForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new RecipeForm.SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class RecipeFormEvent extends ComponentEvent<RecipeForm> {
        private Recipe recipe;

        protected RecipeFormEvent(RecipeForm source, Recipe recipe) {
            super(source, false);
            this.recipe = recipe;
        }

        public Recipe getRecipe() {
            return recipe;
        }
    }

    public static class SaveEvent extends RecipeForm.RecipeFormEvent {
        SaveEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }
    }

    public static class DeleteEvent extends RecipeForm.RecipeFormEvent {
        DeleteEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }

    }

    public static class CloseEvent extends RecipeForm.RecipeFormEvent {
        CloseEvent(RecipeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
