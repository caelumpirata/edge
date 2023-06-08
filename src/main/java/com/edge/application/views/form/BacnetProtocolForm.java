package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.bind.Marshaller.Listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.BacnetTreeTable;
import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.Table.SourceModbusRTUTable;
import com.edge.application.views.Table.SourceModbusTCPTable;
import com.edge.application.views.Table.SourceOpcuaTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.edge.PlatformView;
import com.edge.application.views.service.BacnetTreeConstants;
import com.edge.application.views.service.EdgeService;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;
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


@PageTitle("Bacnet Protocol Form")
@Route(value = "bacnetprotocol" ,layout = MainLayout.class)
public class BacnetProtocolForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="bacnetprotocol";
	
	TextField txt_broadcast_ip     = new TextField("Broadcast IP");
	
	
	ComboBox<String> bacnet_instance_sever = new ComboBox<>("Bacnet Instance-Server Name");
	ComboBox<String> retie = new ComboBox<>("Retries");
	ComboBox<String> timeout = new ComboBox<>("Timeout(Millseconds)");
	ComboBox<String> cov = new ComboBox<>("COV");
		
	Button lookup = new Button("Lookup");
	Button btn_back = new Button("Back");
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");

	
	HorizontalLayout btn_layout = new HorizontalLayout();
	HorizontalLayout btn_lookup = new HorizontalLayout();
	
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	Grid<SourceBacnetTable> grid = new Grid<SourceBacnetTable>();
	ListDataProvider<SourceBacnetTable> dataProvider;
	
	static List<String> ls=new ArrayList<String>();
	
	public static LocalDevice localDevice;
	public static RemoteDevice d;
	public static List<ObjectIdentifier> oids;
	
	BacnetTreeConstants repo;
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;
	static String ip ="";
	static String global="";
	@Autowired
	private static EdgeService service;
	
	long main_id;
	String instance_id="";
	
	
	
	public BacnetProtocolForm(EdgeService service)
	{
		this.service=service;
	}
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
		this.service=service;
		main_id=Long.parseLong(para);
		
		retie.setAllowCustomValue(true); 
		retie.setItems("1","2","3");
	        
	        
	        
		timeout.setAllowCustomValue(true); 
		timeout.setItems("1000","2000","3000");
		
		cov.setAllowCustomValue(true); 
		cov.setItems("Enable","Disable");
	        
		bacnet_instance_sever.setAllowCustomValue(true); 
		bacnet_instance_sever.setItems(ls);     
    
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		lookup.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		if(!para.equals("0"))
        {
			for(SourceBacnetTable stt:service.bacnet_id_list(para))
			{
				instance_id = stt.getSource_id();
				txt_broadcast_ip.setValue(stt.getBroadcast_ip());
				bacnet_instance_sever.setValue(stt.getSource_id()+"-"+stt.getServer_name());
				retie.setValue(stt.getRetrie());
				timeout.setValue(stt.getTimeout());
				cov.setValue(stt.getCov());
			}
        }
		
		
		
		// Validation code
		binder.forField(txt_broadcast_ip).asRequired("Enter Broadcast IP").bind(Validation::getString,Validation::setString);
		binder.forField(bacnet_instance_sever).asRequired("Select Bacnet Server").bind(Validation::getString,Validation::setString);
		binder.forField(retie).asRequired("Select Retries").bind(Validation::getString,Validation::setString);
		binder.forField(timeout).asRequired("Select Timeout").bind(Validation::getString,Validation::setString);
		binder.forField(cov).asRequired("Select Cov").bind(Validation::getString,Validation::setString);
		

		btn_next.setEnabled(false);
		
		
		
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
				
				String broadcast="",server_name="",port="",retie_str="",timeout_str="",cov_str="";
				
				String server =bacnet_instance_sever.getValue().toString(); 
				
				if(!peUtil.isNullString(server)) {
				instance_id =server.split("-")[0];
				server_name=server.split("-")[1];
				
				broadcast = txt_broadcast_ip.getValue().toString();
				port="47808";
				retie_str=retie.getValue().toString();
				timeout_str=timeout.getValue().toString();
				cov_str=cov.getValue().toString();
				
							
				if(service.check_source_bacnet(instance_id,broadcast)) {
					
					Notification.show("Already Source has been saved ...!");
					
				}else {
					
							
											
												
												
					    SourceBacnetTable st = new SourceBacnetTable();
						st.setSource_id(instance_id);
						st.setBroadcast_ip(broadcast);
						st.setPort(port);
						st.setServer_name(server_name);
						st.setRetrie(retie_str);
						st.setTimeout(timeout_str);
						st.setCov(cov_str);
				
						
						st.setRowid(genUniqueID());
						
						service.source_bacnet_save(st);
						
						service.update_source_id(instance_id,main_id);
						
						
//						
//						if(service.check_source_modbus_rtu(instance_id)) {
//							
//							
//							service.deleteSource_modbus_rtu(instance_id);
//						}
//						
//						if(service.check_source_tcp(instance_id)) {
//							service.deleteSource_modbus_tcp(instance_id);
//						}
//						
//                      						
//						if(service.check_source_opcua(instance_id)) {
//							service.deleteSource_opcua(instance_id);
//						}
						
						Notification.show("Source and Bacnet has been mapped sucessfully...!");
						
						
						btn_next.setEnabled(true);
						btn_save.setEnabled(false);
						btn_skip.setEnabled(true);
						
						//UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + txt_source_id.getValue().toString());
						update();
				}
            	
					
        		
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
				
				String source_id = instance_id;
				if(service.check_source_bacnet(source_id))
        		{
					UI.getCurrent().navigate( BacnetTagTreeForm.ROUTE_NAME+ "/" + instance_id.toString());
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
			String id =instance_id;
			if(service.check_source_bacnet(id)) {
				UI.getCurrent().navigate( BacnetTagTreeForm.ROUTE_NAME+ "/" + instance_id.toString());
			}
			
			
		});
		btn_back.addClickListener(e -> {
			
			UI.getCurrent().navigate(SourceList.class );
		
		
		
	});		
		
		lookup.addClickListener(e -> {
			
			ls=new ArrayList<String>();
			ip = txt_broadcast_ip.getValue().toString();
			
			
			
			
			System.out.println("-----Broadcast--------"+ip);
			IpNetwork network = new IpNetwork(ip,47808);
			Transport transport = new Transport(network);
			localDevice = new LocalDevice(123, transport);
			try {
				
				
					 localDevice.initialize();
					 localDevice.getEventHandler().addListener(new Listener());
					 localDevice.sendGlobalBroadcast(new WhoIsRequest());
				
				
				//localDevice.setRetries(0);
				
				
			       
				Thread.sleep(6000);
				
			
				localDevice.terminate();
								
				 
				bacnet_instance_sever.setItems(ls);
				
			}catch(Exception eb){
					System.out.println("==Connection Exception===="+eb);
					Notification.show("==Connection Exception===="+eb, 3000, Position.MIDDLE);
					
			}
			
		});
		
		
		 // layout add code
		btn_layout.removeAll();
		if(peUtil.isNullString(instance_id)) {
		   btn_layout.add(btn_back,btn_save,btn_next);
		}else {
			String id =instance_id;
			if(service.check_source_bacnet(id) ) {
			   btn_layout.add(btn_back,btn_save,btn_skip);
			}else {
				btn_layout.add(btn_back,btn_save,btn_next);
			}
		}
		
		btn_lookup.add(lookup);
		formLayout.add( txt_broadcast_ip,btn_lookup ,bacnet_instance_sever, retie,timeout,cov, btn_layout );
		
		
		
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
		
		Grid.Column<SourceBacnetTable> Raw_Material = grid.addColumn(SourceBacnetTable::getSource_id).setHeader("Instance ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceBacnetTable> sap_id = grid.addColumn(SourceBacnetTable::getServer_name).setHeader("Server Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceBacnetTable> scada_ip = grid.addColumn(SourceBacnetTable::getBroadcast_ip).setHeader("Broadcast IP")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceBacnetTable> scada_id = grid.addColumn(SourceBacnetTable::getPort).setHeader("Port")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		Grid.Column<SourceBacnetTable> scada_cov = grid.addColumn(SourceBacnetTable::getCov).setHeader("COV")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<SourceBacnetTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
							service.deleteSource_bacnet(sourcedata.getId());
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

		List<SourceBacnetTable> list = service.bacnet_source_id();
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
			
			System.out.println("-----str_device--------"+str_device);
			
			try {
				ls.add(instance_no+"-"+server_name);
								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Notification.show("==Connection Exception===="+e, 3000, Position.MIDDLE);
			}
           
           
           
			
          
        }
        catch (BACnetException e) {
            e.printStackTrace();
        }
			}
    
    
	public void generateTree(RemoteDevice d,String dev_id,String server_name ) {
		
		
		
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
			   //long element_id=2;
			   long parent_element=service.perent_id(device_name);
			   
			   Vector vc_tag=new Vector();
			   
			   for(int i=0;i<Folder_name.size();i++)
			   {
				   //element_id++;
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
						try {
							service.bacnet_tree_save(btt);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
			   
			   
			   localDevice.terminate();
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
   	 
   	 
   	 
	 if(str.contains("Device") || str.contains("Vendor") || str.contains("Schedule") || str.contains("Notification") || str.contains("Program") || str.contains("File") || str.contains("Calendar") || str.contains("Loop")) {
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
    		 
      	 
    	 if(str.contains("Device") || str.contains("Vendor") || str.contains("Schedule") || str.contains("Notification") || str.contains("Program") || str.contains("File") || str.contains("Calendar") || str.contains("Loop")) {
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
