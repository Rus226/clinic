package ru.ruslan.ui.view.recipe;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.server.Page;
import org.apache.commons.lang3.StringUtils;
import ru.ruslan.backend.entity.Doctor;
import ru.ruslan.backend.entity.Patient;
import ru.ruslan.backend.entity.Recipe;
import ru.ruslan.backend.service.DoctorService;
import ru.ruslan.backend.service.PatientService;
import ru.ruslan.backend.service.RecipeService;
import ru.ruslan.ui.MainLayout;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Recipes")
public class RecipeView extends VerticalLayout {

    private DoctorService doctorService;
    private RecipeService recipeService;
    private PatientService patientService;

    private Grid<Recipe> gridRecipe = new Grid<>(Recipe.class);

    private TextField filterTextByDescription = new TextField();
    private TextField filterTextByDoctor = new TextField();
    private TextField filterTextByPatient = new TextField();
    private RecipeForm recipeForm;

    public RecipeView(DoctorService doctorService, RecipeService recipeService, PatientService patientService) {
        this.doctorService = doctorService;
        this.recipeService = recipeService;
        this.patientService = patientService;

            addClassName("recipe-view");
            setSizeFull();
            configureGrid();

            recipeForm = new RecipeForm(doctorService.findAll(), patientService.findAll());
            recipeForm.addListener(RecipeForm.SaveEvent.class, this::saveRecipe);
            recipeForm.addListener(RecipeForm.DeleteEvent.class, this::deleteRecipe);
            recipeForm.addListener(RecipeForm.CloseEvent.class, e -> closeEditor());

            Div content = new Div(gridRecipe, recipeForm);
            content.addClassName("content");
            content.setSizeFull();

            add(configureToolBar(), content);
            updateList();
            closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        filterTextByDescription.setPlaceholder("Filter by Description...");
        filterTextByDescription.setClearButtonVisible(true);
        filterTextByDescription.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextByDescription.addValueChangeListener(e -> updateList());

        filterTextByPatient.setPlaceholder("Filter by Patient...");
        filterTextByPatient.setClearButtonVisible(true);
        filterTextByPatient.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextByPatient.addValueChangeListener(e -> updateList());

        filterTextByDoctor.setPlaceholder("Filter by Doctor...");
        filterTextByDoctor.setClearButtonVisible(true);
        filterTextByDoctor.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextByDoctor.addValueChangeListener(e -> updateList());


        Button addDoctorButton = new Button("Add Recipe");
        addDoctorButton.addClickListener(click -> addRecipe());

        HorizontalLayout toolbar = new HorizontalLayout(
                filterTextByDescription,
                filterTextByPatient,
                filterTextByDoctor,
                addDoctorButton);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRecipe() {
        gridRecipe.asSingleSelect().clear();
        editRecipe(new Recipe());
    }

    private void configureGrid() {
        gridRecipe.addClassName("recipeGrid");
        gridRecipe.setSizeFull();
        gridRecipe.removeColumnByKey("patient");
        gridRecipe.removeColumnByKey("doctor");
//        gridRecipe.setColumns("description");
        gridRecipe.setColumns("description", "priority");

        gridRecipe.addColumn(new LocalDateRenderer<>(Recipe::getDateCreation, "dd-MMM-yyyy", Locale.ENGLISH))
                .setHeader("Date Creation");
        gridRecipe.addColumn(new LocalDateRenderer<>(Recipe::getDateTermination, "dd-MMM-yyyy", Locale.ENGLISH))
                .setHeader("Date Termination");

        gridRecipe.addColumn(recipe -> {
            Doctor doctor = recipe.getDoctor();
            return doctor == null ? "-" : doctor.getFirstAndSecondName();
        }).setHeader("Doctor");
        gridRecipe.addColumn(recipe -> {
            Patient patient = recipe.getPatient();
            return patient == null ? "-" : patient.getFirstAndSecondName();
        }).setHeader("Patient");

        gridRecipe.addColumn(new NativeButtonRenderer<>("Edit", this::editRecipe)).setHeader("Action");

//        gridRecipe.setColumns("priority");
        gridRecipe.setItemDetailsRenderer(new ComponentRenderer<>(recipe -> {
            VerticalLayout layout = new VerticalLayout();
            layout.add(new Label("Description: " + recipe.getDescription()));
            return layout;
        }));

//        gridRecipe.asSingleSelect().addValueChangeListener(event -> editRecipe(event.getValue()));
    }

    private void saveRecipe(RecipeForm.SaveEvent event) {
        recipeService.save(event.getRecipe());
        updateList();
        closeEditor();
    }

    private void updateList() {
        gridRecipe.setItems(recipeService.findAll(
                filterTextByDescription.getValue(),
                filterTextByDoctor.getValue(),
                filterTextByPatient.getValue()
                ));
    }

    private void closeEditor() {
        recipeForm.setRecipe(null);
        recipeForm.setVisible(false);
        removeClassName("editing");
    }

    private void editRecipe(Recipe recipe){
        if (recipe == null){
            closeEditor();
        } else {
            recipeForm.setRecipe(recipe);
            recipeForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteRecipe(RecipeForm.DeleteEvent event) {
        recipeService.delete(event.getRecipe());
        updateList();
        closeEditor();
    }
}
