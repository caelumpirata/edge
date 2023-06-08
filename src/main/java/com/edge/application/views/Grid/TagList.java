package com.edge.application.views.Grid;



import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.userTable;
import com.edge.application.views.form.ElementForm;
import com.edge.application.views.form.SourceForm;
import com.edge.application.views.form.TagForm;
import com.edge.application.views.form.UserForm;
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

@PageTitle("Tag List")
@Route(value = "taglist",layout = MainLayout.class)
public class TagList extends VerticalLayout{
	
	public static final String ROUTE_NAME="taglist";
	Button btn_add = new Button("ADD Tag");
	
	TextField txt_search = new TextField();
	Label heading= new Label();
	
	Grid<TagTable> grid = new Grid<TagTable>();
	ListDataProvider<TagTable> dataProvider;
	
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
		heading.setText("Tag List");
		heading.setWidthFull();
		//heading.getElement().getStyle().set("margin-top", "0px");
		heading.getStyle().set("text-align", "center");
		heading.getStyle().set("font-weight", "bold");
		
		btn_add.addClickListener(e->{
			UI.getCurrent().navigate(TagForm.ROUTE_NAME+"/0");
		});
		update();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		Grid.Column<TagTable> Raw_Material = grid.addColumn(TagTable::getTag_id).setHeader("Tag ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable> sap_id = grid.addColumn(TagTable::getTag_name).setHeader("Tag Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable>ref_id = grid.addColumn(TagTable::getTagRefAddress).setHeader("Reference Number")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable>reg_id = grid.addColumn(TagTable::getTagRegAddress).setHeader("Register Number")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable>data_type = grid.addColumn(TagTable::getDataType).setHeader("Data Type")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable>unit = grid.addColumn(TagTable::getUnit).setHeader("Unit")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		
		
		//////////////((((((**********//////////////////////////
		
		// add Instruction code btn
		Grid.Column<TagTable> editsource = grid.addComponentColumn(editdata -> {
			
			// create edit button for each row
			Button addinst = new Button("EDIT");

			// set icon
			addinst.setIcon(new Icon(VaadinIcon.EDIT));

			// set theme
			addinst.addThemeVariants(ButtonVariant.LUMO_SMALL);
			

			// on click operation
			addinst.addClickListener(ed -> {
				UI.getCurrent().navigate(TagForm.ROUTE_NAME + "/" + editdata.getId());
			});
			return addinst;
		}).setHeader("Edit").setTextAlign(ColumnTextAlign.CENTER);
			
		// end
		
		// delete code for grid
		
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<TagTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
									
																								
									service.deleteTag(sourcedata.getId());							
								
									
									
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

		List<TagTable> list = service.tagList();
		dataProvider = new ListDataProvider<>(list);

		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}


}
