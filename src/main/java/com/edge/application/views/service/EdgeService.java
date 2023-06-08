package com.edge.application.views.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edge.application.views.Interface.BacnetCOVStatusInterface;
import com.edge.application.views.Interface.BacnetLiveInterface;
import com.edge.application.views.Interface.BacnetTagInterface;
import com.edge.application.views.Interface.BacnetTagUtilityInterface;
import com.edge.application.views.Interface.BacnetTreeInterface;
import com.edge.application.views.Interface.ElementInterface;
import com.edge.application.views.Interface.ElementTagInterface;
import com.edge.application.views.Interface.KafkaInterface;
import com.edge.application.views.Interface.LiveRepo;
import com.edge.application.views.Interface.MQTTTagUtilityInterface;
import com.edge.application.views.Interface.MqttInterface;
import com.edge.application.views.Interface.MqttLiveInterEntryface;
import com.edge.application.views.Interface.MqttLiveInterface;
import com.edge.application.views.Interface.MqttTopicTreeInterface;
import com.edge.application.views.Interface.OPCUATagUtilityInterface;
import com.edge.application.views.Interface.RTUTagUtilityInterface;
import com.edge.application.views.Interface.SourceBacnetInterface;
import com.edge.application.views.Interface.SourceElementInterface;
import com.edge.application.views.Interface.SourceElementTagInterface;
import com.edge.application.views.Interface.SourceInterface;
import com.edge.application.views.Interface.SourceModbusRTUInterface;
import com.edge.application.views.Interface.SourceModbusTCPInterface;
import com.edge.application.views.Interface.SourceMqttInterface;
import com.edge.application.views.Interface.SourceOpcuaInterface;
import com.edge.application.views.Interface.SourceTagMappingInterface;
import com.edge.application.views.Interface.TCPTagUtilityInterface;
import com.edge.application.views.Interface.TagInterface;
import com.edge.application.views.Interface.UserDetailsInterface;
import com.edge.application.views.Interface.UserInterface;
import com.edge.application.views.Interface.opctreeInterface;
import com.edge.application.views.Interface.sourcePublisherInterface;
import com.edge.application.views.Interface.subscribeLiveRepo;
import com.edge.application.views.Table.BacnetCOVStatusTable;
import com.edge.application.views.Table.BacnetLiveTable;
import com.edge.application.views.Table.BacnetTagTable;
import com.edge.application.views.Table.BacnetTagUtilityTable;
import com.edge.application.views.Table.BacnetTreeTable;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MQTTTagUtilityTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.MqttLiveTable;
import com.edge.application.views.Table.MqttTopicTreeTable;
import com.edge.application.views.Table.MqttliveEntryTable;
import com.edge.application.views.Table.OPCUATagUtilityTable;
import com.edge.application.views.Table.RTUTagUtilityTable;
import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceElementTagTable;
import com.edge.application.views.Table.SourceModbusRTUTable;
import com.edge.application.views.Table.SourceModbusTCPTable;
import com.edge.application.views.Table.SourceMqttlTable;
import com.edge.application.views.Table.SourceOpcuaTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.SourceTagMappingTable;
import com.edge.application.views.Table.TCPTagUtilityTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.sourcePublisherTable;
import com.edge.application.views.Table.userTable;
@Service
public class EdgeService {
	
	@Autowired
	SourceInterface source;
	
	@Autowired
	UserInterface users;
	
	@Autowired
	UserDetailsInterface usersdetails;
	
	@Autowired
	MqttInterface mqtt;
	
	@Autowired
	KafkaInterface kafka;
	
	@Autowired
	sourcePublisherInterface sourcePub;
	
	@Autowired
	ElementInterface element;
	
	@Autowired
	TagInterface tag;
	
	@Autowired
	ElementTagInterface element_tag;
	
	@Autowired
	SourceElementInterface source_element;
	
	@Autowired
	SourceElementTagInterface source_ele_tag;
	
	@Autowired
	SourceModbusRTUInterface source_modbus_rtu;
	
	@Autowired
	SourceModbusTCPInterface source_modbus_tcp;
	
