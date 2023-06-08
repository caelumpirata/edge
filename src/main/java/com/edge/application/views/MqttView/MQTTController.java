package com.edge.application.views.MqttView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.BacnetLiveTable;
import com.edge.application.views.Table.LiveEntity;
import com.edge.application.views.Table.MqttLiveTable;
import com.edge.application.views.Table.MqttTopicTreeTable;
import com.edge.application.views.Table.MqttliveEntryTable;
import com.edge.application.views.Table.SourceMqttlTable;
import com.edge.application.views.Table.opctreetable;
import com.edge.application.views.service.EdgeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MQTTController {
	
	@Autowired
	private EdgeService service;
	
	@RequestMapping("browsermqtt")
	@ResponseBody
	public String modbusTCPtree(HttpServletRequest req) throws JsonProcessingException
	{
		
		String source_id=req.getParameter("source_id");
		String devicename=service.get_source_name_mqtt(source_id);
		
		ModbusTreeStruct mts;
		Collection<ModbusTreeStruct> mdsCollect = new ArrayList<ModbusTreeStruct>();
		ObjectMapper obj = new ObjectMapper();
		
		mts = new ModbusTreeStruct("1", "MQTT Topic Tree", "#", "root","");
		mdsCollect.add(mts);
		
		int i = 1;
		
//		List<Mo> list = service.source_tcp_list();
//		
//		for(SourceModbusTCPTable tcp:list)
//		{
			mts = new ModbusTreeStruct("" + (2), devicename,  "1" , "device",source_id);

			mdsCollect.add(mts);
			
			int tag=i+1;
			
			List<MqttTopicTreeTable> taglist=service.getlistname(devicename);
			
			for(MqttTopicTreeTable tagm:taglist)
			{
				i++;
				mts = new ModbusTreeStruct("" + (i + 1), tagm.getTopic_name(),  "" + tag, "para",source_id);

				mdsCollect.add(mts);
				
			}
		//}
		
		return obj.writeValueAsString(mdsCollect).toString();
	}
	@GetMapping("subscribeMqtt")
	@ResponseBody
	public String mqtt_topic_status_sub(HttpServletRequest rec) throws Exception {
		String source_id = rec.getParameter("source_id");
		String topic = rec.getParameter("topic_name");


		//System.out.println("source..."+source+"...."+server_name+"....."+tag);
		service.mqtt_delete_live();
		MqttLiveTable bt = new MqttLiveTable();
		bt.setSource_id(source_id);
		bt.setTopic_name(topic);
		bt.setStatus("yes");
		
		service.mqtt_live_save(bt);
		
		runMqttData();
//		SubThreadCall("");
		return "treeJson";
	}
	
	

	@GetMapping("unsubscribeMqtt")
	@ResponseBody
	public String mqtt_topic_status_unsub(HttpServletRequest rec) throws Exception {
		
		String source_id = rec.getParameter("source_id");
		String topic = rec.getParameter("topic_name");
		
       
		try
		{
			service.mqtt_live(source_id,topic);
		}
		catch(Exception e)
		{
			
		}
		
		//runData12();
		return "success";
	}
	
	@RequestMapping("mqttLiveReload")
	@ResponseBody
    public String runMqttData() throws Exception {
        // synchronous connect
		
		 String send_data="";
		
		    String url="",port="",source_id="",source_name="",topic="",status="",datetime="";
		    
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			datetime = now.toString();
		    
		    for(MqttLiveTable stt:service.live_mqtt())
			{
		    	source_id = stt.getSource_id();
		    	topic =stt.getTopic_name();
			}
		    
		    if(!peUtil.isNullString(source_id)) {
		    
		    status =service.mqtt_topic_status(source_id);
		    
		    if(status.equalsIgnoreCase("yes")) {
		    
				    for(MqttTopicTreeTable stt:service.list_source_mqtt_tree(source_id))
					{
				    	url = stt.getUrl();
				    	port = stt.getPort();
				    	source_name = stt.getSource_name();
				    	
					}
				    
				      String broker =url+":"+port;
				     		       
				    //  System.out.println("bbbbbbbbb---------"+broker);
				       String cid = "subscribe_mqtt";
				       int qs = 0;

				       try {
				           @SuppressWarnings("resource")
						MqttClient client = new MqttClient(broker, cid, new MemoryPersistence());
				           // connect options
				           MqttConnectOptions options = new MqttConnectOptions();
				         //  options.setConnectionTimeout(60);
				        //   options.setKeepAliveInterval(60);
				           
				           String data="";
				           // setup callback
				           client.setCallback(new MqttCallback() {

				               public void connectionLost(Throwable cause) {
				                   //System.out.println("connectionLost: " + cause.getMessage());
				              }

				               public void messageArrived(String topic, MqttMessage message) {
				                 String topic_name = topic;
				                 String message_content =  new String(message.getPayload());
				                 
				                 String url="",port="",source_id="",source_name="",status="",datetime="";
				     		    
				     		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				     			LocalDateTime now = LocalDateTime.now();
				     			
				     			datetime = now.toString();
				                 
				                 for(MqttLiveTable stt:service.live_mqtt())
				     			{
				     		    	source_id = stt.getSource_id();
				     			}
				                 status =service.mqtt_topic_status(source_id);
				                 for(MqttTopicTreeTable stt:service.list_source_mqtt_tree(source_id))
									{
								    	url = stt.getUrl();
								    	port = stt.getPort();
								    	source_name = stt.getSource_name();
								    	
									}
				                 

				                 service.delete_mqtt_entry_live(source_id);
				                 
				                 MqttliveEntryTable mqt = new MqttliveEntryTable();
				                 
				                 mqt.setSource_id(source_id);
				                 mqt.setSource_name(source_name);
				                 mqt.setBroker(url+":"+port);
				                 mqt.setTopic_name(topic_name);
				                 mqt.setStatus(status);
				                 mqt.setDatetime(datetime);
				                 mqt.setTopic_value(message_content);
				                 service.mqtt_live_entry_save(mqt);
				                

				              }

				               public void deliveryComplete(IMqttDeliveryToken token) {
				                   System.out.println("deliveryComplete---------" + token.isComplete());
				              }

				          });
				           client.connect(options);
				           
				           //System.out.println("topic---------"+topic);
				           client.subscribe(topic, qs);
				           try {
					   			// wait to ensure subscribed messages are delivered
					   			Thread.sleep(5000);
					   			
					   			client.disconnect();
					   		} catch (Exception e) {
					   			e.printStackTrace();
					   		}
				          
				      } catch (Exception e) {
				           e.printStackTrace();
				      }
		    }
	}

		    
		    for(MqttliveEntryTable stt:service.get_mqtt_live_entry())
 			{
                String broker="",st="",src_id="",src_name="",tp="",dt="",topic_value="";
                
                broker = stt.getBroker();
                st = stt.getStatus();
                src_id = stt.getSource_id();
                src_name = stt.getSource_name();
                tp = stt.getTopic_name();
                dt = stt.getDatetime();
                topic_value = stt.getTopic_value();

                String data="{\"broker\":\""+broker+"\",\"src_name\":\""+src_name+"\",\"tp\":\""+tp+"\",\"dt\":\""+dt+"\",\"topic_value\":\""+topic_value+"\"}";

		    	send_data = data;
 			}
		    
		    
			return send_data;
			

		
        
        
    }

}
