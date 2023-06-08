package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.ElementTagMappingList;
import com.edge.application.views.Grid.SourceElementMappingList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.Table.userTable;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import antlr.collections.impl.Vector;

@PageTitle("Source Element Mapping Form")
@Route(value = "SourceElementForm" ,layout = MainLayout.class)
public class SourceElementForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="SourceElementForm";
	
	TextField txt_source_id     = new TextField("Source ID");
	ComboBox<String> source = new ComboBox<>("Source ID-Name");
	ComboBox<String> element = new ComboBox<>("Element ID-Name");
		
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	List<String> ls=new ArrayList<String>();
	
	List<String> ls_tag=new ArrayList<String>();
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;

	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
	    String global_element_id=""+para;
	    
	    Vector vcElem = new Vector();
	    Vector vcTag = new Vector();
	        
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		
		//if(peUtil.isNullString(global_element_id)) {
		for (SourceTable sou_table : service.source_list()) {

			String s_id = sou_table.getSource_id().toString();
			String s_name = sou_table.getSource_name().toString();
			String concnate = s_id+"-"+s_name;
			ls.add(concnate);
			
		}
		/*}else {
			String str = global_element_id+"-"+service.element_name(global_element_id);
			ls.add(str);
			txt_element_id.setValue(str);
		}*/

		source.setItems(ls);
	
		
		/////////////////////////////////
		source.addValueChangeListener(source->{
			
			String source_id_name=source.getValue().toString();
			String source_id=source_id_name.split("-")[0];
			String source_name=source_id_name.split("-")[1];
			
			for (ElementTable tag_table : service.element_list()) {
				
				
				String e_id = tag_table.getElement_id().toString();
				String e_name = tag_table.getElement_name().toString();
				
				if(service.check_source_element_id(e_id,source_id)) {
					
				}
				else {
				String concnate = e_id+"-"+e_name;
				
				ls_tag.add(concnate);
				}
				
			}
			
		});
		
		
		element.setItems(ls_tag);
		// Validation code

		
			binder.forField(source).asRequired("Select Source Id-Name").bind(Validation::getString,Validation::setString);
		    binder.forField(element).asRequired("Select Element Id-Name").bind(Validation::getString,Validation::setString);

	
		
		
			
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					
						String elem="",elem_id="";String elem_name="";
						elem 		=  element.getValue().toString();
						elem_id		=  elem.split("-")[0];
						elem_name   =  elem.split("-")[01];
											
						String ss="",source_id="";String source_name="";
						ss 		=  source.getValue().toString();
						source_id		=  ss.split("-")[0];
						source_name   =  ss.split("-")[01];
						
						
						
						if(service.check_source_element_id(elem_id,source_id))
		        		{
							
							SourceElementTable st = new SourceElementTable();
							st.setElement_id(elem_id);
							st.setElement_name(elem_name);
							st.setSource_id(source_id);
							st.setSource_name(source_name);
							st.setRowid(genUniqueID());
							String id =service.element_main_id(elem_id);
							st.setId(Integer.parseInt(id));
							service.source_element_save(st);
		        		}else {
		        			SourceElementTable st = new SourceElementTable();
		        			st.setElement_id(elem_id);
							st.setElement_name(elem_name);
							st.setSource_id(source_id);
							st.setSource_name(source_name);
							st.setRowid(genUniqueID());
							service.source_element_save(st);
		        			
		        		}
						
						
						UI.getCurrent().navigate(SourceElementMappingList.class);
					
        		
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( source, element, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		
	}
	
	
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	public static String genUniqueID()
	{ 
	  Date d = new Date();
	  long utn = d.getTime();

	  double rand = Math.random();

	  StringBuffer str = new StringBuffer();
	  str.append( utn + "-" + rand);

	  String id = md5(str.toString());

	  

	  return id;
	}
	public static String md5(String str)
{
MessageDigest md;
try
{
md = MessageDigest.getInstance("MD5");

md.update(str.getBytes());
byte[] b = md.digest();
String out = dumpBytes(b);
return out;
}
catch (Exception e) {

}

return "";
}
private static String dumpBytes(byte[] bytes)
{
int size = bytes.length;
StringBuffer sb = new StringBuffer(size * 2);

for (int i = 0; i < size; ++i) {
String s = Integer.toHexString(bytes[i]);

if (s.length() == 8)
{
  sb.append(s.substring(6));
}
else if (s.length() == 2)
{
  sb.append(s);
}
else
{
  sb.append("0" + s);
}
}

return sb.toString();
}

}