	@Autowired
	SourceOpcuaInterface source_opcua;
	
	@Autowired
	SourceBacnetInterface source_bacnet;
	
	@Autowired
	BacnetTreeInterface bacnet_tree;
	
	@Autowired
	BacnetCOVStatusInterface bacnet_cov;
	
	@Autowired
	SourceMqttInterface source_mqtt;
	//tannu start
	@Autowired
	SourceTagMappingInterface source_tag;
	
	@Autowired
	opctreeInterface opc;
	
	@Autowired
	LiveRepo live;
	
	@Autowired
	subscribeLiveRepo sublive;
	
	@Autowired
	BacnetLiveInterface bacnet_live;
	
	@Autowired
	MqttTopicTreeInterface mqtt_tree;
	@Autowired
	MqttLiveInterface mqtt_live;
	
	@Autowired
	MqttLiveInterEntryface mqtt_live_entry;
	
	@Autowired
	BacnetTagInterface bacnet_tag;
	
	@Autowired
	OPCUATagUtilityInterface opcua_utility;
	
	@Autowired
	MQTTTagUtilityInterface mqtt_utility;
	
	@Autowired
	BacnetTagUtilityInterface bacnet_utility;
	
	@Autowired
	RTUTagUtilityInterface rtu_utility;
	
	@Autowired
	TCPTagUtilityInterface tcp_utility;
	
	//tannu end
	/**********************  SAVE CODE******************/
	public void source_save(SourceTable st) { source.save(st); }
	
	public void user_save(userTable ut) { users.save(ut); }
	
	public void user_permission_save(UserDetailsTable ut) { usersdetails.save(ut); }
	
	public void mqtt_save(MqttConfigurationTable ut) { mqtt.save(ut); }
	
	public void kafka_save(KafkaConfigurationTable ut) { kafka.save(ut); }
	
	public void source_pub_save(sourcePublisherTable ut) { sourcePub.save(ut); }
	
	public void element_save(ElementTable ut) { element.save(ut); }
	public void tag_save(TagTable ut) { tag.save(ut); }
	
	public void element_tag_save(ElementTagTable ut) { element_tag.save(ut); }
	
	public void source_element_save(SourceElementTable ut) { source_element.save(ut); }
	
	public void source_element_tag_save(SourceElementTagTable ut) { source_ele_tag.save(ut); }
	
	public void source_modbus_rtu_save(SourceModbusRTUTable ut) { source_modbus_rtu.save(ut); }
	public void source_modbus_tcp_save(SourceModbusTCPTable ut) { source_modbus_tcp.save(ut); }
	
	public void source_opcua_save(SourceOpcuaTable ut) { source_opcua.save(ut); }
	
	public void source_bacnet_save(SourceBacnetTable ut) { source_bacnet.save(ut); }
	
	public void bacnet_tree_save(BacnetTreeTable ut) { bacnet_tree.save(ut); }
	
	public void bacnet_cov_save(BacnetCOVStatusTable ut) { bacnet_cov.save(ut); }
	
	public void source_mqtt_save(SourceMqttlTable ut) { source_mqtt.save(ut); }
	
	public void source_tag_save(SourceTagMappingTable st) {source_tag.save(st);}
	
	public void bacnet_live_save(BacnetLiveTable bt) {bacnet_live.save(bt);}
	
	public void mqtt_tree_save(MqttTopicTreeTable bt) {mqtt_tree.save(bt);}
	
	public void mqtt_live_save(MqttLiveTable bt) {mqtt_live.save(bt);}
	
	public void mqtt_live_entry_save(MqttliveEntryTable st) { mqtt_live_entry.save(st); }
	
	public void bacnet_tag_save(BacnetTagTable st) { bacnet_tag.save(st); }
	
	public void opcua_utility_save(OPCUATagUtilityTable op) {opcua_utility.save(op);}
	
	public void mqtt_utility_save(MQTTTagUtilityTable mq) {mqtt_utility.save(mq);}
	
	public void bacnet_utility_save(BacnetTagUtilityTable bt) {bacnet_utility.save(bt);}
	
