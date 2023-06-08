package com.edge.application.views.MqttView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static org.eclipse.milo.opcua.stack.core.util.ConversionUtil.toList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.milo.examples.server.ExampleServer;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalpetri.netty.fsm.Event.Connect;
import com.edge.application.views.Interface.MqttTopicTreeInterface;
import com.edge.application.views.Interface.opctreeInterface;
import com.edge.application.views.Table.MqttTopicTreeTable;
import com.edge.application.views.Table.SourceMqttlTable;
import com.edge.application.views.Table.SourceOpcuaTable;
import com.edge.application.views.Table.opctreetable;
import com.edge.application.views.service.EdgeService;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;


@Route(value = "mqttview")
//@PWA(name = "example2", shortName = "try2")
public class MqttView extends HorizontalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="mqttview";

	Div tree,serverData,thread;
	VerticalLayout tree_connection;
	IFrame loadTree;
	
	
	@Autowired
	private EdgeService service;
	
	@Autowired
	MqttTopicTreeInterface mqtt;
	
	String SelectedID,connURL="";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private boolean serverRequired;
	
	public static String opcurl = "";
	public static String part1 = "", part2 = "", part3 = "", nodeid1 = "";
	public static String[] parts = null;

	
	//@PostConstruct
	public void init(String para) {

//		System.out.println("OPC View Para..."+para);
		tree_connection=new VerticalLayout();//left side window
		

		loadTree=new IFrame();
		loadTree.setSrc(connURL);
		loadTree.getStyle().set("margin-top", "-11px");
		
		setHeight("100%");
		Anchor a=new Anchor("/serverInfo", "Server");
		
		
		List<SourceMqttlTable> l=null; 
		l=service.list_source_mqtt(para.split("-")[0].toString());
		Grid<SourceMqttlTable> grid=new Grid<>();
		grid.setItems(l);
		grid.addColumn(SourceMqttlTable::getSource_name).setHeader(a);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addComponentColumn(ConnectionInfo1 -> {
			Button connect=new Button();
			connect.setIcon(new Icon(VaadinIcon.PLUG));
			connect.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//Button tree = new Button();
			//connect.setVisible(false);
			//tree.setVisible(false);
			if(mqtt.treedata(ConnectionInfo1.getBrokerIP(),ConnectionInfo1.getPort()))
			{
				//connect.setVisible(true);
				
				connect.addClickListener(event->{
					connURL="MQTTTree.html?url="+ConnectionInfo1.getSource_id();
					System.out.println(connURL);
					loadTree.setSrc(connURL);
					
				});
				
			}
			return connect;
//			else
//			{
//				tree.setVisible(true);
//				tree.setIcon(new Icon(VaadinIcon.FILE_ADD));
//				tree.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//				tree.addClickListener(event->{
//					WriteExample writeData = new WriteExample();
//					clientExample = writeData;
//					try {
//						if (serverRequired) {
//
//							exampleServer = new ExampleServer();
//							exampleServer.startup().get();
//						}
//					} catch (Exception e) {
//					}
//
//					
//					
//					connect.setVisible(true);
//					tree.setVisible(false);
//					runBrowse(ConnectionInfo.getEnd_point_url().toString(),opc);
//					
//					
//				});
//				return tree;
//			}
			
			
			
		}).setWidth("30%");
		grid.addComponentColumn(ConnectionInfo1 -> {
			Button tree = new Button();
			tree.setIcon(new Icon(VaadinIcon.FILE_ADD));
			tree.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//tree.setVisible(false);
			if(!mqtt.treedata(ConnectionInfo1.getBrokerIP(),ConnectionInfo1.getPort()))
			{
			
				//tree.setVisible(true);
				
				tree.addClickListener(event->{
				
					
				
					
					
				});
			}
			return tree;
			
		}).setWidth("30%"); //add is button
		
		
		grid.setHeight("50%");
		grid.setWidth("111%");
		grid.addClassName("spacing");
		String i=tree_connection.getMaxHeight();
		System.out.println(i);
		addClassName("bgcolor1"
				+ "");
		
		loadTree.setHeight("70%");
		loadTree.setWidth("107%");
			
//		grid.addClassName("spacing");
		loadTree.addClassName("spacing");
		tree_connection.add(grid);
		tree_connection.add(loadTree);
		tree_connection.setWidth("25%");
		VerticalLayout dataLive=new VerticalLayout();//middle window
		
		IFrame i_reload=new IFrame("reloadMqtt.html");
		i_reload.setWidth("103%");
		i_reload.setHeight("100%");
		dataLive.add(i_reload);
		dataLive.setWidth("75%");
		
		
//		tree_connection.addClassName("spacing");
		i_reload.addClassName("spacing");
		add(tree_connection,dataLive);
		//addClassName("spacing");
		
		
		
//		VerticalLayout data=new VerticalLayout();//atributes
//		IFrame i_atribute=new IFrame();
//		i_atribute.setId("atribute");
//		i_atribute.setHeight("100%");
//		i_atribute.setWidthFull();
//		i_atribute.addClassName("spacing");
//		data.add(i_atribute);
//		data.setWidth("20%");
//		data.setHeightFull();
//		//data.addClassName("spacing");
//		add(data);
		
		
		
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	
	
	
	
}
