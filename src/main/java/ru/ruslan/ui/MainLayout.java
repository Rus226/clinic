package ru.ruslan.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.VaadinSession;
import ru.ruslan.ui.view.doctor.DoctorView;
import ru.ruslan.ui.view.patient.PatientView;
import ru.ruslan.ui.view.recipe.RecipeView;
import ru.ruslan.ui.view.statisticOfDoctor.StatisticOfDoctorView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();

    }

    private void createDrawer() {
        RouterLink doctorLink = new RouterLink("Doctors", DoctorView.class);
        doctorLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink patientLink = new RouterLink("Patients", PatientView.class);
        patientLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink recipeLink = new RouterLink("Recipes", RecipeView.class);
        recipeLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink statisticLink = new RouterLink("Statistic Of Doctor", StatisticOfDoctorView.class);
        statisticLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(recipeLink, doctorLink, patientLink, statisticLink));
    }

    private void createHeader() {
        H1 logo = new H1("Clinic for YOU");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);

    }
}