	public void rtu_utility_save(RTUTagUtilityTable bt) {rtu_utility.save(bt);}
	
	public void tcp_utility_save(TCPTagUtilityTable bt) {tcp_utility.save(bt);}
	
	
	/**********************  End CODE ******************/
	
	/**************** Source Query *****************/
	
	public List<SourceTable> source_id_list(){ return source.source_id(); } 
	
	// auto increment 0001
	public List<SourceTable> source_id_next(){ return source.source_id_next(); }
	public List<SourceTable> source_list(){return source.findAll();}
	
	public void deleteSource(long id) { source.deleteById(id); } // delete source for main id
	
	public long get_main_source_id(String id) { return source.get_main_source_id(id);}
	
	public List<SourceTable> source_list_id(long id){ return source.source_id_list(id); } // edit form 
	
	public boolean check_Source(String name) { return source.check_source(name);} // source check alreadu exit or not
	public boolean check_Source_id(String id) { return source.check_sourceID(id);}
	
	 public String getPublisherType(String src_id) {return sourcePub.get_PublisherType(src_id);}
	 
	 public void update_protocol(String id,String type) { source.update_protocol(id,type); } 
	 public void update_source_id(String src_id,long id) { source.update_source_id(src_id,id); } 
	 public String getprotocol(String id)
	 {
		 return source.getprotocol(id);
	 }
	/**************** User Query *****************/
	
    public List<userTable> user_id_list(){ return users.user_id(); } // auto increment 0001
	
	public List<userTable> user_list(){return users.findAll();}
	
	public void deleteUser(long id) { users.deleteById(id); } // delete source for main id
	
	public List<userTable> user_list_id(long id){ return users.user_id_list(id); } // edit form 
	
	public boolean check_User(String name) { return users.check_user(name);} // source check alreadu exit or not
	
	
	
	 public String get_pre_userid(long id) { return users.get_user_id(id);}
	 
	 public String getUserType(String usr_id) {return usersdetails.usertype(usr_id);}

	/**************** Users Permission Form *****************/
	 public List<UserDetailsTable> user_permission_list(){ return usersdetails.user_permission_list(); } 
	 public List<userTable> userType_list(){ return users.user_id_name(); } 
	 public boolean check_User_id(String id) { return usersdetails.check_user_id(id);}
	 public String get_id(String id) { return usersdetails.get_id(id);}
	 public String get_user_name(String id) { return usersdetails.get_user(id);}
	 
	 public String get_user_name(long id) { return usersdetails.get_user(id);}
	 public String get_user_Type(long id) { return usersdetails.get_type(id);}
	 public String get_user_id(long id) { return usersdetails.get_user_id(id);}
	 public void deleteUserPermission(long id) { usersdetails.deleteById(id); }
	 
		/**************** UsersLOck Unlock Form *****************/
	 public boolean get_User_id(String id) { return users.check_exist_id(id);}
	 public String get_userid(String id) { return users.get_userid(id);}
	 
	 public void getupdatestatus(long id,String user_id,String status) {users.update_user(id, user_id, status);}
	 
	 /**************** Users password changed*****************/
	 public void getupdatepassword(long id,String password,String pt_password) {users.getupdatepassword(id, password, pt_password);}

	 
	 /**************** mqtt Query *****************/
		
		public List<MqttConfigurationTable> mqtt_id_list(){ return mqtt.mqtt_id(); } // auto increment 0001
		public List<MqttConfigurationTable> mqtt_list_id(long id){ return mqtt.id_list(id); }
		public List<MqttConfigurationTable> mqtt_list(){return mqtt.findAll();}
		public void deleteMqtt(long id) { mqtt.deleteById(id); } // delete source for main id
		public boolean check_broker_surce(String broker,String mid) { return mqtt.check_broker_surce(broker,mid);}
		
/**************** kafka Query *****************/
		
