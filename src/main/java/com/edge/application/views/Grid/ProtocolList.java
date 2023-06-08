package com.edge.application.views.Grid;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.form.BacnetProtocolForm;
import com.edge.application.views.form.ModbusRTUProtocolForm;
import com.edge.application.views.form.ModbusTCPProtocolForm;
import com.edge.application.views.form.MqttProtocolForm;
import com.edge.application.views.form.OpcuaProtocolForm;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value="protocollist",layout = MainLayout.class)
public class ProtocolList extends VerticalLayout{
	public static final String ROUTE_NAME="ProtocolList";
	Label heading= new Label();
	
	Grid<SourceTable> grid = new Grid<SourceTable>();
	ListDataProvider<SourceTable> dataProvider;

	@Autowired
	private EdgeService service;
	
	@PostConstruct
	public void init()
	{
		grid.setWidthFull();
		heading.setText("Protocol-Source List");
		heading.setWidthFull();
		//heading.getElement().getStyle().set("margin-top", "0px");
		heading.getStyle().set("text-align", "center");
		heading.getStyle().set("font-weight", "bold");
		
		update();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		
		Grid.Column<SourceTable> Raw_Material = grid.addColumn(SourceTable::getSource_id).setHeader("Source ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTable> sap_id = grid.addColumn(SourceTable::getSource_name).setHeader("Source Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTable> protocol_id = grid.addColumn(SourceTable::getProtocol).setHeader("Protocol Type")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
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
//						UI.getCurrent().navigate(PlatformView.ROUTE_NAME+"/"+editdata.getSource_id()+"-"+editdata.getSource_name());
					
						if(editdata.getProtocol().toString().equalsIgnoreCase("BACNET")) 
						{
		            		UI.getCurrent().navigate( BacnetProtocolForm.ROUTE_NAME+ "/" + editdata.getSource_id());
						}
						else if(editdata.getProtocol().toString().equalsIgnoreCase("MQTT"))
						{
							UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" +  editdata.getSource_id()+"-"+editdata.getSource_name() );
						}
						else if(editdata.getProtocol().toString().equalsIgnoreCase("OPCUA"))
						{
							UI.getCurrent().navigate( OpcuaProtocolForm.ROUTE_NAME+ "/" +  editdata.getSource_id()+"-"+editdata.getSource_name() );
						}
						else if(editdata.getProtocol().toString().equalsIgnoreCase("MODBUS RTU"))
						{
							UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" +  editdata.getSource_id()+"-"+editdata.getSource_name() );
						}
						else if(editdata.getProtocol().toString().equalsIgnoreCase("MODBUS TCP"))
						{
							UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" +  editdata.getSource_id()+"-"+editdata.getSource_name() );
						}
						else
						{
							
						}
					});
					return addinst;
				}).setHeader("Edit").setTextAlign(ColumnTextAlign.CENTER);
					
				// end
		
		
		grid.setSizeUndefined();
		grid.setHeightByRows(true);
		
		
		
		
		add(heading,grid);
		
	}
	
	public void update() {

		List<SourceTable> list = service.source_list();
		dataProvider = new ListDataProvider<>(list);

		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}

}
