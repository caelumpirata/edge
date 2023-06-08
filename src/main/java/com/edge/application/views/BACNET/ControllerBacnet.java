package com.edge.application.views.BACNET;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.BacnetLiveTable;
import com.edge.application.views.Table.BacnetTreeTable;
import com.edge.application.views.service.EdgeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.exception.PropertyValueException;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;

@Controller
public class ControllerBacnet {
	
	
	@Autowired
	EdgeService service;
	
	public static String source="",tag="",server_name="",status="",broadcasr_ip="";
	public static LocalDevice localDevice;
	public static RemoteDevice d;
	public static List<ObjectIdentifier> oids;
	
	
	@RequestMapping("backnettreejson")
	@ResponseBody
	public String viewHomePage1(Model m,HttpServletRequest rec) {
		String url=rec.getParameter("server_name");
		System.out.println("Server name..."+url);
		jsontree(m,url);
		//System.out.println(m.getAttribute("treeproduct"));
		return m.getAttribute("treeproduct").toString();
	}

	private void jsontree(Model m,String server_name) {
		// TODO Auto-generated method stub
		
		List<BacnetTreeTable> treedata=service.tree_bacnet(server_name);
		String jsonStr = null;
		ObjectMapper Obj = new ObjectMapper();

		try {

			// get Oraganisation object as a json string
			jsonStr = Obj.writeValueAsString(treedata);
			jsonStr = jsonStr.replace("\"parent\":0", "\"parent\":\"#\"");
			jsonStr = jsonStr.replace("\"icon\":\"", "\"icon\":\"/tag/");
			m.addAttribute("treeproduct", jsonStr);
			// Displaying JSON String
			//System.out.println("hi" + jsonStr);
		}

		catch (IOException e) {
			System.out.println(e);
		}

	}
	
	@GetMapping("subscribeBacnet")
	@ResponseBody
	public String subtest(HttpServletRequest rec) throws Exception {
		String source = rec.getParameter("source");
		String server_name = rec.getParameter("server_name");
		String tag = rec.getParameter("tag");
		String broadcast_ip=rec.getParameter("broadcast_ip");

		//System.out.println("source..."+source+"...."+server_name+"....."+tag);
		service.bac_delete_live();
		BacnetLiveTable bt = new BacnetLiveTable();
		bt.setServer_name(server_name);
		bt.setSource(source);
		bt.setTag_name(tag);
		bt.setStatus("yes");
		bt.setBroadcast_ip(broadcast_ip);
		service.bacnet_live_save(bt);
		
		runData12();
//		SubThreadCall("");
		return "treeJson";
	}
	
	

	@GetMapping("unsubscribeBacnet")
	@ResponseBody
	public String unsubtest(HttpServletRequest rec) throws Exception {
		
		String source = rec.getParameter("source");
		String server_name = rec.getParameter("server_name");
		String tag = rec.getParameter("tag");

		try
		{
			service.update_live(server_name, source, tag);
		}
		catch(Exception e)
		{
			
		}
		
		runData12();
		return "success";
	}
	