		public List<KafkaConfigurationTable> kafka_id_list(){ return kafka.kafka_id(); } // auto increment 0001
		public List<KafkaConfigurationTable> kafka_list_id(long id){ return kafka.id_list(id); }
		public List<KafkaConfigurationTable> kafka_list(){return kafka.findAll();}
		public void deleteKafka(long id) { kafka.deleteById(id); } // delete source for main id
		public boolean check_broker_kafka(String broker,String mid) { return kafka.check_broker_kafka(broker,mid);}

		
/**************** Source Publisher Query *****************/
		 public List<sourcePublisherTable> source_id(){ return sourcePub.source_id(); } 
		public List<sourcePublisherTable> source_publisher_id(String id){ return sourcePub.source_id_get(id); } // auto increment 0001
		public List<sourcePublisherTable> list_source_id(String id){ return sourcePub.list_source_id(id); }
		public void deleteSourcePublisher(String id) { sourcePub.deleteRecord(id); }
		public void deleteSource_publisher(long id) { sourcePub.deleteById(id); }// delete source for main id
		public long get_main_id(String id) { return sourcePub.get_main_id(id);}
		public boolean check_source_id(String name,String id) { return sourcePub.check_source_id_name(name,id);}
		public boolean check_source_id_only(String id) { return sourcePub.check_source_id_only(id);}
		public boolean check_Source_id_publisher(String id) { return sourcePub.check_Source_id_publisher(id);}
		
		public String get_Source_name(long id) { return sourcePub.getSource_name(id); }
		public String get_Source_name(String id) { return sourcePub.getSource_name(id); }
		public String get_Source_id(long id) { return sourcePub.getSource_id(id); }
		
		public String get_Publisher_type(String id) { return sourcePub.getPublisher_type(id); }
		
		public String get_Publisher_id(String id) { return sourcePub.getPublisher_id(id); }
		
		/***************************** Element Query*************/
		 public List<ElementTable> element_list(){ return element.element_id_list(); } 
		 public List<ElementTable> element_list_id(long id){ return element.element_id_list(id); } 
		 public boolean check_Element_id(String id) { return element.check_element_id(id);}
		 public void deleteElement(long id) { element.deleteById(id); } 
		 public List<ElementTable> elementList(){return element.findAll();}
		 public String  element_name(String id){ return element.element_name(id); } 
		
		 public String  element_main_id(String id){ return element.element_main_id(id); } 
		
		 
		 /***************************** Tag Query*************/
		 public List<TagTable> tag_list(){ return tag.tag_id_list(); } 
		 public List<TagTable> tag_list_id(long id){ return tag.tag_id_list(id); } 
		 public boolean check_Tag_id(String id) { return tag.check_tag_id(id);}
		 public void deleteTag(long id) { tag.deleteById(id); }
		 public List<TagTable> tagList(){return tag.findAll();}
		 
		
		 
		 /***************************** Element Tag Mapping Query*************/
		 public List<ElementTagTable> element_tag_list(){ return element_tag.element_tag_id_list(); } 
		 public List<ElementTagTable> element_tag_list_id(long id){ return element_tag.element_tag_id_list(id); } 
		 public boolean check_element_Tag_id(String id) { return element_tag.check_element_tag_id(id);}
		 public void deleteElementTag(long id) { element_tag.deleteById(id); }
		 public List<ElementTagTable> element_tagList(){return element_tag.findAll();}
		 public void deleteMapElement(String id) { element_tag.deleteMapElement(id); } 
		 
		 /***************************** Source Element  Mapping Query*************/
		 public List<SourceElementTable> source_element_list(){ return source_element.source_element_id_list(); } 
		 public List<SourceElementTable> source_element_list_id(long id){ return source_element.source_element_id_list(id); } 
		 public boolean check_source_element_id(String id,String sid) { return source_element.check_source_element_id(id,sid);}
		 public void deleteSourceElement(long id) { source_element.deleteById(id); }
		 public List<SourceElementTable> source_elementList(){return source_element.findAll();}
		 public void deleteMapSourceElement(String id) { source_element.deleteMapSourceElement(id); } 
		 
		 public List<SourceElementTable> get_source_element(String source_id,String source_name)
		 {
			 return source_element.get_source_element(source_id,source_name);
		 }
		 
