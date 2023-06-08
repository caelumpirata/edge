package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.SourceModbusRTUTable;
import com.edge.application.views.Table.SourceModbusTCPTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.edge.PlatformView;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Modbus TCP Protocol Form")
@Route(value = "modbustcpprotocol" ,layout = MainLayout.class)
public class ModbusTCPProtocolForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="modbustcpprotocol";
	
	TextField txt_source_id     = new TextField("Source Id");
	TextField txt_ip     = new TextField("TCP / IP Address");
	TextField txt_port     = new TextField("Port");
		
	
	Button btn_back = new Button("Back");
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	Grid<SourceModbusTCPTable> grid = new Grid<SourceModbusTCPTable>();
	ListDataProvider<SourceModbusTCPTable> dataProvider;
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;

	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
		
		
		
		
	    
        String src_id=""+para;
        
        txt_source_id.setValue(src_id);
		txt_source_id.setReadOnly(true);
    
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (SourceTable sou_table : service.source_id_list()) {

			getid = sou_table.getSource_id().toString();
		}

if(!peUtil.isNullString(src_id)) {
			
			String id =src_id.toString();
			
			for(SourceModbusTCPTable stt:service.source_port_list_tcp(id))
			{
				
				txt_ip.setValue(stt.getIP());
				txt_port.setValue(stt.getPort());
				
				
			}
        }
		
		
		
		// Validation code
		binder.forField(txt_source_id).asRequired("Enter Source ID").bind(Validation::getString,Validation::setString);

		binder.forField(txt_ip).asRequired("Enter TCP/IP Address").bind(Validation::getString,Validation::setString);
		binder.forField(txt_port).asRequired("Entert Port").bind(Validation::getString,Validation::setString);
				
		btn_next.setEnabled(false);
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				String source_id="",source_name="",port="",ip="";
				source_id = txt_source_id.getValue().toString();
				source_name =service.get_Source_name(source_id);
				
				if(service.check_source_modbus_tcp(source_id)) {
					
					Notification.show("Already Source has been saved ...!");
					
				}else {
					
						
						
						
						ip = txt_ip.getValue().toString();
						port = txt_port.getValue().toString();
						
												
						SourceModbusTCPTable st = new SourceModbusTCPTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setIP(ip);
						st.setPort(port);
						st.setRowid(genUniqueID());
						
						service.source_modbus_tcp_save(st);
						
						service.update_protocol(source_id,"MODBUS TCP");
						
						
						
						if(service.check_source_modbus_rtu(source_id)) {
							
							
							service.deleteSource_modbus_rtu(source_id);
						}
						
						 if(service.check_source_opcua(source_id)) {
								
								
								service.deleteSource_opcua(source_id);
							}
						 
						 if(service.check_source_opcua(source_id)) {
								
								
								service.deleteSource_opcua(source_id);
							}
						 if(service.check_source_mqtt(source_id)) {
								
								
								service.deleteSource_mqtt(source_id);
							}
						
						Notification.show("Source and TCP/IP has been mapped sucessfully...!");
						
						
						btn_next.setEnabled(true);
						btn_save.setEnabled(false);
						btn_skip.setEnabled(true);
						
						//UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
						update();
				}
            	
					
        		
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
		
		
		btn_next.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				String source_id = txt_source_id.getValue().toString();
				if(service.check_source_modbus_tcp(source_id))
        		{
					UI.getCurrent().navigate( SourceElementTagForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
					//UI.getCurrent().navigate(SourceForm.ROUTE_NAME + "/" + src_id);
        		}else {
        			Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
        		}
			}// biner if condition
			else
			{
				Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
			}
			
		});
				
		btn_skip.addClickListener(e -> {
			String id =src_id.toString();
			if(service.check_source_tcp(id)) {
				UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME + "/"+src_id);
			}
			
			
		});
		btn_back.addClickListener(e -> {
			
			String parameters=txt_source_id.getValue().toString();
			long id = service.get_main_source_id(parameters);
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/"+id);
		
		
		
	});		
		 // layout add code
		btn_layout.removeAll();
		if(peUtil.isNullString(src_id)) {
		   btn_layout.add(btn_back,btn_save,btn_next);
		}else {
			String id =src_id.split("-")[0];
			if(service.check_source_tcp(id) ) {
			   btn_layout.add(btn_back,btn_save, btn_skip);
			}else {
				btn_layout.add(btn_back,btn_save,btn_next);
			}
		}
		formLayout.add( txt_source_id, txt_ip, txt_port, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		update();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		
		Grid.Column<SourceModbusTCPTable> Raw_Material = grid.addColumn(SourceModbusTCPTable::getSource_id).setHeader("Source ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceModbusTCPTable> sap_id = grid.addColumn(SourceModbusTCPTable::getSource_name).setHeader("Source Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceModbusTCPTable> scada_id = grid.addColumn(SourceModbusTCPTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceModbusTCPTable> scada_ip = grid.addColumn(SourceModbusTCPTable::getIP).setHeader("TCP/IP Address")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<SourceModbusTCPTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
							service.deleteSource_modbus_tcp(sourcedata.getId());
							//eti.deleteById(TagTableData.getId());
							btn_save.setEnabled(false);
							btn_save.setEnabled(true);
							btn_skip.setEnabled(false);
							//UI.getCurrent().navigate(ModbusTCPProtocolForm.ROUTE_NAME + "/" +txt_source_id.getValue().toString());
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();
						btn_save.setEnabled(true);	
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
		
		add(grid);
		
	}
	public void update() {

		List<SourceModbusTCPTable> list = service.source_tcp_list();
		dataProvider = new ListDataProvider<>(list);

		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	public static String genUniqueID()
	{ 
	  Date d = new Date();
	  long utn = d.getTime();

	  double rand = Math.random();

	  StringBuffer str = new StringBuffer();
	  str.append( utn + "-" + rand);

	  String id = md5(str.toString());

	  

	  return id;
	}
	public static String md5(String str)
{
MessageDigest md;
try
{
md = MessageDigest.getInstance("MD5");

md.update(str.getBytes());
byte[] b = md.digest();
String out = dumpBytes(b);
return out;
}
catch (Exception e) {

}

return "";
}
private static String dumpBytes(byte[] bytes)
{
int size = bytes.length;
StringBuffer sb = new StringBuffer(size * 2);

for (int i = 0; i < size; ++i) {
String s = Integer.toHexString(bytes[i]);

if (s.length() == 8)
{
  sb.append(s.substring(6));
}
else if (s.length() == 2)
{
  sb.append(s);
}
else
{
  sb.append("0" + s);
}
}

return sb.toString();
}

}
