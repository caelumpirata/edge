package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.MqttView.MQTTAndIMmappingForm;
import com.edge.application.views.Table.MqttTopicTreeTable;
import com.edge.application.views.Table.SourceMqttlTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
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

@PageTitle("Mqtt Protocol Form")
@Route(value = "MqttProtocolForm" ,layout = MainLayout.class)
public class MqttProtocolForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="MqttProtocolForm";
	
	TextField txt_source_id     = new TextField("Source Id");	
	TextField txt_broker_ip     = new TextField("Broker Name/IP like(tcp://45.32.115.95)");
	TextField txt_port   = new TextField("Port");
	TextField txt_user    = new TextField("Username");
	TextField txt_password      = new TextField("Password");
	TextField txt_client_id = new TextField("Client ID");
	
	ComboBox<String> com_qos = new ComboBox<>("Qos");
	ComboBox<String> com_keep_alive = new ComboBox<>("Keep Alive");
	ComboBox<String> com_protocol_version = new ComboBox<>("Protocol Version");
	ComboBox<String> com_clean_session = new ComboBox<>("Clean Sesssion");
	ComboBox<String> com_retaintion = new ComboBox<>("Retaintion");
	
	
	Button btn_back = new Button("Back");
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");
	Button btn_im = new Button("IM");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	Grid<SourceMqttlTable> grid = new Grid<SourceMqttlTable>();
	ListDataProvider<SourceMqttlTable> dataProvider;
	
	Editor<SourceMqttlTable> editor_mqtt;
	SourceMqttlTable mqtt_table = new SourceMqttlTable();
	Binder<SourceMqttlTable> binder_mqtt     = new Binder<SourceMqttlTable>();
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;
	IFrame fi = new IFrame();
	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
		 String src_id=""+para;
	          
		    fi.getElement().setAttribute("frameBorder", "0");
			fi.setSizeFull();
			setSpacing(false);
			fi.setWidthFull();
			fi.setHeightFull();
			setHeightFull();
	        txt_source_id.setValue(src_id);
			txt_source_id.setReadOnly(true);
	    
	    com_qos.setAllowCustomValue(true); 
	    com_qos.setItems("0","1","2");
	    
	    com_keep_alive.setAllowCustomValue(true); 
	    com_keep_alive.setItems("10","20","30","40","50");
	    
	    com_protocol_version.setAllowCustomValue(true); 
	    com_protocol_version.setItems("3.1.1","5.0");
	    
	    com_clean_session.setAllowCustomValue(true); 
	    com_clean_session.setItems("true","false");
	    
	    com_retaintion.setAllowCustomValue(true); 
	    com_retaintion.setItems("true","false");	    
	    
	    String id="";
	    
