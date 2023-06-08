package com.edge.application.views.form;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.service.EdgeService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;

@PageTitle("Mqtt Topic Edit Form")
@Route(value = "mqttedittopicform" ,layout = MainLayout.class)
public class MqttEditTopicForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="mqttedittopicform";
	Button btn_next = new Button("NEXT");
	Button btn_back = new Button("Back");
	Button btn_tag = new Button("Topic Browser");
	Button btn_save = new Button("Save");
		
		HorizontalLayout btn_layout = new HorizontalLayout();
		FormLayout formLayout       = new FormLayout();
		
		HorizontalLayout panel_layout = new HorizontalLayout();
	
	
	IFrame fi = new IFrame();
	
	String connURL="";
	String global_var ="";
	
	  Clipboard clipboard = null;
	  
	@Autowired
	private EdgeService service;
	
	VerticalLayout field_connection=new VerticalLayout();
	
	TextField txt_root     = new TextField("ROOT VALUE");
	
	public void init(String para)
	{
		global_var = ""+para;
		
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_back.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_tag.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
	
		txt_root.setWidth("100%");
		txt_root.setId("txttopic");
		panel_layout.setWidth("100%");
		
	    btn_back.addClickListener(e -> {
				
	    	UI.getCurrent().navigate(BacnetTagTreeForm.ROUTE_NAME + "/" + global_var );
			
			
			
		});	
	    

		btn_tag.addClickListener(e -> {
//			UI.getCurrent().getPage().executeJavaScript("window.open('http://localhost:8081/bacnettagbrowser/"+global_var+"', 'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400px,height=400px');");

			UI.getCurrent().getPage().executeJavaScript(
					"window.open('MqttEditTopic.html?url="+global_var+"','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_save.addClickListener(e -> {
			
			
	           
	          
		
		
		
	});	
	    
	    field_connection.add(txt_root,btn_save);
	    btn_layout.add(btn_back, btn_next,btn_tag);
		if(peUtil.isNullString(global_var)) {
		formLayout.add( btn_layout );
		}else {
			formLayout.add(  btn_layout );
		}
		
		panel_layout.add(field_connection);
		//remove();
		add(btn_layout,panel_layout);
		
		
	}
	
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
		init(parameter);
	
}
	

}
