package com.edge.application.views.BACNET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.service.EdgeService;
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


@Route(value = "bacnetview")
//@PWA(name = "example2", shortName = "try2")
public class BacnetView extends HorizontalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="bacnetview";

	Div tree,serverData,thread;
	VerticalLayout tree_connection;
	IFrame loadTree;
	
	
	@Autowired
	private EdgeService service;
	
	String SelectedID,connURL="";
	
	//@PostConstruct
	public void init(String para) {

//		System.out.println("OPC View Para..."+para);
		tree_connection=new VerticalLayout();//left side window
		

		loadTree=new IFrame();
		loadTree.setSrc(connURL);
		loadTree.getStyle().set("margin-top", "-11px");
		
		setHeight("100%");
		Anchor a=new Anchor("/serverInfo", "Server");
		
		
		List<SourceBacnetTable> l=null; 
		l=service.bacnet_id_list(para);
		Grid<SourceBacnetTable> grid=new Grid<>();
		grid.setItems(l);
		grid.addColumn(SourceBacnetTable::getServer_name).setHeader(a);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addComponentColumn(ConnectionInfo1 -> {
			Button connect=new Button();
			connect.setIcon(new Icon(VaadinIcon.PLUG));
			connect.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//Button tree = new Button();
			//connect.setVisible(false);
			//tree.setVisible(false);
			String broadcast_ip=ConnectionInfo1.getBroadcast_ip();
			if(service.check_server(ConnectionInfo1.getServer_name(),broadcast_ip))
			{
				//connect.setVisible(true);
				
				connect.addClickListener(event->{
					connURL="BacnetTree.html?url="+ConnectionInfo1.getServer_name();
					System.out.println(connURL);
					loadTree.setSrc(connURL);
					
				});
				
			}
			return connect;

			
		}).setWidth("30%");
		
		
		
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
		
		IFrame i_reload=new IFrame("BacnetReload.html");
		i_reload.setWidth("103%");
		i_reload.setHeight("100%");
		dataLive.add(i_reload);
		dataLive.setWidth("100%");
		
		
//		tree_connection.addClassName("spacing");
		i_reload.addClassName("spacing");
		add(tree_connection,dataLive);
		//addClassName("spacing");
		
//		
//		
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