		 /************************* Source Element Tag Mapping Query ***********************/
		 public List<TagTable> get_grid1()
		 {
			 return tag.get_grid();
		 }
		 
		 public boolean check_tag_source(String source_id,String tag_id,String elem_id)
		 {
			 return source_ele_tag.check_tag_source(source_id, tag_id,elem_id);
		 }
		 public List<SourceElementTagTable> source_element_tag_list(){ return source_ele_tag.source_element_tag_id_list(); } 
		 public void deleteMapSourceElementTag(String id) { source_ele_tag.deleteMapSourceElementTag(id); } 
		 public void deleteSourceElementTag(long id) { source_ele_tag.deleteById(id); }
		 public void deleteMap_SourceElementTag(String sid) { source_ele_tag.deleteMap_SourceElementTag(sid); } 
		 public boolean check_source_tag_element_id_only(String sid) { return source_ele_tag.check_source_tag_element_id_only(sid);}
		 
		 /*************************Modbus RTU Protocol ************/
		 public List<SourceModbusRTUTable> source_port_list(){return source_modbus_rtu.findAll();}
		 public boolean check_source_modbus_rtu(String id) { return source_modbus_rtu.check_source_modbust_rtu(id);}
		 public void deleteSource_modbus_rtu(String sid) { source_modbus_rtu.deleteSource_modbus_rtu(sid); }
		 public boolean check_source_comport(String sid) { return source_modbus_rtu.check_source_comport(sid);}
		 public void deleteSourcePort(long id) { source_modbus_rtu.deleteById(id); }
		 public List<SourceModbusRTUTable> source_port_list(String id){return source_modbus_rtu.list_source_modbust_rtu(id);}
		  /* ***********************/
		 
		 /*************************Modbus TCP Protocol ************/
		 public List<SourceModbusTCPTable> source_tcp_list(){return source_modbus_tcp.findAll();}
		 public boolean check_source_modbus_tcp(String id) { return source_modbus_tcp.check_source_modbust_tcp(id);}
		 public void deleteSource_modbus_tcp(String sid) { source_modbus_tcp.deleteSource_modbus_tcp(sid); }
		 public boolean check_source_tcp_port(String sid) { return source_modbus_tcp.check_source_tcp_port(sid);}
		 public boolean check_source_modbust_tcp(String sid) { return source_modbus_tcp.check_source_modbust_tcp(sid);}
		 public boolean check_source_tcp(String sid) { return source_modbus_tcp.check_source_tcp(sid);}
		 public void deleteSource_modbus_tcp(long id) { source_modbus_tcp.deleteById(id); }
		 public List<SourceModbusTCPTable> source_port_list_tcp(String id){return source_modbus_tcp.list_source_modbust_tcp(id);}

		  /* ***********************/
		 
		 /*************************Opcua Protocol ************/
		 public List<SourceOpcuaTable> source_opcua_list(){return source_opcua.findAll();}
		 public boolean check_source_opcua(String id) { return source_opcua.check_source_opcua(id);}
		 public void deleteSource_opcua(String sid) { source_opcua.deleteSource_opcua(sid); }
		 public boolean check_source_opcua_port(String sid) { return source_opcua.check_source_opcua_port(sid);}
		 public boolean check_source_opcua_endpoint_url(String sid) { return source_opcua.check_source_opcua_endpoint_url(sid);}
		 public void deleteSource_opcua(long id) { source_opcua.deleteById(id); }
		 public List<SourceOpcuaTable> list_source_opcua(String id){return source_opcua.list_source_opcua(id);}
		 public String getEndpointurl(String source_id,String source_name) {return source_opcua.getEndpoint(source_id,source_name);}
		 public boolean check_sourceOPCUA(String id) {return source_opcua.check_sourceOPCUA(id);}
		 public void update_opc(String enpoint,String port,String policy,String setting,long id)
		 {
			 source_opcua.update_opc(enpoint, port, policy, setting, id);
		 }
		 public String geturl(String source_id) {return source_opcua.geturl(source_id);}
		  /* ***********************/
		 
