package com.edge.application.views.edge;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

import com.edge.application.views.MainLayout;
import com.edge.application.views.form.SourceForm;
import com.edge.application.views.form.UserChangePasswordForm;
import com.edge.application.views.form.UserDetailsForm;
import com.edge.application.views.form.UserForm;
import com.edge.application.views.form.userLockUnlockForm;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Grid.UserTypeList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PermitAll
@PageTitle("User Management")
@Route(value = "usermanagement", layout = MainLayout.class)
public class UserManagementView extends VerticalLayout {
	
	
	VerticalLayout layout_left = new VerticalLayout();
	VerticalLayout layout_right = new VerticalLayout();
	
	@PostConstruct
	public void init() {
		
		//LEFT-VERTICAL-LAYOUT
		layout_left.add(createLayout("User Form", VaadinIcon.CLIPBOARD_USER, UserList.class));
		
		layout_left.add(createLayout("Lock / Unlock",VaadinIcon.USER_CLOCK, userLockUnlockForm.class));
		layout_left.getStyle().set("align-items" ,"end");
		
		//RIGHT-VERTICAL-LAYOUT
		layout_right.add(createLayout("Permission", VaadinIcon.LOCK, UserTypeList.class));
		layout_right.add(createLayout("Change Password", VaadinIcon.PASSWORD, UserChangePasswordForm.class));
		layout_right.getStyle().set("align-items" ,"start");
				
		//STYLING MAIN LAYOUT
		this.setWidthFull();
		this.setHeightFull();
		this.getElement().getStyle().set("display" ,"flex").set("flex-direction", "row");
		this.getElement().getStyle().set("align-items" ,"center"); //aligns content horizontally
		this.getStyle().set("justify-content" ,"center"); //aligns content vertically
		
		add(layout_left, layout_right);
	}
	
	//CREATES RECTANGULAR BOXES
	@SuppressWarnings("unchecked")
	private Component createLayout(String app, VaadinIcon vaadin_icon, Class class_name) {
		VerticalLayout v1 = new VerticalLayout();
		v1.getElement().getStyle().set("background-color", "white");
		v1.getElement().getStyle().set("border-radius" ,"8px");
		v1.getElement().getStyle().set("box-shadow", "rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px");
		v1.setWidth("400px");
		v1.setHeight("200px");
		
		Label app_name = new Label();
		app_name.setText(app);
		app_name.getElement().getStyle().set("font-weight" ,"bold").set("font-size", "20px");
		
		Icon icon = new Icon(vaadin_icon);
		icon.getStyle().set("margin-left", "auto")
						.set("margin-right", "auto")
						.set("font-size", "50px");
		
		Button app_action = new Button("OPEN");
		app_action.getElement().getStyle().set("margin-top", "auto").set("font-weight" ,"bold").set("color" ,"#69ace7");
		app_action.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		app_action.addClickListener(e->{
			UI.getCurrent().navigate(class_name);
		});
		
		v1.add(app_name, icon, app_action);
		return v1;
	}
}
