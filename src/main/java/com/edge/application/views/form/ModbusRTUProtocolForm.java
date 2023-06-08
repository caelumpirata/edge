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
//import gnu.io.CommPortIdentifier;

@PageTitle("Modbus RTU Protocol Form")
@Route(value = "modbusrtuprotocol" ,layout = MainLayout.class)
public class ModbusRTUProtocolForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="modbusrtuprotocol";
	
	TextField txt_source_id     = new TextField("Source Id");
		
	ComboBox<String> com_port = new ComboBox<>("COM Port");
	ComboBox<String> baud = new ComboBox<>("Baud Rate");
	ComboBox<String> data_bits = new ComboBox<>("Data Bits");
	
	ComboBox<String> stop_bits = new ComboBox<>("Stop Bits");
	ComboBox<String> parity = new ComboBox<>("Parity");
	ComboBox<String> polling_interval = new ComboBox<>("Polling Interval");
	ComboBox<String> polling_set_time = new ComboBox<>("Set Time Format");
	ComboBox<String> report_interval = new ComboBox<>("Report Interval");
	ComboBox<String> report_set_time = new ComboBox<>("Set Time Format");
	
	List<String> ls=new ArrayList<String>();
	List<String> ls_port=new ArrayList<String>();
	
	Button btn_back = new Button("Back");
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	HorizontalLayout btn_poll_layout = new HorizontalLayout();
	HorizontalLayout btn_rep_layout = new HorizontalLayout();
	
	Grid<SourceModbusRTUTable> grid = new Grid<SourceModbusRTUTable>();
	ListDataProvider<SourceModbusRTUTable> dataProvider;
	
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
		
		/*Enumeration portList;
		CommPortIdentifier portId;
		try{
			System.out.println("In Try::");
			portList = CommPortIdentifier.getPortIdentifiers();

			System.out.println("While Up::");
			while (portList.hasMoreElements()) {
				System.out.println("In While::");
				portId = (CommPortIdentifier) portList.nextElement();
				//htComport.put(portId.getName(),portId.getName());
				//System.out.println("Com port....."+portId.getName());
				ls.add(portId.getName());
			}
			
			}catch(Exception ae){
				System.out.println(ae);
				
			}*/
		ls=new ArrayList<String>();
		ls.add("COM1");	
		ls.add("COM2");	
		ls.add("COM3");	
		ls.add("COM4");	
		ls.add("COM5");	
		
	    com_port.setAllowCustomValue(true); 
	    
	    ls_port=new ArrayList<String>();
	    
	    for(int s=0;s<ls.size();s++) {
	    	
	    	String val = ""+ls.get(s);
	    	if(!service.check_source_comport(val)) {
	    		ls_port.add(val);
	    	}
	    	
	    }
	    
	    com_port.setItems(ls_port);
	    
	    baud.setAllowCustomValue(true); 
	    baud.setItems("2400","4800","9600","19200","38400","76800","153600");
	    
	    data_bits.setAllowCustomValue(true); 
	    data_bits.setItems("7","8");
        
	    stop_bits.setAllowCustomValue(true); 
	    stop_bits.setItems("0","1","2");
	    
	    parity.setAllowCustomValue(true); 
	    parity.setItems("None","Odd","Even");
	    
	    polling_interval.setAllowCustomValue(true); 
	    polling_interval.setItems("5","10","15","20","25","30");
	    
	    report_interval.setAllowCustomValue(true); 
	    report_interval.setItems("5","10","15","20","25","30");  
	  
	   
	    polling_set_time.setAllowCustomValue(true); 
	    polling_set_time.setItems("Hour","Minutes","Seconds");
	    polling_set_time.setValue("Minutes");
	    
	    report_set_time.setAllowCustomValue(true); 
	    report_set_time.setItems("Hour","Minutes","Seconds");
	    report_set_time.setValue("Minutes");
	    
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
			
			String id =src_id.split("-")[0];
			
			for(SourceModbusRTUTable stt:service.source_port_list(id))
			{
				
				com_port.setValue(stt.getCom_port());
				baud.setValue(stt.getBaud());
				data_bits.setValue(stt.getData_bits());
				stop_bits.setValue(stt.getStop_bits());
				parity.setValue(stt.getParity());
				polling_interval.setValue(stt.getPoll_interval());
				report_interval.setValue(stt.getRep_interval());
				polling_set_time.setValue(stt.getPolling_set_time());
				report_set_time.setValue(stt.getReporting_set_time());
				
			}
        }
		
		// Validation code
		binder.forField(txt_source_id).asRequired("Enter Source ID").bind(Validation::getString,Validation::setString);

		binder.forField(com_port).asRequired("Select COM Port").bind(Validation::getString,Validation::setString);
		binder.forField(baud).asRequired("Select Baud Rate").bind(Validation::getString,Validation::setString);
		binder.forField(data_bits).asRequired("Select Application").bind(Validation::getString,Validation::setString);
		//binder.forField(protocol_type).asRequired("Select Protocol").bind(Validation::getString,Validation::setString);

		binder.forField(stop_bits).asRequired("Select Stop Bits").bind(Validation::getString,Validation::setString);
		binder.forField(parity).asRequired("Select Parity").bind(Validation::getString,Validation::setString);
		binder.forField(polling_interval).asRequired("Select Polling Interval").bind(Validation::getString,Validation::setString);
		binder.forField(report_interval).asRequired("Select Report Interval").bind(Validation::getString,Validation::setString);
		binder.forField(polling_set_time).asRequired("Select Polling  Set Time").bind(Validation::getString,Validation::setString);
		binder.forField(report_set_time).asRequired("Select Reporting Set Time").bind(Validation::getString,Validation::setString);

		
		update();
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		
		Grid.Column<SourceModbusRTUTable> Raw_Material = grid.addColumn(SourceModbusRTUTable::getSource_id).setHeader("Source ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceModbusRTUTable> sap_id = grid.addColumn(SourceModbusRTUTable::getSource_name).setHeader("Source Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceModbusRTUTable> scada_id = grid.addColumn(SourceModbusRTUTable::getCom_port).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<SourceModbusRTUTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
							service.deleteSourcePort(sourcedata.getId());
							//eti.deleteById(TagTableData.getId());
							
							UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();
						btn_save.setEnabled(true);	
						//update();

					});
					Button cancelButton = new Button("Cancel", event -> {
						dialog.close();
					});

					dialog.add(new HorizontalLayout(confirmButton, cancelButton));

					dialog.open();
				
			});
			return delete;
		}).setHeader("Delete").setTextAlign(ColumnTextAlign.CENTER);
		
		
		
		if(!para.equals("0"))
        {
			for(SourceTable stt:service.source_list_id(main_id))
			{
				
			}
        }
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				String source_id="",source_name="",com="",baud_rate="",data="",stop="",parity_val="",poll_int="",rep_int="",poll_set_time="",rep_set_time="";
//				source_id = txt_source_id.getValue().toString().split("-")[0];
//				source_name=txt_source_id.getValue().toString().split("-")[1];
			
				source_id = txt_source_id.getValue().toString();
				source_name =service.get_Source_name(source_id);
						
				if(service.check_source_modbus_rtu(source_id)) {
					
					Notification.show("Already Source has been saved ...!");
					
				}else {
					
						
						
						
						com = com_port.getValue().toString();
						baud_rate = baud.getValue().toString();
						data = data_bits.getValue().toString();
						stop = stop_bits.getValue().toString();
						parity_val = parity.getValue().toString();
						poll_int =  polling_interval.getValue().toString();
						rep_int =  report_interval.getValue().toString();
						poll_set_time=polling_set_time.getValue().toString();
						rep_set_time=report_set_time.getValue().toString();
												
						SourceModbusRTUTable st = new SourceModbusRTUTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setCom_port(com);
						st.setBaud(baud_rate);
						st.setData_bits(data);
						st.setStop_bits(stop);
						st.setParity(parity_val);
						st.setPoll_interval(poll_int);
						st.setRep_interval(rep_int);
						st.setPolling_set_time(poll_set_time);
						st.setReporting_set_time(rep_set_time);
						st.setRowid(genUniqueID());
						service.source_modbus_rtu_save(st);
						
						service.update_protocol(source_id,"MODBUS RTU");
						
						if(service.check_source_tcp(source_id)) {
							service.deleteSource_modbus_tcp(source_id);
						}
                       if(service.check_source_opcua(source_id)) {
							
							
							service.deleteSource_opcua(source_id);
						}
                       if(service.check_source_mqtt(source_id)) {
							
							
							service.deleteSource_mqtt(source_id);
						}
					
						
						Notification.show("Source and port has been mapped sucessfully...!");
						btn_save.setEnabled(false);
						UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());

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
				
				String source_id = txt_source_id.getValue().toString().split("-")[0];
				if(service.check_source_modbus_rtu(source_id))
        		{
					UI.getCurrent().navigate( SourceTagMappingForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
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
			String id =src_id.split("-")[0];
			if(service.check_source_modbus_rtu(id)) {
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
		   btn_layout.add(btn_back,btn_save);
		}else {
			String id =src_id.split("-")[0];
			if(service.check_source_modbus_rtu(id) ) {
			   btn_layout.add(btn_back,btn_save, btn_next,btn_skip);
			}else {
				btn_layout.add(btn_back,btn_save);
			}
		}
		btn_poll_layout.removeAll();
		btn_poll_layout.add(polling_interval,polling_set_time);
		btn_rep_layout.add(report_interval,report_set_time);
		
		formLayout.removeAll();
		
		formLayout.add( txt_source_id, com_port, baud, data_bits, stop_bits, 
				parity, btn_poll_layout,btn_rep_layout, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		remove(grid);		
		add(grid);
		
	}
	public void update() {

		List<SourceModbusRTUTable> list = null;
		
		list = service.source_port_list();
		dataProvider = new ListDataProvider<>(list);
		grid.removeAllColumns();
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
