package ru.clinic.ui.view.recipe;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.clinic.backend.entity.Doctor;
import ru.clinic.backend.entity.Patient;
import ru.clinic.backend.entity.Priority;
import ru.clinic.backend.entity.Recipe;
import ru.clinic.backend.service.DoctorService;
import ru.clinic.backend.service.PatientService;
import ru.clinic.backend.service.RecipeService;
import ru.clinic.ui.MainLayout;

import java.util.Locale;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Recipes")
public class RecipeView extends VerticalLayout {

    private DoctorService doctorService;
    private RecipeService recipeService;
    private PatientService patientService;

    private Grid<Recipe> gridRecipe = new Grid<>(Recipe.class);

    private TextField filterTextByDescription = new TextField();
    private TextField filterTextByPatient = new TextField();

    private ComboBox<Priority> priority = new ComboBox<>();

    private RecipeDialog recipeDialog;

    public RecipeView(DoctorService doctorService, RecipeService recipeService, PatientService patientService) {
        this.doctorService = doctorService;
        this.recipeService = recipeService;
        this.patientService = patientService;

            addClassName("recipe-view");
            setSizeFull();
            configureGrid();

            recipeDialog = new RecipeDialog(doctorService.findAll(), patientService.findAll());
            recipeDialog.addListener(RecipeDialog.SaveEvent.class, this::saveRecipe);
            recipeDialog.addListener(RecipeDialog.DeleteEvent.class, this::deleteRecipe);
            recipeDialog.addListener(RecipeDialog.CloseEvent.class, e -> closeEditor());

            Div content = new Div(gridRecipe, recipeDialog);
            content.addClassName("content");
            content.setSizeFull();

            add(configureToolBar(), content);
            updateList();
            closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        filterTextByDescription.setPlaceholder("Filter by Description...");
        filterTextByDescription.setClearButtonVisible(true);

        filterTextByPatient.setPlaceholder("Filter by Patient...");
        filterTextByPatient.setClearButtonVisible(true);

        priority.setPlaceholder("Filter by Priority...");
        priority.setItems(Priority.values());
        priority.setClearButtonVisible(true);

        Button filterButton = new Button("Filter");
        filterButton.addClickListener(click -> updateList());
        filterButton.addClickShortcut(Key.ENTER);

        Button addDoctorButton = new Button("Add Recipe");
        addDoctorButton.addClickListener(click -> addRecipe());
        addDoctorButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout toolbar = new HorizontalLayout(
                filterTextByDescription,
                filterTextByPatient,
                priority,
                filterButton,
                addDoctorButton
        );

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRecipe() {
        editRecipe(new Recipe());
    }

    private void configureGrid() {
        gridRecipe.addClassName("recipeGrid");
        gridRecipe.setSizeFull();
        gridRecipe.removeColumnByKey("patient");
        gridRecipe.removeColumnByKey("doctor");
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

        gridRecipe.setItemDetailsRenderer(new ComponentRenderer<>(recipe -> {
            VerticalLayout layout = new VerticalLayout();
            layout.add(new Label("Description: " + recipe.getDescription()));
            return layout;
        }));
    }

    private void updateList() {
        gridRecipe.setItems(recipeService.findAll(
                filterTextByDescription.getValue(),
                filterTextByPatient.getValue(),
                priority.getValue()
        ));
    }

    private void closeEditor() {
        recipeDialog.setRecipe(null);
        recipeDialog.close();
    }

    private void saveRecipe(RecipeDialog.SaveEvent event) {
        recipeService.save(event.getRecipe());
        updateList();
        closeEditor();
    }

    private void editRecipe(Recipe recipe){
        if (recipe == null){
            closeEditor();
        } else {
            recipeDialog.setRecipe(recipe);
            recipeDialog.open();
        }
    }

    private void deleteRecipe(RecipeDialog.DeleteEvent event) {
        recipeService.delete(event.getRecipe());
        updateList();
        closeEditor();
    }
}