		 /*************************Bacnet Protocol ************/
		 public List<SourceBacnetTable> bacnet_source_id(){return source_bacnet.findAll();}
		 public List<SourceBacnetTable> bacnet_id_list(String id){ return source_bacnet.bacnet_id_list(id); } 
		 public boolean check_source_bacnet(String id,String ip) { return source_bacnet.check_source_bacnet(id,ip);}
		 public void deleteSource_bacnet(String sid) { source_bacnet.deleteSource_bacnet(sid); }
		 public boolean check_source_bacnet(String id) { return source_bacnet.check_source_bacnet(id);}

		 public String  bacnet_server_name(String id){ return source_bacnet.bacnet_server_name(id); } 
		 public String  bacnet_source_name(String id){ return source_bacnet.bacnet_source_name(id); } 
		 public String  bacnet_broadcast_ip(String id,String server_name){ return source_bacnet.bacnet_broadcast_ip(id,server_name); } 
		 public void deleteSource_bacnet(long id) { source_bacnet.deleteById(id); }
		 public List<SourceBacnetTable> list_source_bacnet(String id){return source_bacnet.list_source_bacnet(id);}
		 public String get_broadcast_ip(String name) {return source_bacnet.get_broadcast_ip(name);}
		 public String get_broadcast_port(String name) {return source_bacnet.get_broadcast_port(name);}
		  /* ***********************/
		 /*********** Bacnet Tree ------*/
		 public void truncate_bacnet_tree() { bacnet_tree.truncate_bacnet_tree(); }
		 public Vector getAllSource(String device_id,String broadcast_ip,String server_name) { return bacnet_tree.getAllSource(device_id,broadcast_ip,server_name); }
		 public boolean check_server_bacnet(String server_name,String ip) { return bacnet_tree.check_server_bacnet(server_name,ip);}

		 public void truncate_bacnet_server(String server_name,String ip) { bacnet_tree.truncate_bacnet_server(server_name,ip); }
		 public boolean check_server(String server_name,String broadacast_ip) {return bacnet_tree.check_server(server_name,broadacast_ip);}
		 
		 public List<BacnetTreeTable>  tree_bacnet(String serve_name){ return bacnet_tree.get_tree(serve_name);}
		 public long perent_id(String txt) {	 return bacnet_tree.perent_id(txt); }
		 
		 public String get_server_name_bacnet(String source_id)
		 {
			 return bacnet_tree.get_server_name_bacnet(source_id);
		 }
		 /* ***********************/
		 
		 /*************************Source Mqtt Protocol ************/
		 public List<SourceMqttlTable> mqtt_source_id(){return source_mqtt.findAll();}
		 public boolean check_source_mqtt(String id) { return source_mqtt.check_source_mqtt(id);}
		 public void deleteSource_mqtt(String sid) { source_mqtt.deleteSource_mqtt(sid); }
		 public boolean check_source_mqtt_port(String sid) { return source_mqtt.check_source_mqtt_port(sid);}
		 public boolean check_source_mqtt_endpoint_url(String sid) { return source_mqtt.check_source_mqtt_endpoint_url(sid);}
		 public void delete_source_mqtt(long id) { source_mqtt.deleteById(id); }
		 public List<SourceMqttlTable> list_source_mqtt(String id){return source_mqtt.list_source_mqtt(id);}
		 public long perent_id_source_mqtt(String txt)
		 {
			 return source_mqtt.perent_id_source_mqtt(txt);
		 }
		 public String get_source_name_mqtt(String source_id)
		 {
			 return source_mqtt.getSource_name(source_id);
		 }
		 public String get_brokerip(String source_id)
		 {
			 return source_mqtt.getBrokerip(source_id);
		 }
		 public String get_port(String source_id)
		 {
			 return source_mqtt.getPort(source_id);
		 }
		  /* ***********************/
		 /*********** Bacnet COV Status ------*/
		 public void truncate_bacnet_cov_status() { bacnet_cov.truncate_bacnet_cov_status(); }
		 public String check_cov_obj(String server_name,String broadacast_ip,String obj_name) {return bacnet_cov.check_cov_obj(server_name,broadacast_ip,obj_name);}
		 	
		 

