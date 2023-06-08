package com.edge.application.views;


import com.edge.application.components.appnav.AppNav;
import com.edge.application.components.appnav.AppNavItem;
import com.edge.application.views.Grid.KafkaConfigurationList;
import com.edge.application.views.Grid.MqttConfigurationList;
import com.edge.application.views.Grid.ProtocolList;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.edge.UserManagementView;
import com.edge.application.views.form.sourcePublisherForm;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("EDGE");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

//        nav.addItem(new AppNavItem("User Management", UserManagementView.class, "la la-file"));
//        nav.addItem(new AppNavItem("Source Management", SourceList.class, "la la-file"));
        //nav.addItem(new AppNavItem("Publisher", PublisherView.class, "la la-file"));
       // nav.addItem(new AppNavItem("Element Management", ElementManagementView.class, "la la-file"));
        
        AppNavItem usermanagement = new AppNavItem("User Management",UserManagementView.class);
        Icon usermanagement_icon  = new Icon(VaadinIcon.USER);
        usermanagement_icon.getStyle().set("font-size", "small").set("margin-right", "4%").set("margin-left", "4%"); 
        usermanagement.setIcon(usermanagement_icon);
        
        AppNavItem sourceManagemet = new AppNavItem("Source Management",SourceList.class);
        Icon sourceManagemet_icon  = new Icon(VaadinIcon.FILE_ADD);
        sourceManagemet_icon.getStyle().set("font-size", "small").set("margin-right", "4%").set("margin-left", "4%"); 
        sourceManagemet.setIcon(sourceManagemet_icon);
        
        AppNavItem protocolmanagement = new AppNavItem("Protocol Management",ProtocolList.class);
        Icon protocolmanagement_icon  = new Icon(VaadinIcon.GRID_BIG);
        protocolmanagement_icon.getStyle().set("font-size", "small").set("margin-right", "4%").set("margin-left", "4%"); 
        protocolmanagement.setIcon(protocolmanagement_icon);
        
        AppNavItem Setting = new AppNavItem("Setting");
        Setting.addItem(new AppNavItem("MQTT Configuration",MqttConfigurationList.class));
        Setting.addItem(new AppNavItem("KAFKA Configuration",KafkaConfigurationList.class));
       // Setting.addItem(new AppNavItem("Source Publisher",sourcePublisherForm.class));
        Icon user_management_icon  = new Icon(VaadinIcon.COG_O);
        user_management_icon.getStyle().set("font-size", "small").set("margin-right", "4%").set("margin-left", "4%"); 
        Setting.setIcon(user_management_icon);
        nav.addItem(usermanagement,sourceManagemet,protocolmanagement,Setting);

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
