package com.edge.application.views.edge;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.form.BacnetProtocolForm;
import com.edge.application.views.form.ModbusRTUProtocolForm;
import com.edge.application.views.form.ModbusTCPProtocolForm;
import com.edge.application.views.form.MqttProtocolForm;
import com.edge.application.views.form.OpcuaProtocolForm;
import com.edge.application.views.form.SourceElementTagForm;
import com.edge.application.views.form.SourceTagMappingForm;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@PageTitle("Protocol Form")
@Route(value = "PlatformView", layout = MainLayout.class)
public class PlatformView extends VerticalLayout implements HasUrlParameter<String>{
	HorizontalLayout layout_back_btn = new HorizontalLayout();
	HorizontalLayout layout_container = new HorizontalLayout();
	HorizontalLayout layout_button = new HorizontalLayout();
	
	VerticalLayout container_opcua = new VerticalLayout();
	VerticalLayout container_modbus_rtu = new VerticalLayout();
	VerticalLayout container_bacnet = new VerticalLayout();
	VerticalLayout container_modbus_tcp = new VerticalLayout();
	
	
	Icon icon_back = new Icon(VaadinIcon.ARROW_BACKWARD);
	Icon icon_opcua = new Icon(VaadinIcon.LAPTOP);
	Icon icon_mqtt = new Icon(VaadinIcon.LAPTOP);
	Icon icon_bacnet = new Icon(VaadinIcon.LAPTOP);
	Icon icon_modbus = new Icon(VaadinIcon.LAPTOP);
	Icon icon_browser = new Icon(VaadinIcon.BROWSER);
	
	Button btn_back = new Button();
	Button btn_opcua = new Button("OPCUA");
	Button btn_rtu = new Button("MODBUS RTU");
	Button btn_bacnet = new Button("MQTT");
	Button btn_modbus_tcp = new Button("MODBUS TCP");
	Button btn_browse = new Button("Browse");
	Button btn_cancel = new Button("Cancel");
	Button btn_skip = new Button("SKIP");
	
	
	Label header = new Label("Select Protocol Form");
	public static final String ROUTE_NAME="PlatformView";
	
	@Autowired
	private EdgeService service;
	
	@SuppressWarnings("unused")
	public void init(String para) {
		//	ADDING ICONS + SYLING BUTTONS and HEADERS
		
		
		btn_back.setIcon(icon_back);
		btn_browse.setIcon(icon_browser);
		
		btn_opcua.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btn_rtu.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btn_bacnet.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btn_modbus_tcp.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btn_browse.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
		
		icon_opcua.getStyle().set("font-size", "70px");
		icon_mqtt.getStyle().set("font-size", "70px");
		icon_bacnet.getStyle().set("font-size", "70px");
		icon_modbus.getStyle().set("font-size", "70px");
		layout_back_btn.getStyle().set("align-self", "flex-start");
		header.getStyle().set("font-size", "x-large").set("font-weight", "600").set("align-items", "center");
		layout_button.getStyle().set("align-self", "flex-start").set("margin-top", "18px");
		
		String src_id=""+para;
		
		//	BACK-BUTTON-CLICK
		btn_back.addClickListener(e -> {
			
		});
		
		
		//	OPCUA CONTAINER
		container_opcua.add(icon_opcua, btn_opcua);
		container_opcua.getStyle().set("width", "250px").set("height", "180px")
									.set("border-radius", "8px")
									.set("box-shadow",  "rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px");
		btn_opcua.addClickListener(e -> {
			UI.getCurrent().navigate( OpcuaProtocolForm.ROUTE_NAME+ "/" + src_id);
		});
		
		
		//	MOdbus RTU
		container_modbus_rtu.add(icon_mqtt, btn_rtu);
		container_modbus_rtu.getStyle().set("width", "250px").set("height", "180px")
									.set("border-radius", "8px")
									.set("box-shadow",  "rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px");
		btn_rtu.addClickListener(e -> {
			UI.getCurrent().navigate( ModbusRTUProtocolForm.ROUTE_NAME+ "/" + src_id);

		});

		
			//Mqtt CONTAINER
		container_bacnet.add(icon_bacnet, btn_bacnet);
		container_bacnet.getStyle().set("width", "250px").set("height", "180px")
									.set("border-radius", "8px")
									.set("box-shadow",  "rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px");
		btn_bacnet.addClickListener(e -> {
			UI.getCurrent().navigate( MqttProtocolForm.ROUTE_NAME+ "/" + src_id);

		});
		
		
		//	MODBUS CONTAINER
		container_modbus_tcp.add(icon_modbus, btn_modbus_tcp);
		container_modbus_tcp.getStyle().set("width", "250px").set("height", "180px")
									.set("border-radius", "8px")
									.set("box-shadow",  "rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px");
		btn_modbus_tcp.addClickListener(e -> {
			UI.getCurrent().navigate( ModbusTCPProtocolForm.ROUTE_NAME+ "/" + src_id);
		});
		
		
		//	Browser on click
		btn_browse.addClickListener(e -> {
			//Your code goes here.
		});
		
		
		//	Cancel on click
		btn_cancel.addClickListener(e -> {
			UI.getCurrent().navigate(SourceList.class);
		});
		
		btn_skip.addClickListener(e -> {
			String id =src_id.split("-")[0];
			if(service.check_source_modbus_rtu(id) || service.check_source_tcp_port(id) || service.check_source_mqtt(id) || service.check_source_opcua(id)) {
				UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME + "/"+src_id);
			}
			
			
		});
		
		
		
	     
		layout_back_btn.add(btn_back);
		layout_container.add(container_opcua, container_modbus_rtu, container_modbus_tcp,container_bacnet);
		if(peUtil.isNullString(src_id)) {
		layout_button.add(btn_browse, btn_cancel);
		}else {
			String id =src_id.split("-")[0];
			if(service.check_source_modbus_rtu(id) || service.check_source_tcp_port(id) || service.check_source_mqtt(id) || service.check_source_opcua(id)) {
			  layout_button.add(btn_browse, btn_cancel,btn_skip);
			}
		}
		add( layout_back_btn, header, layout_container, layout_button);
		this.getStyle().set("align-items", "center");
	}


	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		init(parameter);
		
	}
}

