package com.edge.application.views.Grid;



import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.sourcePublisherTable;
import com.edge.application.views.Table.userTable;
import com.edge.application.views.form.ElementForm;
import com.edge.application.views.form.ElementTagForm;
import com.edge.application.views.form.SourceElementForm;
import com.edge.application.views.form.SourceForm;
import com.edge.application.views.form.TagForm;
import com.edge.application.views.form.UserForm;
import com.edge.application.views.form.sourcePublisherForm;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Source Publisher Map List")
@Route(value = "SourcePublisherMappinglist",layout = MainLayout.class)
public class SourcePublisherMappingList extends VerticalLayout{
	
	public static final String ROUTE_NAME="SourcePublisherMappinglist";
	Button btn_add = new Button("Mapped Source Publisher");
	
	TextField txt_search = new TextField();
	Label heading= new Label();
	
	Grid<sourcePublisherTable> grid = new Grid<sourcePublisherTable>();
	ListDataProvider<sourcePublisherTable> dataProvider;
	
	HorizontalLayout layout1 = new HorizontalLayout();
	
	@Autowired
	private EdgeService service;
	
	@PostConstruct
	public void init()
	{
		
		
		btn_add.setIcon(new Icon(VaadinIcon.FILE_ADD));
		btn_add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_add.setWidthFull();
		layout1.setWidthFull();
		
		txt_search.setPlaceholder("Search");
		txt_search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		txt_search.setClearButtonVisible(true);
		txt_search.setValueChangeMode(ValueChangeMode.LAZY);
		//txt_search.addValueChangeListener(e -> update());
		
		grid.setWidthFull();
		heading.setText("Source Element List");
		heading.setWidthFull();
		//heading.getElement().getStyle().set("margin-top", "0px");
		heading.getStyle().set("text-align", "center");
		heading.getStyle().set("font-weight", "bold");
		
		btn_add.addClickListener(e->{
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME);
		});
		update();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		Grid.Column<sourcePublisherTable> Raw_Material = grid.addColumn(sourcePublisherTable::getSource_id).setHeader("Source ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<sourcePublisherTable> sap_id = grid.addColumn(sourcePublisherTable::getSourceName).setHeader("Source Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<sourcePublisherTable>elem_id = grid.addColumn(sourcePublisherTable::getPublisherType).setHeader("Publisher")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);

		
		
		
		
		//////////////((((((**********//////////////////////////
		
		// add Instruction code btn
		Grid.Column<sourcePublisherTable> editsource = grid.addComponentColumn(editdata -> {
			
			// create edit button for each row
			Button addinst = new Button("EDIT");

			// set icon
			addinst.setIcon(new Icon(VaadinIcon.EDIT));

			// set theme
			addinst.addThemeVariants(ButtonVariant.LUMO_SMALL);
			

			// on click operation
			addinst.addClickListener(ed -> {
				UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/" + editdata.getSource_id());
			});
			return addinst;
		}).setHeader("Edit").setTextAlign(ColumnTextAlign.CENTER);
			
		// end
		
		// delete code for grid
		
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<sourcePublisherTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

					// create edit button for each row
					Button delete = new Button("DELETE");

					// set icon
					delete.setIcon(new Icon(VaadinIcon.TRASH));

					// set theme
					delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
					

					// on click operation
					delete.addClickListener(ed -> {

						
							Dialog dialog = new Dialog();
							dialog.add(new Text("Are you sure you want to delete tag...?"));
							dialog.setCloseOnEsc(false);
							dialog.setCloseOnOutsideClick(false);

							Button confirmButton = new Button("Confirm", event -> {
								try {
									
									
									service.deleteSource_publisher(sourcedata.getId());							
									
									
									
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.toString());
								}
								dialog.close();

								update();

							});
							Button cancelButton = new Button("Cancel", event -> {
								dialog.close();
							});

							dialog.add(new HorizontalLayout(confirmButton, cancelButton));

							dialog.open();
						
					});
					return delete;
				}).setHeader("Delete").setTextAlign(ColumnTextAlign.CENTER);
				
				// delete end
				
		
		grid.setSizeUndefined();
		grid.setHeightByRows(true);
		
		
		
		layout1.add(txt_search,btn_add);
		add(layout1,grid);
	}
	public void update() {

		List<sourcePublisherTable> list = service.source_id();
		dataProvider = new ListDataProvider<>(list);

		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}


}
