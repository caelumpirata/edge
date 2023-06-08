package com.edge.application.views.OPCUA;

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
import com.edge.application.views.Interface.opctreeInterface;
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


@Route(value = "opcview")
//@PWA(name = "example2", shortName = "try2")
public class opcview extends HorizontalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="opcview";

	Div tree,serverData,thread;
	VerticalLayout tree_connection;
	IFrame loadTree;
	
	
	@Autowired
	private EdgeService service;
	
	@Autowired
	opctreeInterface opc;
	
	String SelectedID,connURL="";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
	private ExampleServer exampleServer;

	private clientExample clientExample;
	OpcUaClient client1;
	
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
		
		
		List<SourceOpcuaTable> l=null; 
		l=service.list_source_opcua(para);//.split("-")[0].toString()
		Grid<SourceOpcuaTable> grid=new Grid<>();
		grid.setItems(l);
		grid.addColumn(SourceOpcuaTable::getServer_name).setHeader(a);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addComponentColumn(ConnectionInfo1 -> {
			Button connect=new Button();
			connect.setIcon(new Icon(VaadinIcon.PLUG));
			connect.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//Button tree = new Button();
			//connect.setVisible(false);
			//tree.setVisible(false);
			if(opc.treedata(ConnectionInfo1.getEnd_point_url()))
			{
				//connect.setVisible(true);
				
				connect.addClickListener(event->{
					connURL="tree.html?url="+ConnectionInfo1.getEnd_point_url();
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
			if(!opc.treedata(ConnectionInfo1.getEnd_point_url()))
			{
			
				//tree.setVisible(true);
				
				tree.addClickListener(event->{
					WriteExample writeData = new WriteExample();
					clientExample = writeData;
					try {
						if (serverRequired) {
	
							exampleServer = new ExampleServer();
							exampleServer.startup().get();
						}
					} catch (Exception e) {
					}
	
					
					runBrowse(ConnectionInfo1.getEnd_point_url().toString(),opc);
					
					
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
		
		IFrame i_reload=new IFrame("reload.html");
		i_reload.setWidth("103%");
		i_reload.setHeight("100%");
		dataLive.add(i_reload);
		dataLive.setWidth("55%");
		
		
//		tree_connection.addClassName("spacing");
		i_reload.addClassName("spacing");
		add(tree_connection,dataLive);
		//addClassName("spacing");
		
		
		
		VerticalLayout data=new VerticalLayout();//atributes
		IFrame i_atribute=new IFrame();
		i_atribute.setId("atribute");
		i_atribute.setHeight("100%");
		i_atribute.setWidthFull();
		i_atribute.addClassName("spacing");
		data.add(i_atribute);
		data.setWidth("20%");
		data.setHeightFull();
		//data.addClassName("spacing");
		add(data);
		
		
		
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	
	OpcUaClient createClient(String url) throws Exception {
		   Path securityTempDir = Paths.get(System.getProperty("java.io.tmpdir"), "security");
	        Files.createDirectories(securityTempDir);
	        if (!Files.exists(securityTempDir)) {
	            throw new Exception("unable to create security dir: " + securityTempDir);
	        }
	        LoggerFactory.getLogger(getClass())
	            .info("security temp dir: {}", securityTempDir.toAbsolutePath());

	      
	        SecurityPolicy securityPolicy = clientExample.getSecurityPolicy();

	        List<EndpointDescription> endpoints;

	        try {
	        	endpoints = DiscoveryClient.getEndpoints(url).get();
	        	
//	        	System.out.println("This is enpoints in createClient() in ClientExampleRunner: " + endpoints);
	        } catch (Throwable ex) {
	            // try the explicit discovery endpoint as well
	            String discoveryUrl = url;

//	        	System.out.println("This is url in createClient() in ClientExampleRunner: " + url);
	            
	            if (!discoveryUrl.endsWith("/")) {
	                discoveryUrl += "/";
	            }
	            discoveryUrl += "discovery";
	            
//	        	System.out.println("This is discoveryUrl in createClient() in ClientExampleRunner: " + discoveryUrl);

	            logger.info("Trying explicit discovery URL: {}", discoveryUrl);
	            endpoints = DiscoveryClient.getEndpoints(discoveryUrl).get();
	            
//	            System.out.println("This is endpoints 2 in createClient() in ClientExampleRunner: " + endpoints);       
	        }

	        EndpointDescription endpoint = endpoints.stream()
	            .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getUri()))
	            .filter(clientExample.endpointFilter())
	            .findFirst()
	            .orElseThrow(() -> new Exception("no desired endpoints returned"));

	        logger.info("Using endpoint: {} [{}/{}]",
	            endpoint.getEndpointUrl(), securityPolicy, endpoint.getSecurityMode());

//	        System.out.println("This is above OPCUA config: " + endpoint);
	        KeyStoreLoader loader=new KeyStoreLoader();
	        OpcUaClientConfig config = OpcUaClientConfig.builder()
	            .setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
	            .setApplicationUri("urn:eclipse:milo:examples:client")
	            .setEndpoint(endpoint)
	            .setIdentityProvider(clientExample.getIdentityProvider())
	            .setRequestTimeout(uint(50000))
	            .setKeyPair(loader.getClientKeyPair())
	            .setCertificate(loader.getClientCertificate())
	            .build();
		return OpcUaClient.create(config);
	}
	public String runBrowse(String url,opctreeInterface opctable) {
		String tree="",status="";
		try {
			OpcUaClient client = createClient(url);
			client1 = client;
			future.whenCompleteAsync((c, ex) -> {
				if (ex != null) {
					logger.error("Error running example: {}", ex.getMessage(), ex);
				}

				/*try {
					client.disconnect().get();
					if (serverRequired && exampleServer != null) {
						exampleServer.shutdown().get();
					}
					Stack.releaseSharedResources();
				} catch (InterruptedException | ExecutionException e) {
					logger.error("Error disconnecting:", e.getMessage(), e);
				}*/

				
			});

			try {

				status=initializefun(client, future,opctable,url);

			} catch (Throwable t) {
				logger.error("Error running client example: {}", t.getMessage(), t);
				future.completeExceptionally(t);
			}
		} catch (Throwable t) {
			logger.error("Error getting client: {}", t.getMessage(), t);

			// future.completeExceptionally(t);

			
		}
		
		return status;
	}
	public static String initializefun(OpcUaClient client, CompletableFuture<OpcUaClient> future,opctreeInterface opctable,String url) throws InterruptedException, ExecutionException, Exception {
		StringBuffer html = new StringBuffer();

		long id0 = 1, id1 = 2, id2 = 100, id3 = 1000, id4 = 10000, id5 = 100000, id6 = 1000000, id7 = 10000000;
		List<String> cmdSequence = new ArrayList<String>();
		List<String> list = new ArrayList<>();
		JsonObject obj = new JsonObject();

		 client.connect().get();

		try {

			// NodeId PERMIT_STATE = NodeId.parse("ns=0;i=87");

			Vector browse1 = new Vector();
			Vector browse2 = new Vector();
			Vector browse3 = new Vector();
			Vector browse4 = new Vector();
			Vector browse5 = new Vector();
			Vector browse6 = new Vector();
			Vector browse7 = new Vector();
			Vector browse8 = new Vector();

			browse1 = browse(client, Identifiers.RootFolder, "");

			obj.addProperty("id", String.valueOf(id0));
			obj.addProperty("name", "Root");
			obj.addProperty("text", "Root");
			obj.addProperty("nodeId", Identifiers.RootFolder.toString().trim());
			obj.addProperty("nodeClass", "Object");
			obj.addProperty("type", "Object");
			obj.addProperty("parent", "#");
			
			list.add(obj.toString());
			
			
			opctreetable opc1=new opctreetable();
			opc1.setId(Long.parseLong(String.valueOf(id0)));
			opc1.setName("Root");
			opc1.setText("Root");
			opc1.setNodeId(Identifiers.RootFolder.toString().trim());
			opc1.setNodeClass("Object");
			opc1.setType("Object");
			opc1.setParent(Long.parseLong("0"));
			opc1.setUrl(url);
			opctable.save(opc1);
			
			

			for (int j = 0; j < browse1.size(); j++) {
				nodeid1 = browse1.get(j).toString();

				parts = nodeid1.split("silcore");
				part1 = parts[0]; // 004
				part2 = parts[1]; // 034556
				part3 = parts[2]; // 034556

				obj.addProperty("id", String.valueOf(id1));
				obj.addProperty("name", "" + part3.toString().trim() + "");
				obj.addProperty("text", part1);
				obj.addProperty("nodeId", part3.toString().trim());
				obj.addProperty("nodeClass", part2.toString().trim());
				obj.addProperty("type", part2.toString().trim());
				obj.addProperty("parent", String.valueOf(id0));
				list.add(obj.toString());
				
				
				opctreetable opc2=new opctreetable();
				opc2.setId(Long.parseLong(String.valueOf(id1)));
				opc2.setName( part3.toString().trim());
				opc2.setText( part1);
				opc2.setNodeId( part3.toString().trim());
				opc2.setNodeClass( part2.toString().trim());
				opc2.setType(part2.toString().trim());
				opc2.setParent(Long.parseLong(String.valueOf(id0)));
				opc2.setUrl(url);
				opctable.save(opc2);
				
				 System.out.println("Hello1 " + part3.toString());
				NodeId PERMIT_STATE1 = NodeId.parse(part3);
				System.out.println("PERMIT STATE (j): " + PERMIT_STATE1);
				browse2 = browse(client, PERMIT_STATE1, "");

				for (int k = 0; k < browse2.size(); k++) {
					if (!browse2.isEmpty()) {
						nodeid1 = browse2.get(k).toString();

						parts = nodeid1.split("silcore");
						part1 = parts[0]; // 004
						part2 = parts[1]; // 034556
						part3 = parts[2]; // 034556

						obj.addProperty("id", String.valueOf(id2));
						obj.addProperty("name", "" + part3.toString().trim() + "");
						obj.addProperty("text", part1);
						obj.addProperty("nodeId", part3.toString().trim());
						obj.addProperty("nodeClass", part2.toString().trim());
						obj.addProperty("type", part2.toString().trim());
						obj.addProperty("parent", String.valueOf(id1));
						list.add(obj.toString());
						
						opctreetable opc3=new opctreetable();
						opc3.setId(Long.parseLong(String.valueOf(id2)));
						opc3.setName( part3.toString().trim());
						opc3.setText( part1);
						opc3.setNodeId( part3.toString().trim());
						opc3.setNodeClass( part2.toString().trim());
						opc3.setType(part2.toString().trim());
						opc3.setParent(Long.parseLong(String.valueOf(id1)));
						opc3.setUrl(url);
						opctable.save(opc3);
						
						 System.out.println("Hello2 " + part3.toString());

						NodeId PERMIT_STATE2 = NodeId.parse(part3);
						System.out.println("PERMIT STATE 2: (k) : " + PERMIT_STATE2);
						browse3 = browse(client, PERMIT_STATE2, "");
					}
					for (int m = 0; m < browse3.size(); m++) {
						if (!browse3.isEmpty()) {
							nodeid1 = browse3.get(m).toString();

							parts = nodeid1.split("silcore");
							part1 = parts[0]; // 004
							part2 = parts[1]; // 034556
							part3 = parts[2]; // 034556

							obj.addProperty("id", String.valueOf(id3));
							obj.addProperty("name", "" + part3.toString().trim() + "");
							obj.addProperty("text", part1);
							obj.addProperty("nodeId", part3.toString().trim());
							obj.addProperty("nodeClass", part2.toString().trim());
							obj.addProperty("type", part2.toString().trim());
							obj.addProperty("parent", String.valueOf(id2));
							list.add(obj.toString());
							
							opctreetable opc4=new opctreetable();
							opc4.setId(Long.parseLong(String.valueOf(id3)));
							opc4.setName( part3.toString().trim());
							opc4.setText( part1);
							opc4.setNodeId( part3.toString().trim());
							opc4.setNodeClass( part2.toString().trim());
							opc4.setType(part2.toString().trim());
							opc4.setParent(Long.parseLong(String.valueOf(id2)));
							opc4.setUrl(url);
							opctable.save(opc4);
							
							
							 System.out.println("Hello3 " + part3.toString());

							NodeId PERMIT_STATE3 = NodeId.parse(part3);
							browse4 = browse(client, PERMIT_STATE3, "");
						}

						for (int n = 0; n < browse4.size(); n++) {
							if (!browse4.isEmpty()) {
								nodeid1 = browse4.get(n).toString();

								parts = nodeid1.split("silcore");
								part1 = parts[0]; // 004
								part2 = parts[1]; // 034556
								part3 = parts[2]; // 034556

								obj.addProperty("id", String.valueOf(id4));
								obj.addProperty("name", "" + part3.toString().trim() + "");
								obj.addProperty("text", part1);
								obj.addProperty("nodeId", part3.toString().trim());
								obj.addProperty("nodeClass", part2.toString().trim());
								obj.addProperty("type", part2.toString().trim());
								obj.addProperty("parent", String.valueOf(id3));
								list.add(obj.toString());
								
								
								opctreetable opc5=new opctreetable();
								opc5.setId(Long.parseLong(String.valueOf(id4)));
								opc5.setName( part3.toString().trim());
								opc5.setText( part1);
								opc5.setNodeId( part3.toString().trim());
								opc5.setNodeClass( part2.toString().trim());
								opc5.setType(part2.toString().trim());
								opc5.setParent(Long.parseLong(String.valueOf(id3)));
								opc5.setUrl(url);
								opctable.save(opc5);
								
								 System.out.println("Hello4 " + part3.toString());

								NodeId PERMIT_STATE4 = NodeId.parse(part3);
								browse5 = browse(client, PERMIT_STATE4, "");
							}

							for (int g = 0; g < browse5.size(); g++) {
								if (!browse5.isEmpty()) {
									nodeid1 = browse5.get(g).toString();

									parts = nodeid1.split("silcore");
									part1 = parts[0]; // 004
									part2 = parts[1]; // 034556
									part3 = parts[2]; // 034556

									obj.addProperty("id", String.valueOf(id5));
									obj.addProperty("name", "" + part3.toString().trim() + "");
									obj.addProperty("text", part1);
									obj.addProperty("nodeId", part3.toString().trim());
									obj.addProperty("nodeClass", part2.toString().trim());
									obj.addProperty("type", part2.toString().trim());
									obj.addProperty("parent", String.valueOf(id4));
									list.add(obj.toString());
									
									opctreetable opc6=new opctreetable();
									opc6.setId(Long.parseLong(String.valueOf(id5)));
									opc6.setName( part3.toString().trim());
									opc6.setText( part1);
									opc6.setNodeId( part3.toString().trim());
									opc6.setNodeClass( part2.toString().trim());
									opc6.setType(part2.toString().trim());
									opc6.setParent(Long.parseLong(String.valueOf(id4)));
									opc6.setUrl(url);
									opctable.save(opc6);
									
									 System.out.println("Hello5 " + part3.toString());

									NodeId PERMIT_STATE5 = NodeId.parse(part3);
									browse6 = browse(client, PERMIT_STATE5, "");
								}

								for (int p = 0; p < browse6.size(); p++) {
									if (!browse6.isEmpty()) {
										nodeid1 = browse6.get(p).toString();

										parts = nodeid1.split("silcore");
										part1 = parts[0]; // 004
										part2 = parts[1]; // 034556
										part3 = parts[2]; // 034556

										obj.addProperty("id", String.valueOf(id6));
										obj.addProperty("name", "" + part3.toString().trim() + "");
										obj.addProperty("text", part1);
										obj.addProperty("nodeId", part3.toString().trim());
										obj.addProperty("nodeClass", part2.toString().trim());
										obj.addProperty("type", part2.toString().trim());
										obj.addProperty("parent", String.valueOf(id5));
										list.add(obj.toString());
										
										opctreetable opc7=new opctreetable();
										opc7.setId(Long.parseLong(String.valueOf(id6)));
										opc7.setName( part3.toString().trim());
										opc7.setText( part1);
										opc7.setNodeId( part3.toString().trim());
										opc7.setNodeClass( part2.toString().trim());
										opc7.setType(part2.toString().trim());
										opc7.setParent(Long.parseLong(String.valueOf(id5)));
										opc7.setUrl(url);
										opctable.save(opc7);
										
										 System.out.println("Hello6 " + part3.toString());

										NodeId PERMIT_STATE6 = NodeId.parse(part3);
										browse7 = browse(client, PERMIT_STATE6, "");
									}

									for (int q = 0; q < browse7.size(); q++) {
										if (!browse7.isEmpty()) {
											nodeid1 = browse7.get(q).toString();

											parts = nodeid1.split("silcore");
											part1 = parts[0]; // 004
											part2 = parts[1]; // 034556
											part3 = parts[2]; // 034556

											obj.addProperty("id", String.valueOf(id7));
											obj.addProperty("name", "" + part3.toString().trim() + "");
											obj.addProperty("text", part1);
											obj.addProperty("nodeId", part3.toString().trim());
											obj.addProperty("nodeClass", part2.toString().trim());
											obj.addProperty("type", part2.toString().trim());
											obj.addProperty("parent", String.valueOf(id6));
											list.add(obj.toString());
											 System.out.println("Hello7 " + part3.toString());
											 
											 opctreetable opc8=new opctreetable();
											 opc8.setId(Long.parseLong(String.valueOf(id7)));
											 opc8.setName( part3.toString().trim());
											 opc8.setText( part1);
											 opc8.setNodeId( part3.toString().trim());
											 opc8.setNodeClass( part2.toString().trim());
											 opc8.setType(part2.toString().trim());
											 opc8.setParent(Long.parseLong(String.valueOf(id6)));
											 opc8.setUrl(url);
											opctable.save(opc8);

											NodeId PERMIT_STATE7 = NodeId.parse(part3);

										}
										id7++;
									}

									id6++;
								}

								id5++;
							}

							id4++;
						}
						id3++;
					}
					id2++;
				}
				id1++;

			}
			id0++;

		} finally {
			client.disconnect().get();
		}
		return list.toString();
	}
	
	public static Vector browse(final OpcUaClient client, final NodeId browseRoot, final String indent) {

		Vector val1 = new Vector();

		try {
			final BrowseDescription browse = new BrowseDescription(browseRoot, BrowseDirection.Forward,
					Identifiers.References, true,
					uint(NodeClass.Object.getValue() | NodeClass.Variable.getValue() | NodeClass.View.getValue()
							| NodeClass.DataType.getValue() | NodeClass.Method.getValue()),
					uint(BrowseResultMask.All.getValue()));

			BrowseResult browseResult = client.browse(browse).get();

			// do {

			List<ReferenceDescription> references;// ;
			List<ReferenceDescription> references1;// ;
			references = toList(browseResult.getReferences());
			for (int i = 0; i < references.size(); i++) {
				ReferenceDescription ref = references.get(i);
//				System.out.println("Browser Info: " + indent + ref.getBrowseName().getName() + "silcore" + ref.getNodeClass().toString() + "silcore"
//						+ ref.getNodeId().local().map(NodeId::toParseableString).orElse(""));
				val1.add(indent + ref.getBrowseName().getName() + "silcore" + ref.getNodeClass().toString() + "silcore"
						+ ref.getNodeId().local().map(NodeId::toParseableString).orElse(""));

			}
		} catch (Exception ex) {
		}
		return val1;
	}
	
}