		 /********************************source Tag Mapping Table ******************************/
		 public List<SourceTagMappingTable> source_finadAll(){return source_tag.findAll();}
		 
		 public List<SourceTagMappingTable> source_id_finadAll(String id){return source_tag.source_id_finalList(id);}
		 public void update_sourcetagmapping(String tag_name,String add,String len,String datat,String unit,String element,String point,long id){ source_tag.update_sourcetagmapping(tag_name, add, len, datat, unit,element,point, id); }

		public void DeleteBySource_Tag(long id) {source_tag.deleteById(id);}
		 public boolean check_source_tag_element(String id,String name,String element_name,String tag_name) {return source_tag.check_source_tag_element(id,name,element_name,tag_name);}
		 public boolean check_source_mapping_tagid(String id) {return source_tag.check_source_mapping_tagid(id);}

		 /******************************/
		
		/************************* OPC UA**********************************************/
		public boolean opc_tree_check(String url){	return opc.treedata(url);}
		public void opcua_delete(String url){	opc.deletetableurl(url);}
		/*********************************************************/
		
		/****************************** ListRepo **********************/
		public void update_live_url(String newstr,String oldstr){	live.update_url(newstr, oldstr);}
		public void dalete_url_live(String url){	live.delete_url(url);}
		/***************************************/
		
		/************************ subscription live repo******************/
		public void delete_sub_live(String url){	sublive.delete_url(url);}
		/*************************************/
		/****************** Bacnet Live *****************************/
		public List<BacnetLiveTable>  getLive_bacnet(String server_name,String source){	return bacnet_live.get_live(server_name,source);}
		public void bac_delete_live() {bacnet_live.deleteAll();}
		
		public void update_live(String ser_nm,String sou,String tag){	bacnet_live.update_status_live(ser_nm, sou, tag);}
		
		public List<BacnetLiveTable>  live_bacnet(){	return bacnet_live.findAll();}
		
		/**************************************/
		/****************** mqtt Live *****************************/
		public List<MqttLiveTable>  getLive_mqtt(String source_d)
		{
			return mqtt_live.get_live(source_d);
		}
		public void mqtt_delete_live() {mqtt_live.deleteAll();}
		
		public void mqtt_live(String source_id,String topic)
		{
			mqtt_live.update_status_live(source_id,topic);
		}
		
		public void delete_live_mqtt_tree(String source_id,String topic) {
			mqtt_live.delete_live_mqtt_tree(source_id,topic);
		}
		
		public List<MqttLiveTable>  live_mqtt()
		{
			return mqtt_live.findAll();
		}
		
		public String mqtt_topic_status(String id)
		{
			return mqtt_live.mqtt_topic_status(id);
		}
		
		/**************************************/
		/************************* Mqtt**********************************************/
		public boolean check_mqtt_url(String url,String port,String id)
		{
			return mqtt_tree.check_mqtt_url(url,port,id);
		}
		public boolean check_mqtt_topic(String name,String url,String port,String id)
		{
			return mqtt_tree.check_mqtt_topic(name,url,port,id);
		}
		
		public boolean check_mqtt_tree_srcid(String id)
		{
			return mqtt_tree.check_mqtt_tree_srcid(id);
		}
		public void deleteTopicTree_mqtt(String url,String port,String id)
		{
			mqtt_tree.deleteTopicTree_mqtt(url,port,id);
		}
		public List<MqttTopicTreeTable> getlistname(String name)
		{
			return mqtt_tree.getlistname(name);
		}
		
		 public List<MqttTopicTreeTable> mqtt_topic_id(){return mqtt_tree.mqtt_topic_id();}

		 public List<MqttTopicTreeTable> list_source_mqtt_tree(String id){return mqtt_tree.list_source_mqtt_tree(id);}
		/*********************************************************/
		 /**************** mqtt Query *****************/
			
