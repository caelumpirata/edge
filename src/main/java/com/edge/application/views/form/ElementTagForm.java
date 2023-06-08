package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.ElementTagMappingList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
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

@PageTitle("Element Tag Mapping Form")
@Route(value = "ElementTagForm" ,layout = MainLayout.class)
public class ElementTagForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="ElementTagForm";
	
	TextField txt_element_id     = new TextField("Element ID");
	ComboBox<String> element = new ComboBox<>("Element ID-Name");
	ComboBox<String> tag = new ComboBox<>("Tag ID-Name");
		
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
		for (ElementTable sou_table : service.element_list()) {

			String e_id = sou_table.getElement_id().toString();
			String e_name = sou_table.getElement_name().toString();
			String concnate = e_id+"-"+e_name;
			ls.add(concnate);
			
		}
		/*}else {
			String str = global_element_id+"-"+service.element_name(global_element_id);
			ls.add(str);
			txt_element_id.setValue(str);
		}*/

		element.setItems(ls);
	
		
		/////////////////////////////////
		
		for (TagTable tag_table : service.tag_list()) {

			String t_id = tag_table.getTag_id().toString();
			String t_name = tag_table.getTag_name().toString();
			
			if(service.check_element_Tag_id(t_id)) {
				
			}
			else {
			String concnate = t_id+"-"+t_name;
			
			ls_tag.add(concnate);
			}
			
		}
		tag.setItems(ls_tag);
		// Validation code

		
			binder.forField(element).asRequired("Select Element Id-Name").bind(Validation::getString,Validation::setString);
		    binder.forField(tag).asRequired("Select Tag Id-Name").bind(Validation::getString,Validation::setString);

	
		
		
			
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					
						String elem="",elem_id="";String elem_name="";
						elem 		=  element.getValue().toString();
						elem_id		=  elem.split("-")[0];
						elem_name   =  elem.split("-")[01];
											
						String tt="",tag_id="";String tag_name="";
						tt 		=  tag.getValue().toString();
						tag_id		=  tt.split("-")[0];
						tag_name   =  tt.split("-")[01];
						
						
						
						if(service.check_Element_id(elem_id))
		        		{
							
							ElementTagTable st = new ElementTagTable();
							st.setElement_id(elem_id);
							st.setElement_name(elem_name);
							st.setTag_id(tag_id);
							st.setTag_name(tag_name);
							st.setRowid(genUniqueID());
							String id =service.element_main_id(elem_id);
							st.setId(Integer.parseInt(id));
							service.element_tag_save(st);
		        		}else {
		        			ElementTagTable st = new ElementTagTable();
							st.setElement_id(elem_id);
							st.setElement_name(elem_name);
							st.setTag_id(tag_id);
							st.setTag_name(tag_name);
							st.setRowid(genUniqueID());
							st.setRowid(genUniqueID());
							service.element_tag_save(st);
		        			
		        		}
						
						
						UI.getCurrent().navigate(ElementTagMappingList.class);
					
        		
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( element, tag, btn_layout );
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
