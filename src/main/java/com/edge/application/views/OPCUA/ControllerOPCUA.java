package com.edge.application.views.OPCUA;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.ServerState;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import org.eclipse.milo.examples.server.ExampleServer;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.Interface.LiveRepo;
import com.edge.application.views.Interface.opctreeInterface;
import com.edge.application.views.OPCUA.KeyStoreLoader;
import com.edge.application.views.OPCUA.clientExample;
import com.edge.application.views.Table.LiveEntity;
import com.edge.application.views.Table.opctreetable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

@Controller
public class ControllerOPCUA {
	
	
	@Autowired
	opctreeInterface opctable;
	
	@Autowired
	LiveRepo live;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
	private ExampleServer exampleServer;

	private clientExample clientExample;
	OpcUaClient client1;
	
	private boolean serverRequired;
	
	
	 /*Tannu Save tree database get main tree opc 2021-02-13*/
  	@RequestMapping("browsetagopc")
  	@ResponseBody
  	public String viewHomePage1(Model m,HttpServletRequest rec) {
  		String url=rec.getParameter("url");
  		System.out.println("URL11....."+url);
  	jsontree(m,url);
  	//System.out.println(m.getAttribute("treeproduct"));
  	return m.getAttribute("treeproduct").toString();
  	}
    
  	private void jsontree(Model m,String url) {
  		//TODO Auto-generated method stub
  		System.out.println("URL....."+url);
  		List<opctreetable> treedata = opctable.getTree(url);
  		String jsonStr = null;
  		ObjectMapper Obj = new ObjectMapper();  
  		
  		  try { 
  			  
  	            // get Oraganisation object as a json string 
  	        	jsonStr = Obj.writeValueAsString(treedata); 
  	        	jsonStr=jsonStr.replace("\"parent\":0", "\"parent\":\"#\"");
  	        	m.addAttribute("treeproduct", jsonStr);
  	            // Displaying JSON String 
  	           // System.out.println("hi"+jsonStr); 
  	        } 
  	  
  	        catch (IOException e) { 
  	            System.out.println("Get Mqtt tree in json line 62 ::"+e); 
  	        } 
  			
  			
  	}
  	
  	/*end*/
  	
	@GetMapping("subscribeNode")
	@ResponseBody
	public String subtest(HttpServletRequest rec) throws Exception {
		String i = rec.getParameter("id");
		String name = rec.getParameter("name");
		String url = rec.getParameter("url");
//		System.out.println("subscribe node :" + i);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		
		live.deleteAll();
		LiveEntity l = new LiveEntity();
		l = live.selectData(i, url);
		
		if (l == null) {
			l = new LiveEntity();

			l.setId(i);
			l.setName(name);
			l.setEndpoint(url);
			l.setSubscribeStatus("yes");
			l.setSrcTime(now.toString());

			live.save(l);
		} else {
			l.setId(i);
			l.setName(name);
			l.setEndpoint(url);
			l.setSubscribeStatus("yes");
			l.setSrcTime(now.toString());

			live.save(l);

		}
		runData();
//		SubThreadCall("");
		return "treeJson";
	}
	
