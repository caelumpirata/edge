package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.KafkaConfigurationList;
import com.edge.application.views.Grid.MqttConfigurationList;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.Validation;
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

@PageTitle("Kafka Configuration Form")
@Route(value = "KafkaConfigurationForm" ,layout = MainLayout.class)
public class KafkaConfigurationForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="KafkaConfigurationForm";
	
	TextField txt_id     = new TextField("ID");	
	TextField txt_broker_ip     = new TextField("Broker Name/IP");
	TextField txt_port   = new TextField("Port");
	TextField txt_user    = new TextField("Username");
	TextField txt_password      = new TextField("Password");
	
	
	
	
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
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
	    main_id=Long.parseLong(para);
	    
	        
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (KafkaConfigurationTable kafka_table : service.kafka_list()) {

			getid = kafka_table.getKafka_id().toString();
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
		txt_id.setValue(final_id);
		txt_id.setReadOnly(true);
		
		
		// Validation code
		binder.forField(txt_id).asRequired("Enter ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_broker_ip).asRequired("Enter Broker name/IP").bind(Validation::getString,Validation::setString);
		binder.forField(txt_port).asRequired("Enter Port").bind(Validation::getString,Validation::setString);
		
	
		
		if(!para.equals("0"))
        {
			for(KafkaConfigurationTable stt:service.kafka_list_id(main_id))
			{
				txt_id.setValue(stt.getKafka_id());
				txt_broker_ip.setValue(stt.getBrokerIP());
				txt_port.setValue(stt.getPort());
				txt_user.setValue(stt.getUser_name());
				txt_password.setValue(stt.getPassword());
				
			}
        }
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				if(!service.check_Source(txt_broker_ip.getValue()))
        		{
					if(para.equals("0"))
            		{
						String id="",broker_name="",port="",user="",password="";
						
						id           = txt_id.getValue().toString();
						broker_name  = txt_broker_ip.getValue().toString();
						port         = txt_port.getValue().toString();
						user         = txt_user.getValue().toString();
						password     = txt_password.getValue().toString();
						
						
						
						KafkaConfigurationTable st = new KafkaConfigurationTable();
						st.setKafka_id(id);
						st.setBrokerIP(broker_name);
						st.setPort(port);
						st.setUser_name(user);
						st.setPassword(password);
						
						st.setRowid(genUniqueID());
						service.kafka_save(st);
						
						UI.getCurrent().navigate(KafkaConfigurationList.class);
            		} // paara check equals 0 then insert or 1 then edit recored
					else
					{
                       String id="",broker_name="",port="",user="",password="";
						
						id           = txt_id.getValue().toString();
						broker_name  = txt_broker_ip.getValue().toString();
						port         = txt_port.getValue().toString();
						user         = txt_user.getValue().toString();
						password     = txt_password.getValue().toString();
						
						
						
						KafkaConfigurationTable st = new KafkaConfigurationTable();
						st.setKafka_id(id);
						st.setBrokerIP(broker_name);
						st.setPort(port);
						st.setUser_name(user);
						st.setPassword(password);
						st.setId(main_id);
						st.setRowid(genUniqueID());
                        service.kafka_save(st);
						
						UI.getCurrent().navigate(KafkaConfigurationList.class);
					}
					
        		}// source check if condition
				else
				{
					Notification.show("Broker Name Already In A Record...!");
				}
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( txt_id, txt_broker_ip, txt_port,txt_user,txt_password, btn_layout );
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
