package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.OPCUA.OPCUAandIMmappingForm;
import com.edge.application.views.Table.SourceOpcuaTable;
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
import com.vaadin.flow.component.notification.NotificationVariant;
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


@PageTitle("OPCUA Protocol Form")
@Route(value = "opcuaprotocol" ,layout = MainLayout.class)
public class OpcuaProtocolForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="opcuaprotocol";
	
	TextField txt_source_id     = new TextField("Source Id");
	TextField txt_server_name     = new TextField("Server Name");
	TextField txt_end_point_url     = new TextField("End Point Url");
	TextField txt_port    = new TextField("Port");
	TextField txt_user_name    = new TextField("Username");
	TextField txt_password    = new TextField("Password");
	TextField txt_certficate    = new TextField("Certificate");
	TextField txt_key    = new TextField("Key");
	
	ComboBox<String> security_policy = new ComboBox<>("Security Policy");
	ComboBox<String> security_setting = new ComboBox<>("Security Setting");
		
	
	Button btn_back = new Button("Back");
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	Grid<SourceOpcuaTable> grid = new Grid<SourceOpcuaTable>();
	ListDataProvider<SourceOpcuaTable> dataProvider;
	Dialog dialog_opc = new Dialog(); //22-11-2022
	VerticalLayout centerButton_opc = new VerticalLayout(); //22-11-2022
	Editor<SourceOpcuaTable> editor_opc;
	SourceOpcuaTable opc_table = new SourceOpcuaTable();
	Binder<SourceOpcuaTable> binder_opc     = new Binder<SourceOpcuaTable>();
	
	IFrame fi = new IFrame();
	
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
		
		fi.getElement().setAttribute("frameBorder", "0");
		fi.setSizeFull();
		setSpacing(false);
		fi.setWidthFull();
		fi.setHeightFull();
		setHeightFull();
		
		security_policy.setAllowCustomValue(true); 
		security_policy.setItems("None","Basic 128 Rsa 15","Basic 256");
	        
	        
	        
	        security_setting.setAllowCustomValue(true); 
	        security_setting.setItems("Anonymous","User Password","Certificate Private File");
	        
	        txt_user_name.setVisible(false);
	        txt_password.setVisible(false);
	        
	        txt_certficate.setVisible(false);
	        txt_key.setVisible(false);
	        
	       
		
	    
        String src_id=""+para;
        
        txt_source_id.setValue(src_id);
		txt_source_id.setReadOnly(true);
    
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		String sec_set ="";	