	@RequestMapping("bacnetlivereadload")
	@ResponseBody
	private String runData12() {
		// TODO Auto-generated method stub
		
		String final_val="",send_data="";
		if(!service.live_bacnet().isEmpty())
		{
			for(BacnetLiveTable bt : service.live_bacnet())
			{
				source=bt.getSource();
				tag=bt.getTag_name();
				server_name=bt.getServer_name();
				status=bt.getStatus();
				broadcasr_ip=bt.getBroadcast_ip();
				//broadcasr_ip=service.get_broadcast_ip(server_name);
				
				//System.out.println("Source..1..."+source);
				if(status.equals("yes"))
				{
					try
					{
						if(ControllerBacnet.localDevice.isInitialized())
						{
							final_val= redata(ControllerBacnet.source,ControllerBacnet.localDevice,ControllerBacnet.d,ControllerBacnet.oids);
						}
						else
						{
							//ControllerBacnet.localDevice.isInitialized()
							ConnectionBacnet cb	=	null;
							try {
							 cb = new ConnectionBacnet(broadcasr_ip);
							 if(ControllerBacnet.localDevice.isInitialized()) {
								 final_val= redata(ControllerBacnet.source,ControllerBacnet.localDevice,ControllerBacnet.d,ControllerBacnet.oids);
								 System.out.println("===Device Connected===="+ControllerBacnet.localDevice.isInitialized());
							 }else {
								 
								 System.out.println("===Connection Device Exception====try");
							 }
							}catch(Exception bx) {
								System.out.println("===Connection Device Exception====try");
							}
						}
					}
					catch(Exception e)
					{
						ConnectionBacnet cb	=	null;
						try {
						 cb = new ConnectionBacnet(broadcasr_ip);
						 if(ControllerBacnet.localDevice.isInitialized()) {
							 final_val=redata(ControllerBacnet.source,ControllerBacnet.localDevice,ControllerBacnet.d,ControllerBacnet.oids);
							 System.out.println("===Device Connected===="+ControllerBacnet.localDevice.isInitialized());
						 }else {
							 
							 System.out.println("===Connection Device Exception====catch");
						 }
						}catch(Exception bx) {
							System.out.println("===Connection Device Exception====catch");
						}
						System.out.println("Main try..."+e);
					}
					
				} // status
				send_data="{\"server_name\":\""+server_name+"\",\"tag\":\""+tag+"\",\"value\":\""+final_val+"\",\"source\":\""+source+"\"}";
			}// for loop
			//System.out.println("Final...."+final_val);
		}// is empty
		
		return send_data;
	}
	

	 private static String redata(String source,LocalDevice localDevice,RemoteDevice d,List<ObjectIdentifier> oids) {
		 String val="";
		 PropertyValues pvs_local = null;
			try {
				pvs_local = RequestUtils.readOidPresentValues(localDevice, d, oids, null);
			} catch (BACnetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!peUtil.isNullString(source)) 
			   {
				   
				   
				   String obj_type_str = "";
				   String    obj_type_id = "";
				    
				       Vector vc = peUtil.split(source, " ");
				     if(!vc.isEmpty()) 
				     {
				    	 
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
				    	 
				    	 try {
					    	 
					    	 String getvalue=getObjValue(pvs_local,localDevice, d,oids,obj_type_str,new Integer(obj_type_id)); 
					    	 if(!getvalue.equals("") && getvalue!=null) {
	   		            	  	 System.out.println("= Read=Value===Continue==="+obj_type_str+"======"+obj_type_id+"==="+peUtil.obj2double(Float.parseFloat(getvalue)));
	   		            	  	 val=getvalue;
	   		            	 } 
				    	 }catch(Exception exTannu) {
					    		 System.out.println("Exception by Tannu===="+exTannu);
					    		 
					    	 }
				    	 
				    	 
				     } // if condition empty
			   } // source null value
			return val;
		
	}

	 public static String getObjValue(PropertyValues pvs_local,LocalDevice ld,RemoteDevice d ,List<ObjectIdentifier> oids,String objType,int obj_id)  {
	    	String str_value="";
	    	 ObjectIdentifier ai_local = null;
	    	  
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
	    	 	else if(objType.equalsIgnoreCase("Binary Value")) {
	    	 		  ai_local = new ObjectIdentifier(ObjectType.binaryValue,obj_id);
	    	 		  
	    	 	  }
	    	  else if(objType.equalsIgnoreCase("Multi-state Input")) {
	    		  ai_local = new ObjectIdentifier(ObjectType.multiStateInput,obj_id);
	    		  
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
	    	      
	              
	            
	                
					
//	              	  references.add(ai_local, PropertyIdentifier.objectName);
//	                 
//					try {
//						values1 = RequestUtils.readProperties(localDevice, d, references, null);
//					} catch (BACnetException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	            	    // String local_obj_name = values1.getString(ai_local, PropertyIdentifier.objectName);
	                 
	                	  str_value = pvs_local.get(ai_local, PropertyIdentifier.presentValue).toString();
	  					 // System.out.println(str_value);
						} catch (PropertyValueException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    		 
	    	 
	    	 
	    	return str_value;
	    }

}
