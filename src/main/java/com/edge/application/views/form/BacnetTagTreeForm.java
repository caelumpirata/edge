package com.edge.application.views.form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.BACNET.BacnetAndIMmappingForm;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.BacnetCOVStatusTable;
import com.edge.application.views.Table.BacnetTreeTable;
import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.service.EdgeService;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.service.confirmed.SubscribeCOVRequest;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Boolean;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.IFrame;
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

@PageTitle("Bacnet Tag Tree Form")
@Route(value = "bacnettagtreeform" ,layout = MainLayout.class)
public class BacnetTagTreeForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="bacnettagtreeform";
	
	TextField txt_broadcast_ip     = new TextField("Broadcast IP");
	ComboBox<String> bacnet_instance_sever = new ComboBox<>("Bacnet Instance-Server Name");
	
	TextField txt_bacnet_server    = new TextField("Bacnet Instance-Server Name");
			
static	Button btn_save = new Button("Add Tag Tree");
static	Button btn_next = new Button("NEXT");
static	Button btn_back = new Button("Back");
static  Button btn_im = new Button("IM");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	List<String> ls=new ArrayList<String>();
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;
	
	public static LocalDevice localDevice;
	public static RemoteDevice d;
	public static List<ObjectIdentifier> oids;
	
	public static String global_instance_no="";
	public static String global_ip = "";

	IFrame fi = new IFrame();
	
	@Autowired
	private static EdgeService service;
	
	long main_id;
	
	public BacnetTagTreeForm(EdgeService service)
	{
		this.service=service;
	}
	
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
	    String global_var=""+para;
	   // System.out.println("Global variable...."+global_var);
	    fi.getElement().setAttribute("frameBorder", "0");
		fi.setSizeFull();
		setSpacing(false);
		fi.setWidthFull();
		fi.setHeightFull();
		setHeightFull();
		
		
	    bacnet_instance_sever.setAllowCustomValue(true); 
	    bacnet_instance_sever.setItems("");   
        
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
				
		if(peUtil.isNullString(global_var)) {
		for (SourceBacnetTable sou_table : service.bacnet_source_id()) {

			String instance = sou_table.getSource_id();
			String server = sou_table.getServer_name();
			String concnate = instance+"-"+server;
			ls.add(concnate);
			
		}
		}else {
			 
			for (SourceBacnetTable sou_table : service.bacnet_id_list(global_var)) {

				String instance = sou_table.getSource_id();
				String server = sou_table.getServer_name();
				String concnate = instance+"-"+server;
				txt_broadcast_ip.setValue(sou_table.getBroadcast_ip());
				txt_bacnet_server.setValue(concnate);
		
			}
			
			
			
		}
		
		txt_broadcast_ip.setReadOnly(true);
		txt_bacnet_server.setReadOnly(true);

		bacnet_instance_sever.setItems(ls);
		
		bacnet_instance_sever.addValueChangeListener(source->{
			
			String value=bacnet_instance_sever.getValue().toString();
			 if(!peUtil.isNullString(value)) {
			String source_id=value.split("-")[0];
			String server=value.split("-")[1];
			 String str = service.bacnet_broadcast_ip(source_id, server) ;
					 if(!peUtil.isNullString(str)) {
						 txt_broadcast_ip.setValue(str);
					 }
			 }
		
			
		});
	
		
		// Validation code

		if(peUtil.isNullString(global_var)) {
			binder.forField(bacnet_instance_sever).asRequired("Select Bacnet Instance-Server Name").bind(Validation::getString,Validation::setString);
			binder.forField(txt_broadcast_ip).asRequired(" Enter Broadcast IP").bind(Validation::getString,Validation::setString);

		}else {
			binder.forField(txt_bacnet_server).asRequired("Enter Bacnet Instance-Server Name").bind(Validation::getString,Validation::setString);
			binder.forField(txt_broadcast_ip).asRequired(" Enter Broadcast IP").bind(Validation::getString,Validation::setString);

		}

	
		
			
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{

				btn_save.setEnabled(false);
				btn_next.setEnabled(false);
				btn_back.setEnabled(false);
				
				
				String broadcast="",server_name="",instance_id="",server="";
                
				if(peUtil.isNullString(global_var)) {
				    server =bacnet_instance_sever.getValue().toString(); 
				}else {
					server =txt_bacnet_server.getValue().toString(); 
				}
				
				if(!peUtil.isNullString(server)) {
				instance_id =server.split("-")[0];
				server_name=server.split("-")[1];
				
				global_instance_no = instance_id;
				
				broadcast = txt_broadcast_ip.getValue().toString();
				
				global_ip = broadcast;
								
			
				IpNetwork network = new IpNetwork(broadcast,47808);
				Transport transport = new Transport(network);
				localDevice = new LocalDevice(123, transport);
				try {
					
					
						 localDevice.initialize();
						 localDevice.getEventHandler().addListener(new Listener());
						 localDevice.sendGlobalBroadcast(new WhoIsRequest());
					
					
					//localDevice.setRetries(0);
					
					
				       
					Thread.sleep(6000);
					
				
					//localDevice.terminate();
									
					 
					bacnet_instance_sever.setItems(ls);
					
				}catch(Exception eb){
						System.out.println("==Connection Exception===="+eb);
						Notification.show("==Connection Exception===="+eb, 3000, Position.MIDDLE);
						
				}
				}
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
		
    btn_back.addClickListener(e -> {
			
			UI.getCurrent().navigate(SourceList.class );
		
		
		
	});		
    btn_im.addClickListener(e -> {
//		UI.getCurrent().getPage().executeJavaScript("window.open('https://cat.terrestrialplatform.link/login', \"_blank\");");
    	UI.getCurrent().navigate(BacnetAndIMmappingForm.ROUTE_NAME+"/"+para);
    });
    btn_next.addClickListener(e -> {
		
    	UI.getCurrent().navigate(BacnetEditTreeForm.ROUTE_NAME + "/" + global_var);
	
	
	
});					
		
		 // layout add code
		btn_layout.add(btn_back,btn_save,btn_im);// btn_next
		if(peUtil.isNullString(global_var)) {
		formLayout.add( bacnet_instance_sever, txt_broadcast_ip, btn_layout );
		}else {
			formLayout.add( txt_bacnet_server, txt_broadcast_ip, btn_layout );
		}
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		
		fi.setSrc("bacnetview/"+global_var);
		add(formLayout,fi);
		
		
	}
	
	
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	static class Listener extends DeviceEventAdapter {
		
		@Autowired
	    @Override
	    public void iAmReceived(RemoteDevice d) {
	   try {
		   System.out.println("-----iAmReceived--------");
	            Address a = new Address(new Unsigned16(0), new OctetString(new byte[] { (byte) 0xc0, (byte) 0xa8, 0x1,
	                    0x5, (byte) 0xba, (byte) 0xc0 }));

	            @SuppressWarnings("unchecked")
				List<ObjectIdentifier> oids = ((SequenceOf<ObjectIdentifier>)
	            						 RequestUtils.sendReadPropertyAllowNull(
	            						localDevice, d, d.getObjectIdentifier(), 
	            						PropertyIdentifier.objectList)).getValues();
	          // System.out.println(oids); // all tree
	           
	            String server_name = getExtendedDeviceInformation(d);
				String str_device = oids.get(0).toString();
				String id_val[]=str_device.split(" ");
				String instance_no= peUtil.obj2str(id_val[1]);
				
				if(global_instance_no.equalsIgnoreCase(instance_no)) {
					
					if(service.check_server_bacnet(server_name, global_ip)) {
						service.truncate_bacnet_server(server_name, global_ip);
					}
					
					generateTree(d,instance_no,server_name,global_ip);
				}
				
				
	           
	           
				
	          
	        }
	        catch (BACnetException e) {
	            e.printStackTrace();
	        }
				}
	    
	    
		public void generateTree(RemoteDevice d,String dev_id,String server_name ,String ip) {
			
			
			
			@SuppressWarnings("unchecked")
			List<ObjectIdentifier> oids = null;
			try {
				oids = ((SequenceOf<ObjectIdentifier>)
											 RequestUtils.sendReadPropertyAllowNull(
											localDevice, d, d.getObjectIdentifier(), 
											PropertyIdentifier.objectList)).getValues();
			} catch (BACnetException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			
			Vector vc_obj_type = new Vector();
		    Vector vc_obj_name= new Vector();

		    Vector vc_cov= new Vector();
			Vector vc_source=new Vector();
			
			//long server_id=1;
			long parent_server=0;
			
			BacnetTreeTable btt = new BacnetTreeTable();
			//btt.setTree_id(server_id);
			btt.setIcon("tree-icon.png");
			btt.setText(server_name);
			btt.setParent(parent_server);
			btt.setServer_name(server_name);
			btt.setType("root");
			btt.setBroadcast_ip(ip);

			try {
				service.bacnet_tree_save(btt);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String device_name= getDeviceName(oids);
			   String device_idd=device_name.split(" ")[1];
			
			  // long device_id=2;
			  // long parent_device=1;
			   long parent_device=service.perent_id(server_name);

			    btt = new BacnetTreeTable();
				//btt.setTree_id(device_id);
				btt.setIcon("tag_folder.png");
				btt.setText(device_name);
				btt.setParent(parent_device);
				btt.setServer_name(server_name);
				btt.setType("device");
				btt.setDevice_id(device_idd);
				btt.setBroadcast_ip(ip);
			
				try {
					service.bacnet_tree_save(btt);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				   Vector Folder_name=new Vector();
				   Folder_name=getDeviceManeFolders(oids);
				   HashSet hs1 = new HashSet(Folder_name); 
				   Folder_name.clear();
				   Folder_name.addAll(hs1);
				  // long element_id=2;
				   long parent_element=service.perent_id(device_name);
				   
				   Vector vc_tag=new Vector();
				   
				   for(int i=0;i<Folder_name.size();i++)
				   {
					  // element_id++;
					   String element_name=Folder_name.get(i).toString();
					   
					    System.out.println(Folder_name.get(i).toString()+"==Value By ObjName==="+getObjNameByobjType(d,oids,Folder_name.get(i).toString())); // tag name
					   
						btt = new BacnetTreeTable();
						//btt.setTree_id(element_id);
						btt.setIcon("m_element.ico");
						btt.setText(element_name);
						btt.setParent(parent_element);
						btt.setServer_name(server_name);
						btt.setType("element");
						btt.setDevice_id(device_idd);
						btt.setBroadcast_ip(ip);
					
					
							try {
								service.bacnet_tree_save(btt);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						
						
						vc_tag=getObjNameByobjType(d,oids,Folder_name.get(i).toString());
						
						//long tag_id=element_id;
						long parent_tag=service.perent_id(element_name);
						
							for(int t=0;t<vc_tag.size();t++)
							{
								//tag_id++;
								String tag_name=vc_tag.get(t).toString();
								 System.out.println(Folder_name.get(i).toString()+"==Value By Source==="+getObjName(d,oids,tag_name)+"========"+tag_name); // tag name
								 String source=getObjName(d,oids,tag_name);
								 String obj_type_str = "";
								 String obj_type_id = "";
								 String[] arrSplit = source.split(" ");
								 
								 if(arrSplit.length==2) {
									 obj_type_str = ""+arrSplit[0];
									 obj_type_id = ""+arrSplit[1];
									 }
								 
								 else if(arrSplit.length==3) {
								 obj_type_str = ""+arrSplit[0]+" "+arrSplit[1];
								 obj_type_id = ""+arrSplit[2];
								 }
								 else if(arrSplit.length==4) {
									 
									 obj_type_str = ""+arrSplit[0]+" "+arrSplit[1]+" "+arrSplit[2];
									 obj_type_id = ""+arrSplit[3];
									 
								 }
								 else if(arrSplit.length==5) {
									 
									 obj_type_str = ""+arrSplit[0]+" "+arrSplit[1]+" "+arrSplit[2]+" "+arrSplit[3];
									 obj_type_id = ""+arrSplit[4];
									 
								 }
								 String service_type=getService(d,oids,obj_type_str,new Integer(obj_type_id));
								
								 
								btt = new BacnetTreeTable();
								//btt.setTree_id(tag_id);
								btt.setIcon("m_para.png");
								btt.setText(tag_name);
								btt.setParent(parent_tag);
								btt.setServer_name(server_name);
								btt.setSource(source);
								btt.setType("tag");
								btt.setService(service_type);
								btt.setDevice_id(device_idd);
								btt.setBroadcast_ip(ip);
								
								try {
									service.bacnet_tree_save(btt);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
								
							
							} // tag vector loop
				   
				   } // element vector loop 
				   
				   ///////////////
				   vc_source = service.getAllSource(device_idd.trim(),IpNetwork.DEFAULT_BROADCAST_IP,server_name);
				   if(!vc_source.isEmpty()) {
				  	   
				  	   int p=0;
				   
					     for(int v=0;v<vc_source.size();v++) {
					    	 
					    	 String str =peUtil.obj2str(vc_source.get(v));
					    	
					    	 
					    	 String obj_type_str = "";
						     String obj_type_id = "";
						    // Vector vc = peUtil.split(str, " ");
							String[] aaray1=str.split(" ");
							Vector vc =new Vector();
							for(int r=0;r<aaray1.length;r++)
							{
								//System.out.println("array::::"+aaray1[r]);
								vc.add(aaray1[r]);
							}
						     
						     if(!vc.isEmpty()) {
						    	 
						    	 if(vc.size()==2) {
									 obj_type_str = peUtil.obj2str(vc.get(0));
									 obj_type_id = peUtil.obj2str(vc.get(1));
									 }
								 
								 else if(vc.size()==3) {
								 obj_type_str = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1));
								 obj_type_id = peUtil.obj2str(vc.get(2));
								 }
								 else if(vc.size()==4) {
									 
									 obj_type_str = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2));
									 obj_type_id = peUtil.obj2str(vc.get(3));
									 
								 }
								 else if(vc.size()==5) {
									 
									 obj_type_str =peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2))+" "+peUtil.obj2str(vc.get(3));
									 obj_type_id = peUtil.obj2str(vc.get(4));
									 
								 }
							   	 
							   	ObjectIdentifier ai_local = null;      
							   	
							   	String obj_Name = obj_type_str;
							   	String t = obj_type_id;
							   	 ////////
							   	 if(obj_Name.equalsIgnoreCase("Analog Input")) {
							   		
							   		
							   		 String str_value = "";
							       		// str_value = getCOVIncrement(BacnetSave.d,oids,obj_Name,new Integer(t));
							       		 		                    
							          // if(str_value.length()<6) {
							           	
							           	 ai_local = new ObjectIdentifier(ObjectType.analogInput,Integer.parseInt(t));
							                 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							                 UnsignedInteger lifetime = new UnsignedInteger(p);
							                 SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							            
							                 try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
												 
												 
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                	
							                 }
							          // }
							   	 
							   	 
							   	 }
							   	 
							   	 ////////////
							   	 
							   	else if(obj_Name.equalsIgnoreCase("Analog Value")) {
							       		
							       		
							   		
							           	
							           	 ai_local = new ObjectIdentifier(ObjectType.analogValue,Integer.parseInt(t));
							                UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							                UnsignedInteger lifetime = new UnsignedInteger(p);
							                SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							            
							               try {
							               	  localDevice.send(d, request);
							                     }catch(Exception ex){
							                    	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                     }
							          
							      }
							   	 
							   	else if(obj_Name.equalsIgnoreCase("Analog Output")) {
							   		
							   		      	
							      	 ai_local = new ObjectIdentifier(ObjectType.analogOutput,Integer.parseInt(t));
							            UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							       
							           try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                 }
							     
							  }
							   	 
							   	 else if(obj_Name.equalsIgnoreCase("Binary Input")) {
							    		  ai_local = new ObjectIdentifier(ObjectType.binaryInput,Integer.parseInt(t));
							    		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							       
							           try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                	
							                 }
							    	  }
							    	  else if(obj_Name.equalsIgnoreCase("Binary Output")) {
							    		  ai_local = new ObjectIdentifier(ObjectType.binaryOutput,Integer.parseInt(t));
							    		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							       
							           try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                	
							                 }
							    	  }
							    	else if(obj_Name.equalsIgnoreCase("Binary Value")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.binaryValue,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               
							                }
							 	  }
							    	  else if(obj_Name.equalsIgnoreCase("Multi-state Input")) {
							    		  ai_local = new ObjectIdentifier(ObjectType.multiStateInput,Integer.parseInt(t));
							    		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							       
							           try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                	
							                 }
							    	  }
							    	  else if(obj_Name.equalsIgnoreCase("Multi-state Output")) {
							    		  ai_local = new ObjectIdentifier(ObjectType.multiStateOutput,Integer.parseInt(t));
							    		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							       
							           try {
							           	  localDevice.send(d, request);
							                 }catch(Exception ex){
							                	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							                	
							                 }
							    	  }
							    	else if(obj_Name.equalsIgnoreCase("Multi-state Value")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.multiStateValue,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Trend Log")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.trendLog,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Load Control")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.loadControl,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Life Safety Point")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.lifeSafetyPoint,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	 
							                }
							 	  }
							     	 
							  	else if(obj_Name.equalsIgnoreCase("Calendar")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.calendar,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("File")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.file,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Loop")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.loop,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               			                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Notification Class")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.notificationClass,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							  	else if(obj_Name.equalsIgnoreCase("Schedule")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.schedule,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							 	else if(obj_Name.equalsIgnoreCase("Program")) {
							 		  ai_local = new ObjectIdentifier(ObjectType.program,Integer.parseInt(t));
							 		 UnsignedInteger subscriberProcessIdentifier = new UnsignedInteger(p);
							            UnsignedInteger lifetime = new UnsignedInteger(p);
							            SubscribeCOVRequest request = new SubscribeCOVRequest(subscriberProcessIdentifier, ai_local,new Boolean(true), lifetime);
							   
							           try {
							           	  localDevice.send(d, request);
							                }catch(Exception ex){
							               	 vc_cov.add(""+obj_Name+" "+new Integer(t));
							               	
							                }
							 	  }
							   	 
						     }
							         	 
							   } //closed for loop
							   
							   
							   	if(!vc_cov.isEmpty())
								{
									
									for(int i=0;i<vc_cov.size();i++)
									{ 
										String obj_name="";
										
										obj_name=peUtil.obj2str(vc_cov.get(i));

									BacnetCOVStatusTable bcs = new BacnetCOVStatusTable();
									bcs.setObj_name(obj_name);
									bcs.setDevice_id(device_idd);
									bcs.setServer_name(server_name);
									bcs.setBroadcast_ip(ip);
									bcs.setStatus("false");
									}
								   
							   }
							   
					}
				   
				   /////////////////
				   
				   
				   
				   
				   
				   
				   
				   
				   localDevice.terminate();

					btn_save.setEnabled(true);
					btn_next.setEnabled(true);
					btn_back.setEnabled(true);
				   
		}

	    
	    
	    
	    
			   }







	static String getExtendedDeviceInformation(RemoteDevice d) throws BACnetException {
	    ObjectIdentifier oid = d.getObjectIdentifier();
	   // System.out.println(oid.getInstanceNumber());
	  
	    // Get the device's supported services
	   // System.out.println("protocolServicesSupported");
	    ReadPropertyAck ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid,
	            PropertyIdentifier.protocolServicesSupported));
	    d.setServicesSupported((ServicesSupported) ack.getValue());

	   
	    ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid, PropertyIdentifier.objectName));
	    d.setName(ack.getValue().toString());
	    System.out.println("Server Name=="+ack.getValue().toString());
	  
	     return ack.getValue().toString();

	}
	public static String getDeviceName(List<ObjectIdentifier> oids){
	    
		   String device_name = "";
	    
		     System.out.println("000000000==============="+oids);
		      
		   for(int s=0;s<oids.size();s++) {
	   	 
	   	 String str = oids.get(s).toString();
	   	if(str.contains("Device")) {
	   		device_name=str.trim();
	   		
	   	}
	   	 
	   	      	 
	   
	   	
	   	 }
	    
	     return device_name;

	}
	public static Vector getDeviceFolders(List<ObjectIdentifier> oids)  {
		   
		   Vector vc = new Vector();
		      
		   for(int s=2;s<oids.size();s++) {
	   	 
	   	 String str = oids.get(s).toString();
	   	 str =str.substring(0, (str.length()-1)).trim();
	   	 
	   	      	 
	   	vc.add(""+str.trim());
	   	
	   	 }
	    
	     return vc;

	}
	public static Vector getDeviceManeFolders(List<ObjectIdentifier> oids) {
		   
		   Vector vc = new Vector();
		      
		   for(int s=0;s<oids.size();s++) {
	   	 
	   	 String str = oids.get(s).toString();
	   	 
	   	 /*
	   	 	if(str.contains("Device") {
	   	 	return vc;
	   	 	
	   	 	}
	   	  */
	   	 
	   	 
	   	 
		 if(str.contains("Device") || str.contains("Vendor") || str.contains("Schedule") || str.contains("Notification") || str.contains("Program") || str.contains("File") || str.contains("Calendar") || str.contains("Loop")|| str.contains("Accumulator")|| str.contains("Trend")|| str.contains("Life")|| str.contains("Command")) {
	   		//System.out.println("==Yug===="+str);
	   	}else {
	   	 String value="";
	   	 String[] arrSplit = str.split(" ");
	   	 if(arrSplit.length==2) {
	   		 value = ""+arrSplit[0];
				
				 }
			 
			 else if(arrSplit.length==3) {
				 value = ""+arrSplit[0]+" "+arrSplit[1];
			
			 }
			 else if(arrSplit.length==4) {
				 
				 value = ""+arrSplit[0]+" "+arrSplit[1]+" "+arrSplit[2];
				
				 
			 }
			 else if(arrSplit.length==5) {
				 
				 value = ""+arrSplit[0]+" "+arrSplit[1]+" "+arrSplit[2]+" "+arrSplit[3];
				
				 
			 }
	   	 vc.add(""+value.trim());
	   	}
	   	
	   	 }
	    
	     return vc;

	}

	public static Vector getObjectTypes(List<ObjectIdentifier> oids) throws BACnetException {
		   
		   Vector vc = new Vector();
		   Vector vc_type = new Vector();
		      
		   for(int s=2;s<oids.size();s++) {
	   	 
	   	 String str = oids.get(s).toString();
	     	vc.add(""+str.trim());
	   	
	   	 }
		  
	    
	     return vc;

	}
	public static Vector getObjNameByobjType(RemoteDevice d ,List<ObjectIdentifier> oids,String objType){
		Vector vcObjName=new Vector();
		
		LinkedHashMap htGetVal= getKeyValueRoot(d,oids);
	    Set entrySet = htGetVal.entrySet();
		   Iterator itr = entrySet.iterator();
	 	while(itr.hasNext()){
			Map.Entry me1 = (Map.Entry) itr.next();
			String key =""+me1.getKey();
			String val =""+me1.getValue();
			key = key.substring(0, (key.length()-1));
			//System.out.println("==XXXX==="+key);
			if(objType.trim().equalsIgnoreCase(key.trim())) {
				
				vcObjName.add(""+val.trim());
			
			}
	 	}
		
		return vcObjName;

	}
		public static String getObjName(RemoteDevice d ,List<ObjectIdentifier> oids,String tag_name){
	    	String vcObjName="";
	    	
	    	
		  	  try{
		  		 
		                    
		          PropertyReferences references = new PropertyReferences();
		  		 
		  		 for(int s=0;s<oids.size();s++) {
		        	 
		        	 String str = oids.get(s).toString();
		        	 
		        	 
		        	 //if(str.contains("Device") || str.contains("Vendor")) {
	    		 
	      	 
		    if(str.contains("Device") || str.contains("Vendor") || str.contains("Schedule") || str.contains("Notification") || str.contains("Program") || str.contains("File") || str.contains("Calendar") || str.contains("Loop")|| str.contains("Accumulator")|| str.contains("Trend")|| str.contains("Life")|| str.contains("Command")) {
	      		//System.out.println("==Yug===="+str);
	      	}else {
				references.add(oids.get(s), PropertyIdentifier.objectName);
		        	 PropertyValues values = RequestUtils.readProperties(localDevice, d, references, null);
		        	 String str_name = values.getString(oids.get(s), PropertyIdentifier.objectName);
		        	 
		        	
					if(str_name.equals(tag_name))
					{
						vcObjName=str;
					}
					
		  		 }
		        	

		        	
		      	//RequestUtils.setPresentValue(localDevice, d, oids.get(s), null);    
		        	
		        	 }
		  		 
		  		
		       
		            
		           
		  		  
		  	  }catch(Exception e){
		  		  System.out.println(  e );
		  	  }
		  	
	    	
	    	return vcObjName;
	    
	    }
		 public static LinkedHashMap getKeyValueRoot(RemoteDevice d ,List<ObjectIdentifier> oids){
		    	
			    
		  	  LinkedHashMap htRetrnValue = new LinkedHashMap();
		  	  try{
		  		  Vector<String> vc_obj_type = new Vector<String>();
		          Vector<String> vc_obj_name= new Vector<String>();
		          
		          Vector<String> vc_root= new Vector<String>();
		          
		          PropertyReferences references = new PropertyReferences();
		  		 
		  		 for(int s=2;s<oids.size();s++) {
		        	 
		        	 String str = oids.get(s).toString();
		        	 
		        	// str = str.substring(0, (str.length()-1)).trim();
		        	            		 
		        	 vc_obj_type.add(str.substring(0, (str.length()-1)).trim());
		        	 
		        	 references.add(oids.get(s), PropertyIdentifier.objectName);
		        	 PropertyValues values = RequestUtils.readProperties(localDevice, d, references, null);
		        	 String str_name = values.getString(oids.get(s), PropertyIdentifier.objectName);
		        	      	
		        	         	 
		        	 htRetrnValue.put(str,str_name);
		        	
		        	 }
		  		 
		  		
		        // System.out.println("==htRetrnValu="+htRetrnValue);
		  		 	/*Set<String> unique = new HashSet<String>();
		  		 	unique.addAll(vc_obj_type);
		    
		      
		            for (String s : unique) {
		            	vc_root.add(s);
		            }*/
		            
		           
		  		  
		  	  }catch(Exception e){
		  		  System.out.println(  e );
		  	  }
		  	  return htRetrnValue;
		    }
		    public static String getService(RemoteDevice d ,List<ObjectIdentifier> oids, String objType,int obj_id ){
		    	
		    	String str_value="";
		    	
		    	
		   	  ObjectIdentifier ai_local = null;
		   	  PropertyValues pvs_local = null;
		      PropertyValues values1 = null;
		   	  PropertyReferences references = new PropertyReferences();
		   	 
		   	 
		   	 
		   	 
		   	 
		   	 
			if(objType.equalsIgnoreCase("Analog Input")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.analogInput,obj_id);
		   		  
		   	  }

		   	  else if(objType.equalsIgnoreCase("Analog Output")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.analogOutput,obj_id);
		   		  
		   	  }
		   	  else if(objType.equalsIgnoreCase("Analog Value")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.analogValue,obj_id);
		   		  
		   	  }
		   	  else if(objType.equalsIgnoreCase("Binary Input")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.binaryInput,obj_id);
		   		  
		   	  }
		   	  else if(objType.equalsIgnoreCase("Binary Output")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.binaryOutput,obj_id);
		   		  
		   	  }
		   	  else if(objType.equalsIgnoreCase("Multi-state Input")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.multiStateInput,obj_id);
		   		  
		   	  }
		   	else if(objType.equalsIgnoreCase("Binary Value")) {
				  ai_local = new ObjectIdentifier(ObjectType.binaryValue,obj_id);
				  
			  }
		   	  else if(objType.equalsIgnoreCase("Multi-state Output")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.multiStateOutput,obj_id);
		   		  
		   	  }
		   	  else if(objType.equalsIgnoreCase("Multi-state Value")) {
		   		  ai_local = new ObjectIdentifier(ObjectType.multiStateValue,obj_id);
		   		  
		   	  }
			   	else if(objType.equalsIgnoreCase("Trend Log")) {
			 		  ai_local = new ObjectIdentifier(ObjectType.trendLog,obj_id);
			 		  
			 	  }
			   	else if(objType.equalsIgnoreCase("Load Control")) {
			 		  ai_local = new ObjectIdentifier(ObjectType.loadControl,obj_id);
			 		  
			 	  }
			   	else if(objType.equalsIgnoreCase("Life Safety Point")) {
					  ai_local = new ObjectIdentifier(ObjectType.lifeSafetyPoint,obj_id);
					  
				  }
			 	else if(objType.equalsIgnoreCase("Calendar")) {
					  ai_local = new ObjectIdentifier(ObjectType.calendar,obj_id);
					  
				  }
			 	else if(objType.equalsIgnoreCase("File")) {
					  ai_local = new ObjectIdentifier(ObjectType.file,obj_id);
					  
				  }
			 	else if(objType.equalsIgnoreCase("Loop")) {
					  ai_local = new ObjectIdentifier(ObjectType.loop,obj_id);
					  
				  }
			 	else if(objType.equalsIgnoreCase("Notification Class")) {
					  ai_local = new ObjectIdentifier(ObjectType.notificationClass,obj_id);
					  
				  }
			 	else if(objType.equalsIgnoreCase("Schedule")) {
					  ai_local = new ObjectIdentifier(ObjectType.schedule,obj_id);
					  
				  }
				else if(objType.equalsIgnoreCase("Program")) {
					  ai_local = new ObjectIdentifier(ObjectType.program,obj_id);
					  
				  }
		   	  
		   		  try {
		   	      
		             
		           
		               
						
		             	  references.add(ai_local, PropertyIdentifier.outOfService);
		                
						try {
							values1 = RequestUtils.readProperties(localDevice, d, references, null);
						} catch (BACnetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						str_value= values1.getString(ai_local, PropertyIdentifier.outOfService);
		                
		               	  //str_value = pvs_local.get(ai_local, PropertyIdentifier.outOfService).toString();
		 					 // System.out.println(str_value);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								
							}
		   	 if(str_value.length()>5) {
		   		 return  "";
		   		 
		   	 }
		   	 
		   	return str_value;
		  	
		    }


}
