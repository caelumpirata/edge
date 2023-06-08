package com.edge.application.views.Grid;



import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.form.SourceForm;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
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

@PageTitle("SourceList")
@Route(value = "sourcelist",layout = MainLayout.class)
public class SourceList extends VerticalLayout{
	
	
	Button btn_add = new Button("ADD SOURCE");
	
	TextField txt_search = new TextField();
	Label heading= new Label();
	
	Grid<SourceTable> grid = new Grid<SourceTable>();
	ListDataProvider<SourceTable> dataProvider;
	
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
		heading.setText("Source List");
		heading.setWidthFull();
		//heading.getElement().getStyle().set("margin-top", "0px");
		heading.getStyle().set("text-align", "center");
		heading.getStyle().set("font-weight", "bold");
		
		btn_add.addClickListener(e->{
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/0");
		});
		update();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		Grid.Column<SourceTable> Raw_Material = grid.addColumn(SourceTable::getSource_id).setHeader("Source ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTable> sap_id = grid.addColumn(SourceTable::getSource_name).setHeader("Source Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTable> scada_id = grid.addColumn(SourceTable::getApplication_name).setHeader("Application Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTable> protocol_id = grid.addColumn(SourceTable::getProtocol).setHeader("Protocol Type")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		// add row from different table 
		
				Grid.Column<SourceTable> Usertype = grid.addComponentColumn(event -> {
					Text publisher_type;
					if (service.getPublisherType(event.getSource_id()) != null) {
						publisher_type = new Text(service.getPublisherType(event.getSource_id()));
					} else {
						publisher_type = new Text(" ");
					}
					return publisher_type;
				}).setHeader("Publisher Type").setSortable(true).setResizable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				// serching text box
				HeaderRow filterRow = grid.appendHeaderRow();
				TextField source_id = new TextField();
				source_id.addValueChangeListener(event1 -> dataProvider.addFilter(
				        group -> StringUtils.containsIgnoreCase(group.getSource_id(),
				        		source_id.getValue())));

				source_id.setValueChangeMode(ValueChangeMode.EAGER);
				source_id.setWidth(Raw_Material.getWidth());
				filterRow.getCell(Raw_Material).setComponent(source_id);
				source_id.setPlaceholder("Source ID Filter");
				
				TextField source_name = new TextField();
				source_name.addValueChangeListener(event1 -> dataProvider.addFilter(
				        group -> StringUtils.containsIgnoreCase(group.getSource_name(),
				        		source_name.getValue())));

				source_name.setValueChangeMode(ValueChangeMode.EAGER);
				source_name.setWidth(sap_id.getWidth());
				filterRow.getCell(sap_id).setComponent(source_name);
				source_name.setPlaceholder("Source Name Filter");
				
				TextField app_name = new TextField();
				app_name.addValueChangeListener(event1 -> dataProvider.addFilter(
				        group -> StringUtils.containsIgnoreCase(group.getApplication_name(),
				        		app_name.getValue())));

				app_name.setValueChangeMode(ValueChangeMode.EAGER);
				app_name.setWidth(scada_id.getWidth());
				filterRow.getCell(scada_id).setComponent(app_name);
				app_name.setPlaceholder("Application  Filter");
				
				TextField protocol_type = new TextField();
				protocol_type.addValueChangeListener(event1 -> dataProvider.addFilter(
				        group -> StringUtils.containsIgnoreCase(group.getProtocol(),
				        		protocol_type.getValue())));

				protocol_type.setValueChangeMode(ValueChangeMode.EAGER);
				protocol_type.setWidth(protocol_id.getWidth());
				filterRow.getCell(protocol_id).setComponent(protocol_type);
				protocol_type.setPlaceholder("Protocol Type Filter");
		
		
		
		// add Instruction code btn
		Grid.Column<SourceTable> editsource = grid.addComponentColumn(editdata -> {

			// create edit button for each row
			Button addinst = new Button("EDIT");

			// set icon
			addinst.setIcon(new Icon(VaadinIcon.EDIT));

			// set theme
			addinst.addThemeVariants(ButtonVariant.LUMO_SMALL);
			

			// on click operation
			addinst.addClickListener(ed -> {
				
				String proto_type = editdata.getProtocol();
				//if(!proto_type.equalsIgnoreCase("BACNET")) {
				UI.getCurrent().navigate(SourceForm.ROUTE_NAME + "/" + editdata.getId());
				//}
			});
			return addinst;
		}).setHeader("Edit").setTextAlign(ColumnTextAlign.CENTER);
			
		// end
		
		// delete code for grid
		
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<SourceTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

					// create edit button for each row
					Button delete = new Button("DELETE");

					// set icon
					delete.setIcon(new Icon(VaadinIcon.TRASH));

					// set theme
					delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
					

					// on click operation
					delete.addClickListener(ed -> {

						
							Dialog dialog = new Dialog();
							dialog.add(new Text("Are you sure you want to delete Source...?"));
							dialog.setCloseOnEsc(false);
							dialog.setCloseOnOutsideClick(false);

							Button confirmButton = new Button("Confirm", event -> {
								try {
									
                                    String src_id =sourcedata.getSource_id();
									
									service.deleteSource(sourcedata.getId());
									
									if(service.check_Source_id_publisher(src_id)) {
									   service.deleteSourcePublisher(src_id);
									}
									
									if(service.check_source_tag_element_id_only(src_id)) {
										service.deleteMap_SourceElementTag(src_id);
									}
									if(service.check_source_modbus_rtu(src_id)) {
										service.deleteSource_modbus_rtu(src_id);
									}
									if(service.check_source_modbust_tcp(src_id)) {
										service.deleteSource_modbus_tcp(src_id);
									}
									 if(service.check_source_opcua(src_id)) {
											
											
											service.deleteSource_opcua(src_id);
										}
									 if(service.check_source_bacnet(src_id)) {
											
											
											service.deleteSource_bacnet(src_id);
										}
									//eti.deleteById(TagTableData.getId());
									
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

		List<SourceTable> list = service.source_list();
		dataProvider = new ListDataProvider<>(list);

		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}


}
