//package com.edge.application.views.BACNET;
//
//import java.util.List;
//import java.util.Vector;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.edge.application.views.Interface.peUtil;
//
//import com.serotonin.bacnet4j.LocalDevice;
//import com.serotonin.bacnet4j.RemoteDevice;
//import com.serotonin.bacnet4j.event.DeviceEventAdapter;
//import com.serotonin.bacnet4j.exception.BACnetException;
//import com.serotonin.bacnet4j.exception.PropertyValueException;
//import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
//import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
//import com.serotonin.bacnet4j.transport.Transport;
//import com.serotonin.bacnet4j.type.constructed.Address;
//import com.serotonin.bacnet4j.type.constructed.SequenceOf;
//import com.serotonin.bacnet4j.type.enumerated.ObjectType;
//import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
//import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
//import com.serotonin.bacnet4j.type.primitive.OctetString;
//import com.serotonin.bacnet4j.type.primitive.Unsigned16;
//import com.serotonin.bacnet4j.util.PropertyReferences;
//import com.serotonin.bacnet4j.util.PropertyValues;
//import com.serotonin.bacnet4j.util.RequestUtils;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.notification.Notification.Position;
//
//public class TestReading {
//	
//	public static LocalDevice localDevice;
//	public static RemoteDevice d;
//	public static List<ObjectIdentifier> oids;
//	static String source="Analog Input 0";
//	String server_name="BACnetDeviceServer1";
//	
//	public static void main(String args[])
//	{
//		
//		IpNetwork network = new IpNetwork("192.168.0.255",47808);
//		Transport transport = new Transport(network);
//		localDevice = new LocalDevice(123, transport);
//		try {
//			
//			
//				 localDevice.initialize();
//				 localDevice.getEventHandler().addListener(new Listener());
//				 localDevice.sendGlobalBroadcast(new WhoIsRequest());
//			
//			
//			//localDevice.setRetries(0);
//			
//			
//		       
//			Thread.sleep(6000);
//			
//			
//			//localDevice.terminate();
//							
//			 localDevice.getEventHandler().removeListener(new Listener());
//			
//			
//		}catch(Exception eb){
//				System.out.println("==Connection Exception===="+eb);
//				//Notification.show("==Connection Exception===="+eb, 3000, Position.MIDDLE);
//				
//		}
//		
//		
//	}
//	
//
//
//static class Listener extends DeviceEventAdapter {
//		
//		@Autowired
//	    @Override
//	    public void iAmReceived(RemoteDevice d) {
//	   try {
//		   System.out.println("-----iAmReceived--------");
//	            Address a = new Address(new Unsigned16(0), new OctetString(new byte[] { (byte) 0xc0, (byte) 0xa8, 0x1,
//	                    0x5, (byte) 0xba, (byte) 0xc0 }));
//
//	            @SuppressWarnings("unchecked")
//				List<ObjectIdentifier> oids = ((SequenceOf<ObjectIdentifier>)
//	            						 RequestUtils.sendReadPropertyAllowNull(
//	            						localDevice, d, d.getObjectIdentifier(), 
//	            						PropertyIdentifier.objectList)).getValues();
//	          // System.out.println(oids); // all tree
//	           
//	           
//				
//	           
//	            try {
//					redata(source,localDevice,d,oids);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//	          
//	        }
//	        catch (BACnetException e) {
//	            e.printStackTrace();
//	        }
//				}
//}
//	
//	 private static void redata(String source,LocalDevice localDevice,RemoteDevice d,List<ObjectIdentifier> oids) {
//		 PropertyValues pvs_local = null;
//			try {
//				pvs_local = RequestUtils.readOidPresentValues(localDevice, d, oids, null);
//			} catch (BACnetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			if(!peUtil.isNullString(source)) 
//			   {
//				   
//				   
//				   String obj_type_str = "";
//				   String    obj_type_id = "";
//				    
//				       Vector vc = peUtil.split(source, " ");
//				     if(!vc.isEmpty()) 
//				     {
//				    	 
//				    	 if(vc.size()==2) {
//							 obj_type_str = peUtil.obj2str(vc.get(0));
//							 obj_type_id = peUtil.obj2str(vc.get(1));
//							 }
//						 
//							 else if(vc.size()==3) {
//							 obj_type_str = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1));
//							 obj_type_id = peUtil.obj2str(vc.get(2));
//							 }
//							 else if(vc.size()==4) {
//								 
//								 obj_type_str = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2));
//								 obj_type_id = peUtil.obj2str(vc.get(3));
//								 
//							 }
//							 else if(vc.size()==5) {
//								 
//								 obj_type_str =peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2))+" "+peUtil.obj2str(vc.get(3));
//								 obj_type_id = peUtil.obj2str(vc.get(4));
//								 
//							 }
//				    	 
//				    	 try {
//					    	 
//					    	 String getvalue=getObjValue(pvs_local,localDevice, d,oids,obj_type_str,new Integer(obj_type_id)); 
//					    	 if(!getvalue.equals("") && getvalue!=null) {
//	   		            	  	 System.out.println("= Read=Value===Continue==="+obj_type_str+"======"+obj_type_id+"==="+peUtil.obj2double(Float.parseFloat(getvalue)));
//	   		            	 } 
//				    	 }catch(Exception exTannu) {
//					    		 System.out.println("Exception by Tannu===="+exTannu);
//					    		 
//					    	 }
//				    	 
//				    	 
//				     } // if condition empty
//			   } // source null value
//		
//	}
//
//
//	public static String getObjValue(PropertyValues pvs_local,LocalDevice ld,RemoteDevice d ,List<ObjectIdentifier> oids,String objType,int obj_id)  {
//	    	String str_value="";
//	    	 ObjectIdentifier ai_local = null;
//	    	  
//	          PropertyValues values1 = null;
//	    	 PropertyReferences references = new PropertyReferences();
//	    	 
//	    	 
//	    	 
//	    	 
//	    	 
//	    	  if(objType.equalsIgnoreCase("Analog Input")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.analogInput,obj_id);
//	    		  
//	    	  }
//
//	    	  else if(objType.equalsIgnoreCase("Analog Output")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.analogOutput,obj_id);
//	    		  
//	    	  }
//	    	  else if(objType.equalsIgnoreCase("Analog Value")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.analogValue,obj_id);
//	    		  
//	    	  }
//	    	  else if(objType.equalsIgnoreCase("Binary Input")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.binaryInput,obj_id);
//	    		  
//	    	  }
//	    	  else if(objType.equalsIgnoreCase("Binary Output")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.binaryOutput,obj_id);
//	    		  
//	    	  }
//	    	 	else if(objType.equalsIgnoreCase("Binary Value")) {
//	    	 		  ai_local = new ObjectIdentifier(ObjectType.binaryValue,obj_id);
//	    	 		  
//	    	 	  }
//	    	  else if(objType.equalsIgnoreCase("Multi-state Input")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.multiStateInput,obj_id);
//	    		  
//	    	  }
//	    	  else if(objType.equalsIgnoreCase("Multi-state Output")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.multiStateOutput,obj_id);
//	    		  
//	    	  }
//	    	  else if(objType.equalsIgnoreCase("Multi-state Value")) {
//	    		  ai_local = new ObjectIdentifier(ObjectType.multiStateValue,obj_id);
//	    		  
//	    	  }
//	    	 	else if(objType.equalsIgnoreCase("Trend Log")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.trendLog,obj_id);
//	    			  
//	    		  }
//	    	   	else if(objType.equalsIgnoreCase("Load Control")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.loadControl,obj_id);
//	    			  
//	    		  }
//	    		else if(objType.equalsIgnoreCase("Life Safety Point")) {
//	  			  ai_local = new ObjectIdentifier(ObjectType.lifeSafetyPoint,obj_id);
//	  			  
//	  		  }
//	    	 	else if(objType.equalsIgnoreCase("Calendar")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.calendar,obj_id);
//	    			  
//	    		  }
//	    	 	else if(objType.equalsIgnoreCase("File")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.file,obj_id);
//	    			  
//	    		  }
//	    	 	else if(objType.equalsIgnoreCase("Loop")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.loop,obj_id);
//	    			  
//	    		  }
//	    	 	else if(objType.equalsIgnoreCase("Notification Class")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.notificationClass,obj_id);
//	    			  
//	    		  }
//	    	 	else if(objType.equalsIgnoreCase("Schedule")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.schedule,obj_id);
//	    			  
//	    		  }
//	    		else if(objType.equalsIgnoreCase("Program")) {
//	    			  ai_local = new ObjectIdentifier(ObjectType.program,obj_id);
//	    			  
//	    		  }
//	    	  
//	    		  try {
//	    	      
//	              
//	            
//	                
//					
////	              	  references.add(ai_local, PropertyIdentifier.objectName);
////	                 
////					try {
////						values1 = RequestUtils.readProperties(localDevice, d, references, null);
////					} catch (BACnetException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//	            	    // String local_obj_name = values1.getString(ai_local, PropertyIdentifier.objectName);
//	                 
//	                	  str_value = pvs_local.get(ai_local, PropertyIdentifier.presentValue).toString();
//	  					 // System.out.println(str_value);
//						} catch (PropertyValueException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//	    		 
//	    	 
//	    	 
//	    	return str_value;
//	    }
//
//}