	@GetMapping("unsubscribeNode")
	@ResponseBody
	public String unsubtest(HttpServletRequest rec) throws Exception {
//		System.out.println(Long.parseLong(rec.getParameter("id")));
		try {

			
			LiveEntity l = live.selectData(rec.getParameter("id"), rec.getParameter("url"));
			l.setSubscribeStatus("no");
			live.save(l);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		//SubThreadCall("");
		runData();
		return "success";
	}
	
	OpcUaClient createClient(String url) throws Exception {
		
		//String url="opc.tcp://DESKTOP-6AQURNT:48010";
		   Path securityTempDir = Paths.get(System.getProperty("java.io.tmpdir"), "security");
	        Files.createDirectories(securityTempDir);
	        if (!Files.exists(securityTempDir)) {
	            throw new Exception("unable to create security dir: " + securityTempDir);
	        }
	        LoggerFactory.getLogger(getClass())
	            .info("security temp dir: {}", securityTempDir.toAbsolutePath());

	      
	        SecurityPolicy securityPolicy = SecurityPolicy.None;

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
	            .filter(e -> true)
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
	            .setIdentityProvider(new AnonymousProvider())
	            .setRequestTimeout(uint(50000))
	            .setKeyPair(loader.getClientKeyPair())
	            .setCertificate(loader.getClientCertificate())
	            .build();
		return OpcUaClient.create(config);
	}
	
	@RequestMapping("treeReload")
	@ResponseBody
    public String runData() throws Exception {
        // synchronous connect
		
		String send_date="",node_id="",datatype="",status="",datetime="";
//		try {
			//String dataTry = "", data1[];
			List<LiveEntity> l = live.findAll();
			for (LiveEntity s : l) 
			{
				if (s.getSubscribeStatus().equals("yes")) 
				{
					String getnode_id = s.getName();
					System.out.println("Noed id...."+getnode_id);
					String geturl=s.getEndpoint();
					OpcUaClient client = createClient(geturl);
					client.connect().get();
					
					node_id=getnode_id;

			        // synchronous read request via VariableNode
			        VariableNode node = client.getAddressSpace().createVariableNode(NodeId.parse(node_id));
			        DataValue value = node.readValue().get();
			        datatype=value.getValue().toString().split("=")[0].replace("{", "");
			        status=value.getStatusCode().toString();
			        datetime=value.getServerTime().getJavaDate().toString();

			        
			        //{"id":"NodeId{ns=3, id=AirConditioner_10.Temperature}","name":"NodeId{ns=3, id=AirConditioner_10.Temperature}","value":"49.753600000088746","datatype":"Variantvalue","status":"StatusCode{name=Good, value=0x00000000, quality=good}","srctime":"Wed Apr 07 16:23:07 IST 2021","endpoint":"opc.tcp://Admin-PC:48010","userAccess":null,"displayName":null,"browseName":null,"nodeClass":null}
			        send_date="{\"id\":\""+node_id+"\",\"name\":\""+node_id+"\",\"value\":\""+value.getValue().getValue()+"\",\"datatype\":\""+datatype+"\",\"status\":\""+status+"\",\"srctime\":\""+datetime+"\",\"endpoint\":\"opc.tcp://Admin-PC:48010\",\"userAccess\":null,\"displayName\":null,\"browseName\":null,\"nodeClass\":null}";
			        //System.out.println(""+value.getServerTime().getJavaDate().toString());
			        // asynchronous read request
			        readServerStateAndTime(client).thenAccept(values -> {
			            DataValue v0 = values.get(0);
			            DataValue v1 = values.get(1);
			           // System.out.println(""+ServerState.from((Integer) v0.getValue().getValue()));
			           // logger.info("State={}", ServerState.from((Integer) v0.getValue().getValue()));
			           // logger.info("CurrentTime={}", v1.getValue().getValue());
			          //  System.out.println(""+v1.getValue().getValue());

			            future.complete(client);
			        });
			        
			        
				}
				//System.out.println("print..."+send_date);
				return send_date;
			}
			return send_date;
			
//		}
//		catch(Exception ex)
//		{
//			return "";
//		}
		//return datetime;
		//return "";
		
        
        
    }

    private CompletableFuture<List<DataValue>> readServerStateAndTime(OpcUaClient client) {
        List<NodeId> nodeIds = ImmutableList.of(
            Identifiers.Server_ServerStatus_State,
            Identifiers.Server_ServerStatus_CurrentTime);

        return client.readValues(0.0, TimestampsToReturn.Both, nodeIds);
    }
    
    @RequestMapping("singleSelect")
	@ResponseBody
	public String singleselecct(HttpServletRequest rec) throws JsonProcessingException, IOException //single atribute 
	{

			String id=rec.getParameter("id");
			String url= rec.getParameter("url");
			
			System.out.println("id..."+id+"....."+url);
		
		return "";
	}

}
