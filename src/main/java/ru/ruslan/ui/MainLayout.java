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
import ru.ruslan.ui.view.doctor.DoctorView;
import ru.ruslan.ui.view.patient.PatientView;

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

        addToDrawer(new VerticalLayout(doctorLink, patientLink));
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
