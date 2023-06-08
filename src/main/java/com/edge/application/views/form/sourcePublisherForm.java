package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.KafkaConfigurationList;
import com.edge.application.views.Grid.MqttConfigurationList;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Grid.SourcePublisherMappingList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.OPCUA.OPCUAandIMmappingForm;
import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.OPCUATagUtilityTable;
import com.edge.application.views.Table.SourceOpcuaTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.Table.sourcePublisherTable;
import com.edge.application.views.Table.userTable;
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
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Source Publisher Form")
@Route(value = "sourcePublisherForm" ,layout = MainLayout.class)
public class sourcePublisherForm extends HorizontalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="sourcePublisherForm";
	
	TextField txt_source_id     = new TextField("Source Id");
	ComboBox<String> source = new ComboBox<>("Source Id-Name");
	ComboBox<String> publisher = new ComboBox<>("Publisher");
	
	TextField txt_publisher_id     = new TextField("Publisher Id");
		
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("Finish");
	Button btn_skip = new Button("Finish");
	
	Dialog dialog = new Dialog(); //22-11-2022
	VerticalLayout centerButton = new VerticalLayout(); //22-11-2022
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	VerticalLayout grid_layout = new VerticalLayout();
	
	VerticalLayout v1 = new VerticalLayout();
	VerticalLayout v1_grid = new VerticalLayout();
	VerticalLayout v2_grid = new VerticalLayout();
	VerticalLayout main_v1= new VerticalLayout();
	
	HorizontalLayout ele_h1= new HorizontalLayout();
	
	HorizontalLayout v2_p = new HorizontalLayout();
	
	Button btn_publisher = new Button("Add Publisher");
	
	Button btn_back = new Button("BACK");
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	List<String> ls=new ArrayList<String>();
	
	String pub_type="";
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;

	Grid<KafkaConfigurationTable> grid = new Grid<KafkaConfigurationTable>();
	ListDataProvider<KafkaConfigurationTable> dataProvider=null;
	
	Grid<MqttConfigurationTable> grid1 = new Grid<MqttConfigurationTable>();
	ListDataProvider<MqttConfigurationTable> dataProvider1=null;
	
	Grid<sourcePublisherTable> grid2 = new Grid<sourcePublisherTable>();
	ListDataProvider<sourcePublisherTable> dataProvider2=null;
	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
	    String src_id=""+para;
	    
	  	   
	    publisher.setAllowCustomValue(true); 
	    publisher.setItems("");
	    publisher.setItems("MQTT","KAFKA");   
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		btn_next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
//		grid.setVisible(false);
//		grid1.setVisible(false);
		
		
		// source id autoincrement code
		
		btn_publisher.setWidthFull();
		btn_publisher.getElement().getStyle().set("margin-top", "36px");
		
		txt_publisher_id.setReadOnly(true);
		
		ls=new ArrayList<String>();
		if(peUtil.isNullString(src_id)) {
			for (SourceTable sou_table : service.source_id_list()) {
	
				String s_id = sou_table.getSource_id().toString();
				String s_name = sou_table.getSource_name().toString();
				String concnate = s_id+"-"+s_name;
				ls.add(concnate);
				
			}
		}else {
			String str =src_id+"-"+service.get_Source_name(src_id);
			//ls.add(str);
			txt_source_id.setValue(str);
			txt_source_id.setReadOnly(true);
			
			String pub_str = service.get_Publisher_type(src_id);
			publisher.setValue(pub_str);
			
			if(service.check_source_id_only(src_id)) {
			
		  	   txt_publisher_id.setValue(service.get_Publisher_id(src_id));
			}
		}

		source.setItems(ls);
		if(service.check_source_id_only(src_id)) {
		 pub_type = publisher.getValue().toString();
		}
		
		
	
		
		// Validation code

		if(peUtil.isNullString(src_id)) {
			binder.forField(source).asRequired("Select Source Id-Name").bind(Validation::getString,Validation::setString);
		}else {
			binder.forField(txt_source_id).asRequired("Source Id-Name").bind(Validation::getString,Validation::setString);

		}
			
			binder.forField(publisher).asRequired("Select Publisher").bind(Validation::getString,Validation::setString);
			binder.forField(txt_publisher_id).asRequired("Enter Publisher ID").bind(Validation::getString,Validation::setString);

	
		
		
			
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					
						String user_id_name="";String user_id="",user_name="",user_type="",modi_date="",instal_date="",p_id="";
						
						if(peUtil.isNullString(src_id)) {
						user_id_name =  source.getValue().toString();
						}else {
							user_id_name =  txt_source_id.getValue().toString();
						}
						user_id=user_id_name.split("-")[0];
						user_name  =user_id_name.split("-")[1];
											
						user_type   		= publisher.getValue().toString();
						p_id = txt_publisher_id.getValue().toString();
						
						
						if(service.check_source_id(user_name, user_id)) {
							
							long main_id=service.get_main_id(user_id);
							sourcePublisherTable st = new sourcePublisherTable();
							st.setSource_id(user_id);
							st.setSourceName(user_name);
							st.setPublisherType(user_type);
							st.setPublisherId(p_id);
							st.setRowid(genUniqueID());
							st.setId(main_id);
							service.source_pub_save(st);
							
						}
						else {	
							sourcePublisherTable st = new sourcePublisherTable();
							st.setSource_id(user_id);
							st.setSourceName(user_name);
							st.setPublisherType(user_type);
							st.setPublisherId(p_id);
							st.setRowid(genUniqueID());
							service.source_pub_save(st);
						}
							
		        		
						
						
                               update1(src_id,user_type);
                               btn_layout.removeAll();
       						if(service.check_Source_id_publisher(src_id)) {
       							btn_layout.add(btn_save, btn_skip);
       						}else {
       							btn_layout.add(btn_save);
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
				String id="";
				
				id    = txt_source_id.getValue().toString();
				if(service.check_Source_id_publisher(src_id))
        		{
					UI.getCurrent().navigate( SourceTagMappingForm.ROUTE_NAME+ "/" + id);
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
			if(service.check_Source_id_publisher(src_id)) {
				UI.getCurrent().navigate(SourceList.class);
			}
			
			
		});
		
		btn_publisher.addClickListener(ele->{
			
			String pub_type1 = publisher.getValue().toString();
			if(pub_type1.equalsIgnoreCase("MQTT")) {
				
				centerButton.removeAll();
				dialog.open();
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
				
				TextField txt_id     = new TextField("ID");	
				TextField txt_broker_ip     = new TextField("Broker Name/IP");
				TextField txt_port   = new TextField("Port");
				TextField txt_user    = new TextField("Username");
				TextField txt_password      = new TextField("Password");
				TextField txt_client_id = new TextField("Client ID");
				
				ComboBox<String> com_qos = new ComboBox<>("Qos");
				ComboBox<String> com_keep_alive = new ComboBox<>("Keep Alive");
				ComboBox<String> com_protocol_version = new ComboBox<>("Protocol Version");
				ComboBox<String> com_clean_session = new ComboBox<>("Clean Sesssion");
				ComboBox<String> com_retaintion = new ComboBox<>("Retaintion");
				
				Button btn_save = new Button("SAVE");
				Button btn_next = new Button("NEXT");
				
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
				    
			    
			        // btn style
					btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
					for (MqttConfigurationTable mqt_table : service.mqtt_id_list()) {

						getid = mqt_table.getMqtt_id().toString();
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
					binder.forField(com_qos).asRequired("Select Qos").bind(Validation::getString,Validation::setString);
					binder.forField(com_keep_alive).asRequired("Select Keep Alive").bind(Validation::getString,Validation::setString);
					binder.forField(com_protocol_version).asRequired("Select Protocol Version").bind(Validation::getString,Validation::setString);
					binder.forField(com_clean_session).asRequired("Select Clean Session").bind(Validation::getString,Validation::setString);
					binder.forField(com_retaintion).asRequired("Select Retaintion").bind(Validation::getString,Validation::setString);
				    
					btn_save.addClickListener(s->{
//						if (binder.writeBeanIfValid(entity)) 
//						{
							
							if(!service.check_broker_surce(txt_broker_ip.getValue(),txt_id.getValue()))
			        		{
								
									String id="",broker_name="",port="",user="",password="",clientid="",qos="",keepalive="",version="",clean_session="",retaintion="",p_id="";
									
									id           = txt_id.getValue().toString();
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
									
									
									MqttConfigurationTable st = new MqttConfigurationTable();
									st.setMqtt_id(id);
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
									service.mqtt_save(st);
									//UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+src_id);
									update(src_id,pub_type);
									btn_layout.removeAll();
									if(service.check_Source_id_publisher(src_id)) {
										btn_layout.add(btn_save, btn_skip);
									}else {
										btn_layout.add(btn_save);
									}
									dialog.close();
								
			        		}// source check if condition
							else
							{
								Notification.show("Broker IP Already In A Record...!");
							}
							
							
//						}// biner if condition
//						else
//						{
//							Notification.show("Action Denied", 3000, Position.MIDDLE);
//						}
						
					});
				

				Button can = new Button("Cancel", ev1 -> {
					
					//UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+src_id);
					dialog.close();
					
				});

				
				centerButton.add(txt_id, txt_broker_ip, txt_port,txt_user,txt_password,txt_client_id, com_qos, com_keep_alive, 
						com_protocol_version, com_clean_session, com_retaintion, btn_save);
				centerButton.setHorizontalComponentAlignment(Alignment.CENTER, txt_id, txt_broker_ip, txt_port,txt_user,txt_password,txt_client_id, com_qos, com_keep_alive, 
						com_protocol_version, com_clean_session, com_retaintion, btn_save);
				centerButton.setWidth("100%");

				centerButton.add(txt_id, txt_broker_ip, txt_port,txt_user,txt_password,txt_client_id, com_qos, com_keep_alive, 
						com_protocol_version, com_clean_session, com_retaintion, new HorizontalLayout(btn_save, can));
				dialog.add(centerButton);
				
			} /// close if fr mqtt
			
			if(pub_type1.equalsIgnoreCase("KAFKA")) {
				
				centerButton.removeAll();
				dialog.open();
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
				
				TextField txt_id     = new TextField("ID");	
				TextField txt_broker_ip     = new TextField("Broker Name/IP");
				TextField txt_port   = new TextField("Port");
				TextField txt_user    = new TextField("Username");
				TextField txt_password      = new TextField("Password");
				
				
				
				
				Button btn_save = new Button("SAVE");
				Button btn_next = new Button("NEXT");
				
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
				//binder.forField(txt_id).asRequired("Enter ID").bind(Validation::getString,Validation::setString);
				binder.forField(txt_broker_ip).asRequired("Enter Broker name/IP").bind(Validation::getString,Validation::setString);
				binder.forField(txt_port).asRequired("Enter Port").bind(Validation::getString,Validation::setString);
				btn_save.addClickListener(s->{
//					if (binder.writeBeanIfValid(entity)) 
//					{
						
						if(!service.check_broker_kafka(txt_broker_ip.getValue(),txt_id.getValue()))
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
								
								//UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+src_id);
								update(src_id,pub_type);
								dialog.close();
		            	
							
		        		}// source check if condition
						else
						{
							Notification.show("Broker Name Already In A Record...!");
						}
						
						
//					}// biner if condition
//					else
//					{
//						Notification.show("Action Denied", 3000, Position.MIDDLE);
//					}
					
				});
				
	            Button can = new Button("Cancel", ev1 -> {
					
					//UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+src_id);
					dialog.close();
					
				});

				
				centerButton.add(txt_id, txt_broker_ip, txt_port,txt_user,txt_password, btn_save);
				centerButton.setHorizontalComponentAlignment(Alignment.CENTER, txt_id, txt_broker_ip, txt_port,txt_user,txt_password, btn_save);
				centerButton.setWidth("100%");

				centerButton.add(txt_id, txt_broker_ip, txt_port,txt_user,txt_password, new HorizontalLayout(btn_save, can));
				dialog.add(centerButton);
				
			} // if close kafka dialog
			
			
		});
		
		update(src_id,pub_type);
		
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		Grid.Column<KafkaConfigurationTable> Raw_Material = grid.addColumn(KafkaConfigurationTable::getKafka_id).setHeader("Kafka ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<KafkaConfigurationTable> sap_id = grid.addColumn(KafkaConfigurationTable::getBrokerIP).setHeader("Broker IP")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<KafkaConfigurationTable> scada_ip = grid.addColumn(KafkaConfigurationTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		grid.addItemDoubleClickListener(e -> {
			txt_publisher_id.setValue(e.getItem().getKafka_id());
           
        });
		
		grid1.setWidthFull();
		grid1.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		
		Grid.Column<MqttConfigurationTable> aa = grid1.addColumn(MqttConfigurationTable::getMqtt_id).setHeader("MQtt ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<MqttConfigurationTable> bb = grid1.addColumn(MqttConfigurationTable::getBrokerIP).setHeader("Broker IP")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<MqttConfigurationTable> cc = grid1.addColumn(MqttConfigurationTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		grid1.addItemDoubleClickListener(e -> {
			txt_publisher_id.setValue(e.getItem().getMqtt_id());
           
        });
		
		
		grid2.setWidthFull();
		grid2.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		Grid.Column<sourcePublisherTable> opc_url = grid2.addColumn(sourcePublisherTable::getSource_id).setHeader("SourceID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		

		
		Grid.Column<sourcePublisherTable> opc_source = grid2.addColumn(sourcePublisherTable::getPublisherType).setHeader("Publisher Type")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<sourcePublisherTable> deleteColumn = grid2.addComponentColumn(sourcedata -> {

			// create edit button for each row
			Button delete = new Button("DELETE");

			// set icon
			delete.setIcon(new Icon(VaadinIcon.TRASH));

			// set theme
			delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			

			// on click operation
			delete.addClickListener(ed -> {

				
					Dialog dialog = new Dialog();
					dialog.add(new Text("Are you sure you want to delete Mapping...?"));
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
						update1(sourcedata.getSource_id(),sourcedata.getPublisherType());
						btn_layout.removeAll();
						if(service.check_Source_id_publisher(src_id)) {
							btn_layout.add(btn_save, btn_skip);
						}else {
							btn_layout.add(btn_save);
						}

						

					});
					Button cancelButton = new Button("Cancel", event -> {
						dialog.close();
					});

					dialog.add(new HorizontalLayout(confirmButton, cancelButton));

					dialog.open();
				
			});
			return delete;
		}).setHeader("Delete").setTextAlign(ColumnTextAlign.CENTER);
		
		
		
		btn_back.addClickListener(pdf->{
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME+"/"+src_id);
		});
		
		

		 // layout add code
		grid_layout.add(grid2);	
		ele_h1.add(publisher,btn_publisher);
		
		btn_layout.removeAll();
		if(service.check_Source_id_publisher(src_id)) {
			btn_layout.add(btn_save, btn_skip);
		}else {
			btn_layout.add(btn_save);
		}
		if(peUtil.isNullString(src_id)) {
			v1.add( source, ele_h1,txt_publisher_id, btn_layout,grid_layout );
		}else {
			v1.add( txt_source_id, ele_h1,txt_publisher_id, btn_layout,grid_layout );
		}
		
	
		publisher.addValueChangeListener(event -> {
			
			 pub_type = publisher.getValue().toString();
			 
			 //System.out.println("---sunil--pub_type========"+pub_type);
			// update(src_id,pub_type);
			
			 if(pub_type.equalsIgnoreCase("KAFKA")) {
					
					
				 			 
				 v1_grid.setVisible(true);
				 v2_grid.setVisible(false);
				
				
			}
			
			if(pub_type.equalsIgnoreCase("MQTT")) {
				
				 				
				 v1_grid.setVisible(false);
				 v2_grid.setVisible(true);
				
				
			}
			
			
		});
		
		
		 
		
		
		if(pub_type.equalsIgnoreCase("KAFKA")) {
			
			
			
			 
			v1_grid.setVisible(true);
			v2_grid.setVisible(false);
			
			
		}
		
		if(pub_type.equalsIgnoreCase("MQTT")) {
			
			
			
			v1_grid.setVisible(false);
			 v2_grid.setVisible(true);
			
			
		}
		
		v1_grid.add(grid);
		v2_grid.add(grid1);
		
		
		
		add(v1,v1_grid,v2_grid);	 
	
		//add(grid_layout);
		// delete end
	
		
		
		
		
		
		
	}
	public void update(String id,String type) {
		
		
			
			List<KafkaConfigurationTable> list =null;
			list = service.kafka_id_list();
			dataProvider = new ListDataProvider<>(list);
			//grid.removeAllColumns();
			grid.setItems(list);
			grid.setDataProvider(dataProvider);
			
			List<MqttConfigurationTable> list1 =null;
			list1 = service.mqtt_list();
			dataProvider1 = new ListDataProvider<>(list1);
			//grid1.removeAllColumns();
			grid1.setItems(list1);
			grid1.setDataProvider(dataProvider1);
			
			
			
			List<sourcePublisherTable> list2 =null;
			list2 = service.list_source_id(id);
			dataProvider2 = new ListDataProvider<>(list2);
			//grid1.removeAllColumns();
			grid2.setItems(list2);
			grid2.setDataProvider(dataProvider2);
			
	}
	
	public void update1(String id,String type) {
					
		List<sourcePublisherTable> list2 =null;
		list2 = service.list_source_id(id);
		dataProvider2 = new ListDataProvider<>(list2);
		//grid1.removeAllColumns();
		grid2.setItems(list2);
		grid2.setDataProvider(dataProvider2);
		
}

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