			public List<MqttliveEntryTable> get_mqtt_live_entry(){ return mqtt_live_entry.get_mqtt_live_entry(); } // auto increment 0001
			public void delete_mqtt_entry_live(String id) { mqtt_live_entry.delete_mqtt_entry_live(id); } // delete source for main id
			
			public boolean check_mqtt_live_entry(String id)
			{
				return mqtt_live_entry.check_mqtt_live_entry(id);
			}
			/************* Bacnet Tag Entery*****************??????????*/
			
			
			public boolean check_bacnet_obj(String id,String server_name,String broadacast_ip,String obj_name,String oid)
			{
				return bacnet_tag.check_bacnet_obj(id,server_name,broadacast_ip,obj_name,oid);
			}
			
			 public void delete_bacnet_tag(long id) { bacnet_tag.deleteById(id); }
			public List<BacnetTagTable> bacnet_tag_list(String device_id){ return bacnet_tag.bacnet_tag_list(device_id); } 
			/**********/
			
			/**************************** OPCUA Tag Utility Mapping***********************/
			public List<OPCUATagUtilityTable> list_opcua(String url)
			{
				return opcua_utility.list_opcua(url);
			}
			public void delete_opc_utility(long id)
			{
				opcua_utility.deleteById(id);
			}
			public boolean check_opc_utility(String nodeid,String url,String topic)
			{
				return opcua_utility.check_opc_utility(nodeid, url,topic);
			}
			public boolean check_opc_utility_url(String id)
			{
				return opcua_utility.check_opc_utility_url(id);
			}
			
			/**************************** MQTT Tag Utility Mapping***********************/
			public List<MQTTTagUtilityTable> list_mqtt(String url)
			{
				return mqtt_utility.list_mqtt(url);
			}
			public void delete_mqtt_utility(long id)
			{
				mqtt_utility.deleteById(id);
			}
			public boolean check_mqtt_utility(String tag,String source_id,String topic,String port,String broker)
			{
				return mqtt_utility.check_mqtt_utility(tag, source_id,topic,port,broker);
			}
			public boolean check_mqtt_utility_source(String id)
			{
				return mqtt_utility.check_mqtt_utility_source(id);
			}
			/**************************** Bacnet Tag Utility Mapping***********************/
			public List<BacnetTagUtilityTable> list_bacnet(String url)
			{
				return bacnet_utility.list_bacnet(url);
			}
			public void delete_bacnet_utility(long id)
			{
				bacnet_utility.deleteById(id);
			}
			
			public boolean check_bacnet_utility(String ip,String port,String topic,String server_name,String obj_id,String obj_name,String id)
			{
				return bacnet_utility.check_bacnet_utility(ip, port,topic,server_name,obj_id,obj_name,id);
			}
			
			public boolean check_bacnet_utility_source(String id,String ip,String port,String server_name)
			{
				return bacnet_utility.check_bacnet_utility_source(id,ip,port,server_name);
			}
			
			/**************************** RTU Tag Utility Mapping***********************/
			public List<RTUTagUtilityTable> list_rtu(String url)
			{
				return rtu_utility.list_rtu(url);
			}
			public void delete_rtu_utility(long id)
			{
				rtu_utility.deleteById(id);
			}
			public boolean check_rtu_utility(String tagname,String id)
			{
				return rtu_utility.check_rtu_utility(tagname,id);
			}
			
			public boolean check_rtu_utility_src_id(String id)
			{
				return rtu_utility.check_rtu_utility_src_id(id);
			}
			
			/**************************** TCP Tag Utility Mapping***********************/
			public List<TCPTagUtilityTable> list_tcp(String url)
			{
				return tcp_utility.list_tcp(url);
			}
			public void delete_tcp_utility(long id)
			{
				tcp_utility.deleteById(id);
			}
			
			public boolean check_tcp_utility(String tagname,String id)
			{
				return tcp_utility.check_tcp_utility(tagname,id);
			}
			
			public boolean check_tcp_utility_src_id(String id)
			{
				return tcp_utility.check_tcp_utility_src_id(id);
			}

}