if(!peUtil.isNullString(src_id)) {
			
			 id =src_id;
			
			for(SourceMqttlTable stt:service.list_source_mqtt(id))
			{
				
				txt_broker_ip.setValue(stt.getBrokerIP());
				txt_port.setValue(stt.getPort());
				txt_user.setValue(stt.getUser_name());
				txt_password.setValue(stt.getPassword());
				txt_client_id.setValue(stt.getClient_id());
				com_qos.setValue(stt.getQos());
				com_keep_alive.setValue(stt.getKeepAlive());
				com_protocol_version.setValue(stt.getVersion());
				com_clean_session.setValue(stt.getSession());
				com_retaintion.setValue(stt.getRetaintion());
				
				
			}
        }
	    
        txt_source_id.setValue(src_id);
        txt_source_id.setReadOnly(true);
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		
		// Validation code
		binder.forField(txt_source_id).asRequired("Enter ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_broker_ip).asRequired("Enter Broker name/IP").bind(Validation::getString,Validation::setString);
		binder.forField(txt_port).asRequired("Enter Port").bind(Validation::getString,Validation::setString);
		binder.forField(com_qos).asRequired("Select Qos").bind(Validation::getString,Validation::setString);
		binder.forField(com_keep_alive).asRequired("Select Keep Alive").bind(Validation::getString,Validation::setString);
		binder.forField(com_protocol_version).asRequired("Select Protocol Version").bind(Validation::getString,Validation::setString);
		binder.forField(com_clean_session).asRequired("Select Clean Session").bind(Validation::getString,Validation::setString);
		binder.forField(com_retaintion).asRequired("Select Retaintion").bind(Validation::getString,Validation::setString);
	
		
		
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
						String source_id="",source_name="",broker_name="",port="",user="",password="",clientid="",qos="",keepalive="",version="",clean_session="",retaintion="";
						
						source_id = txt_source_id.getValue().toString();
						source_name =service.get_Source_name(source_id);
						broker_name  = txt_broker_ip.getValue().toString();
						port         = txt_port.getValue().toString();
						user         = txt_user.getValue().toString();
						password     = txt_password.getValue().toString();
						clientid     = txt_client_id.getValue().toString();
						qos          = com_qos.getValue().toString();
						keepalive    = com_keep_alive.getValue().toString();
						version      = com_protocol_version.getValue().toString();
						clean_session  = com_clean_session.getValue().toString();
						retaintion    = com_retaintion.getValue().toString();
						
						
						if(service.check_source_mqtt(source_id)) {
							
							Notification.show("Already Source has been saved ...!");
							
						}else {
							
							
							 
							 if(service.check_mqtt_url(broker_name, port,source_id)) {
								 service.deleteTopicTree_mqtt(broker_name, port,source_id);
							 }
						
										
						SourceMqttlTable st = new SourceMqttlTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setBrokerIP(broker_name);
						st.setPort(port);
						st.setUser_name(user);
						st.setPassword(password);
						st.setClient_id(clientid);
						st.setQos(qos);
						st.setKeepAlive(keepalive);
						st.setVersion(version);
						st.setSession(clean_session);
						st.setRetaintion(retaintion);
						st.setRowid(genUniqueID());
						service.source_mqtt_save(st);
						
						service.update_protocol(source_id,"MQTT");
						
                        if(service.check_source_modbus_rtu(source_id)) {
							
							
							service.deleteSource_modbus_rtu(source_id);
						}
						
						if(service.check_source_tcp(source_id)) {
							service.deleteSource_modbus_tcp(source_id);
						}
						
						 if(service.check_source_opcua(source_id)) {
								
								
								service.deleteSource_opcua(source_id);
						 }
						 
						 
						 if(service.check_mqtt_url(broker_name, port,source_id)) {
							 service.deleteTopicTree_mqtt(broker_name, port,source_id);
						 }
							 String broker =broker_name+":"+port;
						       String topic = "#";
						       //String username = "emqx";
						      // String password = "public";
						       String brok = broker_name;
						       String cid = "subscribe_client";
						       int qs = 0;

						       try {
						           @SuppressWarnings("resource")
								MqttClient client = new MqttClient(broker, cid, new MemoryPersistence());
						           // connect options
						           MqttConnectOptions options = new MqttConnectOptions();
						           options.setConnectionTimeout(60);
						           options.setKeepAliveInterval(60);
						           // setup callback
						           client.setCallback(new MqttCallback() {

						               public void connectionLost(Throwable cause) {
						                   System.out.println("connectionLost: " + cause.getMessage());
						              }

						               public void messageArrived(String topic, MqttMessage message) {
						                 String topic_name = topic;
						               String  source_id = txt_source_id.getValue().toString();
						 				String source_name =service.get_Source_name(source_id);
						                String port = txt_port.getValue().toString();
						                if(!service.check_mqtt_topic(topic_name, brok, port, source_id))
										 {
											 MqttTopicTreeTable mtt = new MqttTopicTreeTable();
							                 mtt.setUrl(brok);
							                 mtt.setPort(port);
							                 mtt.setSource_id(source_id);
							                 mtt.setSource_name(source_name);
							                 mtt.setTopic_name(topic_name);
							                 service.mqtt_tree_save(mtt);
										 }
						              }

						               public void deliveryComplete(IMqttDeliveryToken token) {
						                   System.out.println("deliveryComplete---------" + token.isComplete());
						              }

						          });
						           client.connect(options);
						           client.subscribe(topic, qs);
						           try {
						   			// wait to ensure subscribed messages are delivered
						   			Thread.sleep(6000);
						   			
						   			client.disconnect();
						   		} catch (Exception e) {
						   			e.printStackTrace();
						   		}
						      } catch (Exception e) {
						           e.printStackTrace();
						      }
						
						
						 
						Notification.show("Source and Mqtt has been mapped sucessfully...!");
						
						
						btn_next.setEnabled(true);
						btn_save.setEnabled(false);
						btn_skip.setEnabled(true);
						
						UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" + src_id);
						//UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
						//update(source_id);
						
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
				
				String source_id = txt_source_id.getValue();
				if(service.check_source_mqtt(source_id))
        		{
					UI.getCurrent().navigate( SourceList.class);
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
			
			String sd = txt_source_id.getValue().toString();
			if(service.check_source_mqtt(sd)) {
//				UI.getCurrent().navigate(MqttEditTopicForm.ROUTE_NAME+"/"+sd);
				UI.getCurrent().navigate(MQTTAndIMmappingForm.ROUTE_NAME + "/"+src_id);	
			}
			
			
		});
		btn_back.addClickListener(e -> {
			
			String parameters=txt_source_id.getValue().toString();
			long vv = service.get_main_source_id(parameters);
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/"+vv);
		
		
		
	});	
		btn_im.addClickListener(e -> {
//			UI.getCurrent().getPage().executeJavaScript("window.open('https://cat.terrestrialplatform.link/login', \"_blank\");");
			UI.getCurrent().navigate(MQTTAndIMmappingForm.ROUTE_NAME + "/"+src_id);
		});
				
		btn_layout.removeAll();
		formLayout.removeAll();
		if(peUtil.isNullString(src_id)) {
		   btn_layout.add(btn_back,btn_save);
		}else {
			 id =src_id;
			if(service.check_source_mqtt(id) ) {
			   btn_layout.add(btn_back,btn_skip,btn_im);
			}else {
				btn_layout.add(btn_back,btn_save);
			}
		}
		 // layout add code
		if(!service.check_source_mqtt(id) ) {
		formLayout.add( txt_source_id, txt_broker_ip, txt_port,txt_user,txt_password,txt_client_id, com_qos, com_keep_alive, 
				com_protocol_version, com_clean_session, com_retaintion, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		}else {
			formLayout.add(btn_layout)	;
			add(formLayout);
		}
		
		update(id);
		
		editor_mqtt = grid.getEditor();
		
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		
		Grid.Column<SourceMqttlTable> end_point = grid.addColumn(SourceMqttlTable::getBrokerIP).setHeader("End Point Url")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> port = grid.addColumn(SourceMqttlTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> user = grid.addColumn(SourceMqttlTable::getUser_name).setHeader("Username")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> pass = grid.addColumn(SourceMqttlTable::getPassword).setHeader("Password")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> client = grid.addColumn(SourceMqttlTable::getClient_id).setHeader("Client ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> qs = grid.addColumn(SourceMqttlTable::getQos).setHeader("Qos")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> keep = grid.addColumn(SourceMqttlTable::getKeepAlive).setHeader("Keep Alive")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> verson = grid.addColumn(SourceMqttlTable::getVersion).setHeader("Version")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> ses = grid.addColumn(SourceMqttlTable::getSession).setHeader("Session")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceMqttlTable> retain = grid.addColumn(SourceMqttlTable::getRetaintion).setHeader("Retaintion")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		TextField broker_field = new TextField();
		//broker_field.setReadOnly(true);
		binder_mqtt.forField(broker_field).bind(SourceMqttlTable::getBrokerIP,SourceMqttlTable::setBrokerIP);
		end_point.setEditorComponent(broker_field);
		
		TextField port_field = new TextField();
		//port_field.setReadOnly(true);
		binder_mqtt.forField(port_field).bind(SourceMqttlTable::getPort,SourceMqttlTable::setPort);
		port.setEditorComponent(port_field);
		
		
		TextField user_field = new TextField();
		binder_mqtt.forField(user_field).bind(SourceMqttlTable::getUser_name,SourceMqttlTable::setUser_name);
		user.setEditorComponent(user_field);
		
		TextField password_field = new TextField();
		binder_mqtt.forField(password_field).bind(SourceMqttlTable::getPassword,SourceMqttlTable::setPassword);
		pass.setEditorComponent(password_field);
		
		TextField client_field = new TextField();
		binder_mqtt.forField(client_field).bind(SourceMqttlTable::getClient_id,SourceMqttlTable::setClient_id);
		client.setEditorComponent(client_field);
		
		
		ComboBox<String> qos_field = new ComboBox<String>();
		qos_field.setItems("0","1","2");
		
		binder_mqtt.forField(qos_field).bind(SourceMqttlTable::getQos, SourceMqttlTable::setQos);
		qs.setEditorComponent(qos_field);
		
		ComboBox<String> keep_alive_field = new ComboBox<String>();
		keep_alive_field.setItems("10","20","30","40","50");
		binder_mqtt.forField(keep_alive_field).bind(SourceMqttlTable::getKeepAlive, SourceMqttlTable::setKeepAlive);
		keep.setEditorComponent(keep_alive_field);
		
		ComboBox<String> protocol_version_field = new ComboBox<String>();
		protocol_version_field.setItems("3.1.1","5.0");
		binder_mqtt.forField(protocol_version_field).bind(SourceMqttlTable::getVersion, SourceMqttlTable::setVersion);
		verson.setEditorComponent(protocol_version_field);
		
		ComboBox<String> session_field = new ComboBox<String>();
		session_field.setItems("true","false");
		binder_mqtt.forField(session_field).bind(SourceMqttlTable::getSession, SourceMqttlTable::setSession);
		ses.setEditorComponent(session_field);
		
		ComboBox<String> retaintion_field = new ComboBox<String>();
		retaintion_field.setItems("true","false");
		binder_mqtt.forField(retaintion_field).bind(SourceMqttlTable::getRetaintion, SourceMqttlTable::setRetaintion);
		retain.setEditorComponent(retaintion_field);
		
		
		
		editor_mqtt.setBinder(binder_mqtt);
		
		Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
		Grid.Column<SourceMqttlTable> editorColumn = grid.addComponentColumn(tData -> {
			Button edit = new Button("EDIT");
			edit.setIcon(new Icon(VaadinIcon.EDIT));
			edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//edit.addClassName("edit");
			edit.addClickListener(e -> {
				editor_mqtt.editItem(tData);
				broker_field.focus();

			});
			edit.setEnabled(!editor_mqtt.isOpen());
			editButtons.add(edit);
			return edit;
		}).setHeader("Edit").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
		editor_mqtt.addOpenListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_mqtt.isOpen())));
		editor_mqtt.addCloseListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_mqtt.isOpen())));

		Button save = new Button("Save");
		save.addClassName("save");

		save.addClickListener(event -> {
			
			if (binder_mqtt.writeBeanIfValid(mqtt_table)) {
				
				Dialog dialog = new Dialog();
				dialog.add(new Text("This Endpoint Url already Mapping in Mqtt Topic Tree if you editing then again download Mqtt Tree."));
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
			
					Button confirmButton = new Button("Confirm", event1 -> {
						try {
						//	service.update_opc(broker_field.getValue().toString(),port_field.getValue().toString(),policy_field.getValue().toString(),setting_field.getValue().toString(),editor_opc.getItem().getId());
						//	service.opcua_delete(broker_field.getValue().toString());
							
							
							
							String source_idd = txt_source_id.getValue().toString();
							String source_name =service.get_Source_name(source_idd);
							
							String url = broker_field.getValue();
							String port_val = port_field.getValue();
							
							 if(service.check_mqtt_url(url, port_val,source_idd)) {
								 
								 service.deleteTopicTree_mqtt(url, port_val,source_idd);
							 }
							
							
							
							SourceMqttlTable st = new SourceMqttlTable();
							st.setSource_id(source_idd);
							st.setSource_name(source_name);
							
							st.setBrokerIP(broker_field.getValue());
							st.setPort(port_field.getValue());
							st.setUser_name(user_field.getValue());
							st.setPassword(password_field.getValue());
							st.setClient_id(client_field.getValue());
							st.setQos(qos_field.getValue());
							st.setKeepAlive(keep_alive_field.getValue());
							st.setVersion(protocol_version_field.getValue());
							st.setSession(session_field.getValue());
							st.setRetaintion(retaintion_field.getValue());
							st.setId(service.perent_id_source_mqtt(source_idd));
							st.setRowid(genUniqueID());
							
						
							
							service.source_mqtt_save(st);	
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						
						String url = broker_field.getValue();
						String port_val = port_field.getValue();
						String source_idd =  src_id;
						 //if(service.check_mqtt_url(url, port_val,source_idd)) {
						//	 
						//	 service.deleteTopicTree_mqtt(url, port_val,source_idd);
						// }
							 String broker =url+":"+port_val;
						       String topic = "#";
						       //String username = "emqx";
						      // String password = "public";
						       String cid = "edit_subscribe_client";
						       int ques = 0;

						       try {
						           @SuppressWarnings("resource")
								MqttClient client1 = new MqttClient(broker, cid, new MemoryPersistence());
						           // connect options
						           MqttConnectOptions options = new MqttConnectOptions();
						           options.setConnectionTimeout(60);
						           options.setKeepAliveInterval(60);
						           // setup callback
						           client1.setCallback(new MqttCallback() {

						               public void connectionLost(Throwable cause) {
						                   System.out.println("connectionLost: " + cause.getMessage());
						              }

						               public void messageArrived(String topic, MqttMessage message) {
						                 String topic_name = topic;
						                 String source_id = txt_source_id.getValue().toString();
						 				 String source_name =service.get_Source_name(source_id);
						             
										 if(!service.check_mqtt_topic(topic_name, url, port_val, source_id))
										 {
											 MqttTopicTreeTable mtt = new MqttTopicTreeTable();
							                 mtt.setUrl(url);
							                 mtt.setPort(port_val);
							                 mtt.setSource_id(source_id);
							                 mtt.setSource_name(source_name);
							                 mtt.setTopic_name(topic_name);
							                 service.mqtt_tree_save(mtt);
										 }
										 

						              }

						               public void deliveryComplete(IMqttDeliveryToken token) {
						                   System.out.println("deliveryComplete---------" + token.isComplete());
						              }

						          });
						           client1.connect(options);
						           client1.subscribe(topic, ques);
						           try {
						   			// wait to ensure subscribed messages are delivered
						   			Thread.sleep(6000);
						   			
						   			client1.disconnect();
						   		} catch (Exception e) {
						   			e.printStackTrace();
						   		}
						      } catch (Exception e) {
						           e.printStackTrace();
						      }
					
						
						
						dialog.close();
						
						

					});
					Button cancelButton = new Button("Cancel", event1 -> {
					
						dialog.close();
					});

					dialog.add(new HorizontalLayout(confirmButton, cancelButton));

					dialog.open();
			
				

				editor_mqtt.save();
			}
			editor_mqtt.closeEditor();
		});

		Button cancel = new Button("Cancel");
		cancel.addClassName("cancel");
		cancel.addClickListener(event -> {
			editor_mqtt.cancel();
		});

		grid.getElement().addEventListener("keyup", event -> editor_mqtt.cancel())
				.setFilter("event.key === 'Escape' || event.key === 'Esc'");

		Div buttons = new Div(save, cancel);

		save.getElement().setAttribute("padding-left", "20px");

		editorColumn.setEditorComponent(buttons);
		

		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<SourceMqttlTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

			// create edit button for each row
			Button delete = new Button("DELETE");

			// set icon
			delete.setIcon(new Icon(VaadinIcon.TRASH));

			// set theme
			delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			

			// on click operation
			delete.addClickListener(ed -> {

				
					Dialog dialog = new Dialog();
					dialog.add(new Text("Are you sure ! want to delete mqtt source protocol...?"));
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);

					Button confirmButton = new Button("Confirm", event -> {
						try {
							service.delete_source_mqtt(sourcedata.getId());
							service.delete_mqtt_entry_live(sourcedata.getSource_id());
							 service.deleteTopicTree_mqtt(sourcedata.getBrokerIP(), sourcedata.getPort(),sourcedata.getSource_id());
							service.mqtt_delete_live();
							btn_save.setEnabled(false);
							btn_save.setEnabled(true);
							btn_skip.setEnabled(false);
							//UI.getCurrent().navigate(ModbusTCPProtocolForm.ROUTE_NAME + "/" +txt_source_id.getValue().toString());
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();
						//btn_save.setEnabled(true);	
						//update( src_id.split("-")[0]);
						UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" + src_id);

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
		remove(fi);
		remove(grid);
		if(service.check_mqtt_tree_srcid(src_id.toString())) {
        fi.setSrc("mqttview/"+src_id);
           add(grid,fi);
		}else {
		
		   add(grid);
		}
		
		
	}
	public void update(String id) {
		
		List<SourceMqttlTable> list =null;
		list = service.list_source_mqtt(id);
		dataProvider = new ListDataProvider<>(list);
		
		grid.removeAllColumns();
		grid.setItems(list);

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
