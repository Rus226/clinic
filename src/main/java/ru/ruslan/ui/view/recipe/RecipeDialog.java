package ru.ruslan.ui.view.recipe;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.entity.Patient;
import ru.ruslan.backend.entity.Priority;
import ru.ruslan.backend.entity.Recipe;

import java.time.LocalDate;
import java.util.List;

public class RecipeDialog extends Dialog{

    private TextArea description = new TextArea("Description");

    private ComboBox<Patient> patient = new ComboBox<>("Patient");
    private ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    private ComboBox<Priority> priority = new ComboBox<>("Priority");

    private DatePicker dateTermination = new DatePicker("Termination Date");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    private Binder<Recipe> binder = new BeanValidationBinder<>(Recipe.class);

    public void setRecipe(Recipe recipe) {
        binder.setBean(recipe);
    }

    public RecipeDialog(List<Doctor> doctors, List<Patient> patients) {
        binder.bindInstanceFields(this);

        patient.setItems(patients);
        patient.setItemLabelGenerator(Patient::getFirstAndSecondName);

        doctor.setItems(doctors);
        doctor.setItemLabelGenerator(Doctor::getFirstAndSecondName);

        priority.setItems(Priority.values());

        dateTermination.setInitialPosition(LocalDate.now());

        description.setWidth("350px");

        add(description,
                patient,
                doctor,
                priority,
                dateTermination,
                createButtonsLayout());

        setWidth("350px");
        setHeight("470px");
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new RecipeDialog.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new RecipeDialog.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new RecipeDialog.SaveEvent(this, binder.getBean()));
        }
        close();
    }

    // Events
    public static abstract class RecipeFormEvent extends ComponentEvent<RecipeDialog> {
        private Recipe recipe;

        protected RecipeFormEvent(RecipeDialog source, Recipe recipe) {
            super(source, false);
            this.recipe = recipe;
        }

        public Recipe getRecipe() {
            return recipe;
        }
    }

    public static class SaveEvent extends RecipeDialog.RecipeFormEvent {
        SaveEvent(RecipeDialog source, Recipe recipe) {
            super(source, recipe);
        }
    }

    public static class DeleteEvent extends RecipeDialog.RecipeFormEvent {
        DeleteEvent(RecipeDialog source, Recipe recipe) {
            super(source, recipe);
        }

    }

    public static class CloseEvent extends RecipeDialog.RecipeFormEvent {
        CloseEvent(RecipeDialog source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