//		if(!peUtil.isNullString(src_id)) {
//			
//			String id =src_id.split("-")[0];
//			
//			for(SourceOpcuaTable stt:service.list_source_opcua(id))
//			{
//				
//				txt_server_name.setValue(stt.getServer_name());
//				txt_end_point_url.setValue(stt.getEnd_point_url());
//				//txt_user_name.setValue(stt.getUsername());
//				//txt_password.setValue(stt.getPassword());
//				//txt_certficate.setValue(stt.getCertificate());
//				//txt_key.setValue(stt.getKey());
//				txt_port.setValue(stt.getPort());
//				security_policy.setValue(stt.getSecurityPolicy());
//				
//				 sec_set = stt.getSecuritySetting();
//				 
//				 security_setting.setValue(sec_set);
//				if(!peUtil.isNullString(sec_set)) {
//					if(sec_set.equalsIgnoreCase("User Password")) {
//						txt_user_name.setVisible(true);
//				        txt_password.setVisible(true);
//				        txt_user_name.setValue(stt.getUsername());
//				        txt_password.setValue(stt.getPassword());
//						
//					}
//					if(sec_set.equalsIgnoreCase("Certificate Private File")) {
//						 txt_certficate.setVisible(true);
//					     txt_key.setVisible(true);
//					     txt_certficate.setValue(stt.getCertificate());
//				        txt_password.setValue(stt.getKey());
//						
//					}
//					
//				}
//				
//			}
//        }
		
		
		
		
		// Validation code
		binder.forField(txt_source_id).asRequired("Enter Source ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_server_name).asRequired("Enter Server Name").bind(Validation::getString,Validation::setString);
		binder.forField(txt_end_point_url).asRequired("Entert Endpoint URL").bind(Validation::getString,Validation::setString);
		binder.forField(txt_port).asRequired("Entert Port").bind(Validation::getString,Validation::setString);
		binder.forField(security_policy).asRequired("Select  Security Policy").bind(Validation::getString,Validation::setString);

		btn_next.setEnabled(true);
		
		
		security_setting.addValueChangeListener(event -> {
			
			
			String sec_setting = security_setting.getValue().toString();
			if(!peUtil.isNullString(sec_setting)) {
				if(sec_setting.equalsIgnoreCase("User Password")) {
					txt_user_name.setVisible(true);
			        txt_password.setVisible(true);
			       
			        txt_certficate.setVisible(false);
				    txt_key.setVisible(false);
//			        binder.forField(txt_user_name).asRequired("Enter Username").bind(Validation::getString,Validation::setString);
//	    			binder.forField(txt_password).asRequired("Enter password").bind(Validation::getString,Validation::setString);

					
				}
				if(sec_setting.equalsIgnoreCase("Certificate Private File")) {
					txt_certficate.setVisible(true);
				    txt_key.setVisible(true);
				
				    txt_user_name.setVisible(false);
			        txt_password.setVisible(false);
//				    binder.forField(txt_certficate).asRequired("Enter Certificate").bind(Validation::getString,Validation::setString);
//	    			binder.forField(txt_key).asRequired("Enter Key").bind(Validation::getString,Validation::setString);
					
				}
				if(sec_setting.equalsIgnoreCase("Anonymous"))
				{
					txt_user_name.setVisible(false);
			        txt_password.setVisible(false);
			        txt_certficate.setVisible(false);
				    txt_key.setVisible(false);
				}
				
			}
			
			});

		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				String source_id="",source_name="",server_name="",port="",end_point="",policy="",setting="",user="",password="",certificate="",key="";
				source_id = txt_source_id.getValue().toString();
				source_name =service.get_Source_name(source_id);
				server_name=txt_server_name.getValue().toString();
				end_point=txt_end_point_url.getValue().toString();
				policy=security_policy.getValue().toString();
				setting=security_setting.getValue().toString();
				
				if(!peUtil.isNullString(setting)) {
					if(setting.equalsIgnoreCase("User Password")) {
						user = txt_user_name.getValue();
				        password=txt_password.getValue().toString();
						
						
					}
					if(setting.equalsIgnoreCase("Certificate Private File")) {
						
					    certificate=txt_certficate.getValue().toString();
					    key = txt_key.getValue().toString();
				       
						
					}
					
				}
				
				if(service.check_source_opcua(source_id)) {
					
					Notification.show("Already Source has been saved ...!");
					
				}else {
					
						
						
						
						
						port = txt_port.getValue().toString();
						
												
						SourceOpcuaTable st = new SourceOpcuaTable();
						st.setSource_id(source_id);
						st.setSource_name(source_name);
						st.setPort(port);
						st.setEnd_point_url(end_point);
						st.setServer_name(server_name);
						st.setSecurityPolicy(policy);
						st.setSecuritySetting(setting);
						if(!peUtil.isNullString(setting)) {
							if(setting.equalsIgnoreCase("User Password")) {
								st.setUsername(user);
								st.setPassword(password);
								
							}
							if(setting.equalsIgnoreCase("Certificate Private File")) {
														    
							    st.setCertificate(certificate);
								st.setKey(key);
						       
								
							}
							
						}
						
						st.setRowid(genUniqueID());
						
						service.source_opcua_save(st);
						
						service.update_protocol(source_id,"OPCUA");
						
						
						
						if(service.check_source_modbus_rtu(source_id)) {
							
							
							service.deleteSource_modbus_rtu(source_id);
						}
						
						if(service.check_source_tcp(source_id)) {
							service.deleteSource_modbus_tcp(source_id);
						}
						  if(service.check_source_mqtt(source_id)) {
								
								
								service.deleteSource_mqtt(source_id);
							}
						
						Notification.show("Source and OPCUA has been mapped sucessfully...!");
						
						
						btn_next.setEnabled(true);
						//btn_save.setEnabled(false);
						btn_skip.setEnabled(true);
						 
						formLayout.removeAll();
						btn_layout.removeAll();
						btn_layout.add(btn_back,btn_next,btn_skip);
						formLayout.add(  btn_layout );
							
						//UI.getCurrent().navigate( OpcuaProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
						
						
						fi.setSrc("opcview/"+src_id);
						removeAll();
						add(formLayout,grid,fi);
						update();
						
				}
            	
					
        		
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
		
		
		btn_next.addClickListener(s->{
			
			String id =src_id;
			if(service.check_source_opcua(id)) {
//				UI.getCurrent().navigate(OpcuaEditTreeForm.ROUTE_NAME + "/"+src_id);
				UI.getCurrent().navigate(OPCUAandIMmappingForm.ROUTE_NAME + "/"+src_id);
			}
		});
				
		btn_skip.addClickListener(e -> {
			String id =src_id;
			if(service.check_source_opcua(id)) {
//				UI.getCurrent().navigate(OpcuaEditTreeForm.ROUTE_NAME + "/"+src_id);
				UI.getCurrent().navigate(OPCUAandIMmappingForm.ROUTE_NAME + "/"+src_id);
			}
			
			
		});
		btn_back.addClickListener(e -> {
			
			String parameters=txt_source_id.getValue().toString();
			long id = service.get_main_source_id(parameters);
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/"+id);
		
		
		
	});		
		
		
		if(!service.check_sourceOPCUA(src_id))
		{
			formLayout.removeAll();
			formLayout.add( txt_source_id, txt_server_name,txt_end_point_url, txt_port,security_policy,security_setting,txt_user_name,txt_password,txt_certficate,txt_key, btn_layout );
			add(formLayout);
			 // layout add code
			btn_layout.removeAll();
			if(peUtil.isNullString(src_id)) {
			   btn_layout.add(btn_back,btn_save,btn_next);
			}else {
				String id =src_id;
				if(service.check_source_opcua(id) ) {
				   btn_layout.add(btn_back,btn_save,btn_next,btn_skip);
				}else {
					btn_layout.add(btn_back,btn_save,btn_next);
				}
			}
		
		}else {
			
			
			
				   btn_layout.add(btn_back,btn_next,btn_skip);
					formLayout.add(  btn_layout );
					add(formLayout);
			 // layout add code
			
			
		}
		
	
		
		
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		
		
		update();
		editor_opc = grid.getEditor();
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

//		grid.addComponentColumn(event -> {
//
//			Text id;
//			id = new Text("" + event.getId());
//			return id;
//		});
//		
//		Grid.Column<SourceOpcuaTable> source_id = grid.addColumn(SourceOpcuaTable::getSource_id).setHeader("Source ID")
//				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
//				.setTextAlign(ColumnTextAlign.CENTER);
//		
//		Grid.Column<SourceOpcuaTable> source_name = grid.addColumn(SourceOpcuaTable::getSource_name).setHeader("Source Name")
//				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
//				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceOpcuaTable> end_point = grid.addColumn(SourceOpcuaTable::getEnd_point_url).setHeader("End Point Url")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceOpcuaTable> port = grid.addColumn(SourceOpcuaTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceOpcuaTable> policy = grid.addColumn(SourceOpcuaTable::getSecurityPolicy).setHeader("Security Policy")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceOpcuaTable> setting = grid.addColumn(SourceOpcuaTable::getSecuritySetting).setHeader("Security Setting")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		TextField end_point_field = new TextField();
		binder_opc.forField(end_point_field).bind(SourceOpcuaTable::getEnd_point_url,SourceOpcuaTable::setEnd_point_url);
		end_point.setEditorComponent(end_point_field);
		
		TextField port_field = new TextField();
		binder_opc.forField(port_field).bind(SourceOpcuaTable::getPort,SourceOpcuaTable::setPort);
		port.setEditorComponent(port_field);
		
		ComboBox<String> policy_field = new ComboBox<String>();
		policy_field.setItems("None","Basic 128 Rsa 15","Basic 256");
		binder_opc.forField(policy_field).bind(SourceOpcuaTable::getSecurityPolicy, SourceOpcuaTable::setSecurityPolicy);
		policy.setEditorComponent(policy_field);
		
		ComboBox<String> setting_field = new ComboBox<String>();
		setting_field.setItems("Anonymous","User Password","Certificate Private File");
		binder_opc.forField(setting_field).bind(SourceOpcuaTable::getSecuritySetting, SourceOpcuaTable::setSecuritySetting);
		setting.setEditorComponent(setting_field);
		
		editor_opc.setBinder(binder_opc);
		Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
		Grid.Column<SourceOpcuaTable> editorColumn = grid.addComponentColumn(tData -> {
			Button edit = new Button("EDIT");
			edit.setIcon(new Icon(VaadinIcon.EDIT));
			edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//edit.addClassName("edit");
			edit.addClickListener(e -> {
				editor_opc.editItem(tData);
				end_point_field.focus();

			});
			edit.setEnabled(!editor_opc.isOpen());
			editButtons.add(edit);
			return edit;
		}).setHeader("Edit").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
		editor_opc.addOpenListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_opc.isOpen())));
		editor_opc.addCloseListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_opc.isOpen())));

		Button save = new Button("Save");
		save.addClassName("save");

		save.addClickListener(event -> {
			
			if (binder_opc.writeBeanIfValid(opc_table)) {
				if(service.opc_tree_check(end_point_field.getValue().toString()))
				{
					

					Dialog dialog = new Dialog();
					dialog.add(new Text("This Endpoint Url already Mapping in OPCUA Tree if you editing then again download OPCUA Tree."));
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);

					Button confirmButton = new Button("Confirm", event1 -> {
						try {
							service.update_opc(end_point_field.getValue().toString(),port_field.getValue().toString(),policy_field.getValue().toString(),setting_field.getValue().toString(),editor_opc.getItem().getId());
							service.opcua_delete(end_point_field.getValue().toString());
							
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();

						
						

					});
					Button cancelButton = new Button("Cancel", event1 -> {
						dialog.close();
						
					});

					dialog.add(new HorizontalLayout(confirmButton, cancelButton));

					dialog.open();
				}
				else
				{
					service.update_opc(end_point_field.getValue().toString(),port_field.getValue().toString(),policy_field.getValue().toString(),setting_field.getValue().toString(),editor_opc.getItem().getId());

					Notification notification = new Notification("Edit Action", 2000, Notification.Position.BOTTOM_CENTER);
					notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
					notification.open();
				}
				

				editor_opc.save();
			}
			editor_opc.closeEditor();
		});

		Button cancel = new Button("Cancel");
		cancel.addClassName("cancel");
		cancel.addClickListener(event -> {
			editor_opc.cancel();
		});

		grid.getElement().addEventListener("keyup", event -> editor_opc.cancel())
				.setFilter("event.key === 'Escape' || event.key === 'Esc'");

		Div buttons = new Div(save, cancel);

		save.getElement().setAttribute("padding-left", "20px");

		editorColumn.setEditorComponent(buttons);
    	// edit code end for grid
		

		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<SourceOpcuaTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

			// create edit button for each row
			Button delete = new Button("DELETE");

			// set icon
			delete.setIcon(new Icon(VaadinIcon.TRASH));

			// set theme
			delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			

			// on click operation
			delete.addClickListener(ed -> {

				
					Dialog dialog = new Dialog();
					dialog.add(new Text("Are you sure ! want to delete opcua protocol...?"));
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);

					Button confirmButton = new Button("Confirm", event -> {
						try {
							service.deleteSource_opcua(sourcedata.getId());
							service.dalete_url_live(sourcedata.getEnd_point_url());
							service.delete_sub_live(sourcedata.getEnd_point_url());
							//eti.deleteById(TagTableData.getId());

							formLayout.removeAll();
							formLayout.add( txt_source_id, txt_server_name,txt_end_point_url, txt_port,security_policy,security_setting,txt_user_name,txt_password,txt_certficate,txt_key, btn_layout );
							add(formLayout);
							 // layout add code
							btn_layout.removeAll();
							if(peUtil.isNullString(src_id)) {
							   btn_layout.add(btn_back,btn_save,btn_next);
							}else {
								String id =src_id;
								if(service.check_source_opcua(id) ) {
								   btn_layout.add(btn_back,btn_save,btn_next,btn_skip);
								}else {
									btn_layout.add(btn_back,btn_save,btn_next);
								}
							}
							remove(fi);
							remove(grid);
						
							//UI.getCurrent().navigate(OpcuaProtocolForm.ROUTE_NAME + "/" +txt_source_id.getValue().toString());
							
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
		
		//add(grid);
		
		if(service.check_sourceOPCUA(src_id))
		{
		
		fi.setSrc("opcview/"+src_id);
		remove(fi);
		add(grid,fi);
		}else {
			remove(grid,fi);
		}
		
	}
	public void update() {
		
		List<SourceOpcuaTable> list =null;
		list = service.source_opcua_list();
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
