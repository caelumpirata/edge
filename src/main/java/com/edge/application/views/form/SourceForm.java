package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.edge.PlatformView;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Source Form")
@Route(value = "sourceform" ,layout = MainLayout.class)
public class SourceForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="sourceform";
	
	TextField txt_source_id     = new TextField("Source Id");
	TextField txt_source_name   = new TextField("Source Name");
	TextField txt_longtitude    = new TextField("Longitude");
	TextField txt_latitude      = new TextField("Latitude");
	TextField txt_location_name = new TextField("Location Name");
	
	ComboBox<String> com_application = new ComboBox<>("Application Name");
	ComboBox<String> protocol_type = new ComboBox<>("Protocol Type");
	
	DatePicker date_install  = new DatePicker("Install Date");
	DatePicker date_modified = new DatePicker("Modified Date");
	
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;
	String protocol_type_str   = "";
	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
	    main_id=Long.parseLong(para);
	    
        com_application.setAllowCustomValue(true); 
        com_application.setItems("EMS","UMS","AHU","CSM");
        
        
        
        protocol_type.setAllowCustomValue(true); 
        protocol_type.setItems("");
    
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (SourceTable sou_table : service.source_id_next()) {

			getid = sou_table.getSource_id().toString();
		}

		if (!getid.equals("")) {
			int_id = Integer.parseInt(getid);
			int_id = int_id + 1;
			if (int_id < 10) {

				final_id = "000" + int_id;
			} else {
				final_id = "00" + int_id;
			}
		} else {
			int_id = 1;
			if (int_id < 10) {

				final_id = "000" + int_id;
			} else {
				final_id = "00" + int_id;
			}
		}
		txt_source_id.setValue(final_id);
		txt_source_id.setReadOnly(true);
		
		
		// Validation code
		
		String ttt="";
		
		if(!para.equals("0"))
        {
			for(SourceTable stt:service.source_list_id(main_id))
			{
				txt_source_id.setValue(stt.getSource_id());
				txt_source_name.setValue(stt.getSource_name());
				txt_latitude.setValue(stt.getLatitude());
				txt_longtitude.setValue(stt.getLongtitude());
				txt_location_name.setValue(stt.getLocation_name());
				com_application.setValue(stt.getApplication_name());
				protocol_type.setValue(stt.getProtocol());
				protocol_type_str = stt.getProtocol();
				date_install.setValue(LocalDate.parse(stt.getImstall_date()));
				date_modified.setValue(LocalDate.parse(stt.getModifide_date()));
			}
        }
		

		String check_src_id =  txt_source_id.getValue().toString();
		if(!peUtil.isNullString(check_src_id)) {
			if(service.check_source_bacnet(check_src_id)) {
				 protocol_type.setItems("BACNET");
				 protocol_type.setValue(protocol_type_str);
			}
			
			
			else {
				if(!service.check_source_bacnet(check_src_id)) {
					
						 protocol_type.setItems("MODBUS RTU","MODBUS TCP","OPCUA","MQTT");
						 protocol_type.setValue(protocol_type_str);
					
//					 else {
//					 protocol_type.setItems("MODBUS RTU","MODBUS TCP","BACNET","OPCUA");
//					 protocol_type.setValue(protocol_type_str);
//					 }
				}
				
				else {
					 protocol_type.setItems("MODBUS RTU","MODBUS TCP","OPCUA","MQTT");
					 protocol_type.setValue(protocol_type_str);
				}
			}
			 
		}else {
			 protocol_type.setItems("BACNET");
			 protocol_type.setValue(protocol_type_str);
		}
		
	
		if(para.equals("0"))
        {	
			 protocol_type.setItems("MODBUS RTU","MODBUS TCP","BACNET","OPCUA","MQTT");
		binder.forField(txt_source_id).asRequired("Enter Source ID").bind(Validation::getString,Validation::setString);
        }
		binder.forField(txt_source_name).asRequired("Enter Source Name").bind(Validation::getString,Validation::setString);
		binder.forField(com_application).asRequired("Select Application").bind(Validation::getString,Validation::setString);
		binder.forField(protocol_type).asRequired("Select Protocol").bind(Validation::getString,Validation::setString);

		binder.forField(txt_longtitude).asRequired("Enter Longtitude").bind(Validation::getString,Validation::setString);
		binder.forField(txt_latitude).asRequired("Enter Latitude").bind(Validation::getString,Validation::setString);
		binder.forField(txt_location_name).asRequired("Enter Location Name").bind(Validation::getString,Validation::setString);
		binder.forField(date_install).asRequired("Enter Install Date").bind(Validation::getFromDate,Validation::setFromDate);
		binder.forField(date_modified).asRequired("Enter Modified Date").bind(Validation::getFromDate,Validation::setFromDate);
		
		
	
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					if(para.equals("0"))
            		{
						
						if(!service.check_Source(txt_source_name.getValue()))
		        		{
						String source_id="",source_name="",app_name="",longtitude="",latitude="",location="",instal_date="",modi_date="",proto_type="";
						
						proto_type   = protocol_type.getValue().toString();
						if(!proto_type.equalsIgnoreCase("BACNET")) {
							source_id    = txt_source_id.getValue().toString();
						}
						
						source_name  = txt_source_name.getValue().toString();
						app_name     = com_application.getValue().toString();
						proto_type   = protocol_type.getValue().toString();
						longtitude   = txt_longtitude.getValue().toString();
						latitude     = txt_latitude.getValue().toString();
						location     = txt_location_name.getValue().toString();
						instal_date  = date_install.getValue().toString();
						modi_date    = date_modified.getValue().toString();
						
						SourceTable st = new SourceTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setApplication_name(app_name);
						st.setProtocol(proto_type);
						st.setLongtitude(longtitude);
						st.setLatitude(latitude);
						st.setLocation_name(location);
						st.setImstall_date(instal_date);
						st.setModifide_date(modi_date);
						st.setRowid(genUniqueID());
						service.source_save(st);
						Notification.show("Source has been saved sucessfully...!");
						btn_save.setEnabled(false);
						//UI.getCurrent().navigate(SourceList.class);
		        		}// source check if condition
						else
						{
							Notification.show("Source Name Already In A Record...!");
						}
            		} // paara check equals 0 then insert or 1 then edit recored
					else
					{
                       String source_id="",source_name="",app_name="",longtitude="",latitude="",location="",instal_date="",modi_date="",proto_type="";
						
						source_id    = txt_source_id.getValue().toString();
						source_name  = txt_source_name.getValue().toString();
						app_name     = com_application.getValue().toString();
						proto_type   = protocol_type.getValue().toString();
						longtitude   = txt_longtitude.getValue().toString();
						latitude     = txt_latitude.getValue().toString();
						location     = txt_location_name.getValue().toString();
						instal_date  = date_install.getValue().toString();
						modi_date    = date_modified.getValue().toString();
						
						SourceTable st = new SourceTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setApplication_name(app_name);
						st.setProtocol(proto_type);
						st.setLongtitude(longtitude);
						st.setLatitude(latitude);
						st.setLocation_name(location);
						st.setImstall_date(instal_date);
						st.setModifide_date(modi_date);
						st.setId(main_id);
						st.setRowid(genUniqueID());
						service.source_save(st);
						
						Notification.show("Source has been updated sucessfully...!");
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
				String src_id=txt_source_id.getValue().toString();
				
				protocol_type_str    = protocol_type.getValue().toString();
				
			
				if(protocol_type_str.equalsIgnoreCase("BACNET")) {
					if(para.equals("0"))
            		{
					  UI.getCurrent().navigate( BacnetProtocolForm.ROUTE_NAME+ "/0" );
            		}else {
            			if(!peUtil.isNullString(src_id)) {
            			   UI.getCurrent().navigate( BacnetProtocolForm.ROUTE_NAME+ "/" + src_id);
            			}else {
            				UI.getCurrent().navigate( BacnetProtocolForm.ROUTE_NAME+ "/" + main_id );
            			}
            		}

				}
				else {
//				if(service.check_Source_id(src_id))
//        		{
//					UI.getCurrent().navigate( PlatformView.ROUTE_NAME+ "/" + src_id+"-"+txt_source_name.getValue().toString());
//					//UI.getCurrent().navigate(SourceForm.ROUTE_NAME + "/" + src_id);
//        		}else {
//        			Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
//        		}
					
					if(protocol_type_str.equalsIgnoreCase("MODBUS RTU"))
					{
						if(!peUtil.isNullString(src_id)) {
	            			   UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" + src_id);
	            			}else {
	            				UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" + main_id );
	            			}
					}
					else if(protocol_type_str.equalsIgnoreCase("MODBUS TCP"))
					{
						if(!peUtil.isNullString(src_id)) {
	            			   UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + src_id);
	            			}else {
	            				UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + main_id );
	            			}
					}
					else if(protocol_type_str.equalsIgnoreCase("OPCUA"))
					{
						if(!peUtil.isNullString(src_id)) {
	            			   UI.getCurrent().navigate( OpcuaProtocolForm.ROUTE_NAME+ "/" + src_id);
	            			}else {
	            				UI.getCurrent().navigate( OpcuaProtocolForm.ROUTE_NAME+ "/" + main_id );
	            			}
					}
					else if(protocol_type_str.equalsIgnoreCase("MQTT"))
					{
						if(!peUtil.isNullString(src_id)) {
	            			   UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" + src_id);
	            			}else {
	            				UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" + main_id );
	            			}
					}
					else 
					{
	        			Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
	        		}
				}
			}// biner if condition
			else
			{
				Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( txt_source_id, txt_source_name, com_application, txt_longtitude, txt_latitude, 
							txt_location_name,protocol_type, date_install, date_modified, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		
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
