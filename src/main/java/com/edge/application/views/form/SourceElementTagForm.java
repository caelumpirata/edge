package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.ElementTagMappingList;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceElementTagTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Source Element Tag Mapping")
@Route(value = "SourceElementTagForm",layout = MainLayout.class)
public class SourceElementTagForm extends HorizontalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="SourceElementTagForm";
	
	ComboBox<String> com_source_id_name = new ComboBox<String>("Source ID NAME:");
	ComboBox<String> com_element_id_name = new ComboBox<String>("Element ID NAME:");
	TextField txt_source_id     = new TextField("Source Id");
	TextField txt_tag_id = new TextField("Tag ID:");
	TextField txt_tag_name = new TextField("Tag Name:");
	TextField txt_tag_ref = new TextField("Tag Address:");
	TextField txt_tag_reg  = new TextField("Tag Lenght:");
	TextField txt_tag_datatype = new TextField("Tag DataType:");
	TextField txt_tag_unit  = new TextField("Tag Unit:");
	
	Grid<TagTable> grid_tag = new Grid<TagTable>();
	ListDataProvider<TagTable> dataProvider;
	
	Button btn_submit= new Button("SUBMIT");
	Button btn_next= new Button("NEXT");
	Button btn_cancel= new Button("CANCEL");
	Button btn_skip = new Button("SKIP");
	Button btn_back = new Button("Back");
	
	Button btn_element= new Button("ADD ELEMENT"); // 22-11-2022
	Button btn_tag= new Button("ADD TAG");
	
	VerticalLayout v1 = new VerticalLayout();
	VerticalLayout v2_grid = new VerticalLayout();
	
	HorizontalLayout tag_h1 =new HorizontalLayout();
	HorizontalLayout tag_h2 =new HorizontalLayout();
	HorizontalLayout tag_h3 =new HorizontalLayout();
	HorizontalLayout btn_h1= new HorizontalLayout();
	HorizontalLayout ele_h1= new HorizontalLayout(); // add 22-11-2022
	
	Binder<Validation> binder  =new Binder<Validation>();
	Binder<Validation> binder1  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	Dialog dialog = new Dialog(); //22-11-2022
	VerticalLayout centerButton = new VerticalLayout(); //22-11-2022
	
	List<String> ls=new ArrayList<String>();
	Collection<String> cls =new ArrayList<String>();
	@Autowired
	EdgeService service;
	
	String getid="",final_id=""; //22-11-2022
	private int int_id = 0; // 22-11-2022
	
	int u=0;
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
		cls =new ArrayList<String>();
		dataProvider=null;
		 String src_id=""+para;
		com_source_id_name.setWidthFull();
		com_element_id_name.setWidthFull();
		txt_tag_name.setWidthFull();
		txt_tag_datatype.setWidthFull();
		txt_tag_unit.setWidthFull();
		txt_tag_ref.setWidthFull();
		txt_tag_reg.setWidthFull();
		txt_tag_id.setWidthFull();
		tag_h1.setWidthFull();
		tag_h2.setWidthFull();
		tag_h3.setWidthFull();
		btn_element.setWidthFull();
		ele_h1.setWidthFull();
		btn_element.getElement().getStyle().set("margin-top", "36px");
		
		txt_tag_name.setReadOnly(true);
		txt_tag_datatype.setReadOnly(true);
		txt_tag_unit.setReadOnly(true);
		txt_tag_ref.setReadOnly(true);
		txt_tag_reg.setReadOnly(true);
		txt_tag_id.setReadOnly(true);
		
		tag_h1.add(txt_tag_id,txt_tag_name);
		tag_h2.add(txt_tag_ref,txt_tag_reg);
		tag_h3.add(txt_tag_datatype,txt_tag_unit);
		
		
		if(peUtil.isNullString(src_id)) {
		
		for (SourceTable sou_table : service.source_id_list()) {
			
			String s_id = sou_table.getSource_id().toString();
			String s_name = sou_table.getSource_name().toString();
			String concnate = s_id+"-"+s_name;
			ls.add(concnate);
		}
		
		//com_source_id_name.addValueChangeListener(source->{
			
						
			for (ElementTable se : service.element_list()) {
				//System.out.println("eie..."+se.getElement_id()+"-"+se.getElement_name());
				cls.add(se.getElement_id()+"-"+se.getElement_name());
			}
			
		//});
		}else {
			txt_source_id.setValue(src_id);
			txt_source_id.setReadOnly(true);
			//String source_id=src_id.split("-")[0];
			//String source_name=src_id.split("-")[1];
			//for(SourceElementTable se:service.get_source_element(source_id, source_name))
			//{
			for (ElementTable se : service.element_list()) {	
				cls.add(se.getElement_id()+"-"+se.getElement_name());
			}
		}
		
		com_source_id_name.setItems(ls);
		com_element_id_name.setItems(cls);
		
		if(peUtil.isNullString(src_id)) {
			binder.forField(com_source_id_name).asRequired("Select Source ID-Name").bind(Validation::getString,Validation::setString);
			}else {
				binder.forField(txt_source_id).asRequired("Select Source ID-Name").bind(Validation::getString,Validation::setString);

			}
			
			binder.forField(com_element_id_name).asRequired("Select Element ID-Name").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_id).asRequired("Tag ID").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_name).asRequired("Tag Name").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_ref).asRequired("Tag Address").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_reg).asRequired("Tag Lenght").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_datatype).asRequired("Tag Data Type").bind(Validation::getString,Validation::setString);
			binder.forField(txt_tag_unit).asRequired("Tag Unit").bind(Validation::getString,Validation::setString);
		
		  update();
		
		grid_tag.setWidthFull();
		grid_tag.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		Grid.Column<TagTable> Raw_Material = grid_tag.addColumn(TagTable::getTag_id).setHeader("Tag ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable> sap_id = grid_tag.addColumn(TagTable::getTag_name).setHeader("Tag Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable> scada_id = grid_tag.addColumn(TagTable::getTagRefAddress).setHeader("Address")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<TagTable> protocol_id = grid_tag.addColumn(TagTable::getTagRegAddress).setHeader("Lenght")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		// grid code double click to get Value in text box
		grid_tag.addItemDoubleClickListener(e -> {
			txt_tag_id.setValue(e.getItem().getTag_id());
            txt_tag_name.setValue(e.getItem().getTag_name());
            txt_tag_datatype.setValue(e.getItem().getDataType());
            txt_tag_unit.setValue(e.getItem().getUnit());
            txt_tag_ref.setValue(e.getItem().getTagRefAddress());
            txt_tag_reg.setValue(e.getItem().getTagRegAddress());
        });
		
		// start code dialog boc in btn element click event 22-11-2022 tannu
		btn_element.addClickListener(ele->{
			centerButton.removeAll();
			dialog.open();
			dialog.setCloseOnEsc(false);
			dialog.setCloseOnOutsideClick(false);
			TextField ele_id=new TextField("Element ID:");
			TextField ele_name=new TextField("Element Name:");
			ComboBox<String> com_point = new ComboBox<String>("Modbus Point Type:");
			com_point.setAllowCustomValue(true); 
			com_point.setItems("01:COIL STATUS","02:INPUT STATUS","03:HOLDING REGISTER","04:INPUT REGISTER"); 
			ele_id.setReadOnly(true);
			for (ElementTable sou_table : service.element_list()) {

				getid = sou_table.getElement_id().toString();
			}

			if (!getid.equals("")) {
				int_id = Integer.parseInt(getid);
				int_id = int_id + 1;
				if (int_id < 10) {

					final_id = "000" + int_id;
				} else {
					final_id = "00" + int_id;
				}
			} else {
				int_id = 1;
				if (int_id < 10) {

					final_id = "000" + int_id;
				} else {
					final_id = "00" + int_id;
				}
			}
			ele_id.setValue(final_id);
			
			if(peUtil.isNullString(src_id)) {
				binder.forField(com_source_id_name).asRequired("Select Source ID-Name").bind(Validation::getString,Validation::setString);
				}else {
					binder.forField(txt_source_id).asRequired("Select Source ID-Name").bind(Validation::getString,Validation::setString);

				}
			Button confirmButton = new Button("SAVE", ev -> {
				
				ElementTable el = new ElementTable();
				el.setElement_id(ele_id.getValue().toString());
				el.setElement_name(ele_name.getValue().toString());
				el.setModbus_pointType(com_point.getValue().toString());
				el.setRowid(genUniqueID());
				service.element_save(el);
				
				SourceElementTable st = new SourceElementTable();
				st.setElement_id(ele_id.getValue().toString());
				st.setElement_name(ele_name.getValue().toString());
				if(peUtil.isNullString(src_id)) 
				{
					st.setSource_id(com_source_id_name.getValue().toString().split("-")[0].toString());
					st.setSource_name(com_source_id_name.getValue().toString().split("-")[1].toString());
				}
				else
				{
					st.setSource_id(txt_source_id.getValue().toString().split("-")[0].toString());
					st.setSource_name(txt_source_id.getValue().toString().split("-")[1].toString());
				}
				st.setRowid(genUniqueID());
				service.source_element_save(st);
				
				ele_name.clear();
				for (ElementTable sou_table : service.element_list()) {

					getid = sou_table.getElement_id().toString();
				}

				if (!getid.equals("")) {
					int_id = Integer.parseInt(getid);
					int_id = int_id + 1;
					if (int_id < 10) {

						final_id = "000" + int_id;
					} else {
						final_id = "00" + int_id;
					}
				} else {
					int_id = 1;
					if (int_id < 10) {

						final_id = "000" + int_id;
					} else {
						final_id = "00" + int_id;
					}
				}
				ele_id.setValue(final_id);
				cls.clear();
				com_element_id_name.clear();
				for (ElementTable se : service.element_list()) {	
					cls.add(se.getElement_id()+"-"+se.getElement_name());
				}
				com_element_id_name.setItems(cls);
				
			});

			Button can = new Button("Cancel", ev1 -> {
				u=1;
				UI.getCurrent().navigate(SourceElementTagForm.ROUTE_NAME + "/"+src_id);
				dialog.close();
				
			});

			centerButton.add(ele_id,ele_name,com_point, confirmButton);
			centerButton.setHorizontalComponentAlignment(Alignment.CENTER, ele_id,ele_name,com_point,confirmButton);
			centerButton.setWidth("100%");

			centerButton.add(ele_id,ele_name,com_point, new HorizontalLayout(confirmButton, can));
			dialog.add(centerButton);
		});
		
		// end btn element code
		
		
		// start code dialog boc in btn Tag click event 22-11-2022 tannu
				btn_tag.addClickListener(ele->{
					centerButton.removeAll();
					dialog.open();
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);
					TextField txt_tag_id     = new TextField("Tag Id");
					TextField txt_tag_name   = new TextField("Tag Name");
					TextField txt_ref_address    = new TextField("Tag Refrence (Address)");
					TextField txt_reg_address    = new TextField("Tag Register (Address)");
					ComboBox<String> data_type = new ComboBox<>("Data Type");
					TextField txt_unit    = new TextField("Unit");
					data_type.setAllowCustomValue(true); 
				    data_type.setItems("float","int","double","boolean");
				    
				    txt_ref_address.setAllowedCharPattern("[0-9]");
				    txt_reg_address.setAllowedCharPattern("[0-9]");
				    txt_unit.setAllowedCharPattern("[0-9]");
			       
			       		getid="";		
					// source id autoincrement code
					for (TagTable sou_table : service.tag_list()) {

						getid = sou_table.getTag_id().toString();
					}

					if (!getid.equals("")) {
						int_id = Integer.parseInt(getid);
						int_id = int_id + 1;
						if (int_id < 10) {

							final_id = "000" + int_id;
						} else {
							final_id = "00" + int_id;
						}
					} else {
						int_id = 1;
						if (int_id < 10) {

							final_id = "000" + int_id;
						} else {
							final_id = "00" + int_id;
						}
					}
					txt_tag_id.setValue(final_id);
					txt_tag_id.setReadOnly(true);
					
					binder1.forField(txt_tag_id).asRequired("Enter Tag ID").bind(Validation::getString,Validation::setString);
					binder1.forField(txt_tag_name).asRequired("Enter Tag Name").bind(Validation::getString,Validation::setString);
					binder1.forField(txt_ref_address).asRequired("Enter Tag Reference Address").bind(Validation::getString,Validation::setString);
					binder1.forField(txt_reg_address).asRequired("Enter Tag Register Address").bind(Validation::getString,Validation::setString);
					binder1.forField(data_type).asRequired("Select Data Type").bind(Validation::getString,Validation::setString);
					binder1.forField(txt_unit).asRequired("Enter Unit").bind(Validation::getString,Validation::setString);
					Button confirmButton = new Button("SAVE", ev -> {
						if (binder1.writeBeanIfValid(entity)) 
						{
						
						
                        String tag_id="",tag_name="",ref_add="",reg_add="",d_type="",t_unit="";
						
						tag_id    = txt_tag_id.getValue().toString();
						tag_name  = txt_tag_name.getValue().toString();
						ref_add    	   = txt_ref_address.getValue().toString();
						reg_add    	   = txt_reg_address.getValue().toString();
						d_type    	   = data_type.getValue().toString();
						t_unit    	   = txt_unit.getValue().toString();
										
            		  
						TagTable st = new TagTable();
						st.setTag_id(tag_id);
						st.setTag_name(tag_name);
						st.setTagRefAddress(ref_add);
						st.setTagRegAddress(reg_add);
						st.setDataType(d_type);
						st.setUnit(t_unit);
						
						st.setRowid(genUniqueID());
						service.tag_save(st);
						
						
						
						txt_tag_name.clear();
						txt_ref_address.clear();
						txt_reg_address.clear();
						data_type.clear();
						txt_unit.clear();
						
						getid="";
						for (TagTable sou_table : service.tag_list()) {

							getid = sou_table.getTag_id().toString();
						}

						if (!getid.equals("")) {
							int_id = Integer.parseInt(getid);
							int_id = int_id + 1;
							if (int_id < 10) {

								final_id = "000" + int_id;
							} else {
								final_id = "00" + int_id;
							}
						} else {
							int_id = 1;
							if (int_id < 10) {

								final_id = "000" + int_id;
							} else {
								final_id = "00" + int_id;
							}
						}
						txt_tag_id.setValue(final_id);
						txt_tag_id.setReadOnly(true);
					}
					});

					Button can = new Button("Cancel", ev1 -> {
						u=1;
						UI.getCurrent().navigate(SourceElementTagForm.ROUTE_NAME + "/"+src_id);
						dialog.close();
						
					});

					
					centerButton.add(txt_tag_id,txt_tag_name,txt_ref_address,txt_reg_address,data_type,txt_unit, confirmButton);
					centerButton.setHorizontalComponentAlignment(Alignment.CENTER, txt_tag_id,txt_tag_name,txt_ref_address,txt_reg_address,data_type,txt_unit,confirmButton);
					centerButton.setWidth("100%");

					centerButton.add(txt_tag_id,txt_tag_name,txt_ref_address,txt_reg_address,data_type,txt_unit, new HorizontalLayout(confirmButton, can));
					dialog.add(centerButton);
				});

				/// end button tag 
		// Validation code
		
		
		
		btn_submit.addClickListener(b->{
			
			if (binder.writeBeanIfValid(entity)) 
			{
				String source_id="",source_name="";
				if(peUtil.isNullString(src_id)) {
					source_id = com_source_id_name.getValue().toString().split("-")[0];
					source_name=com_source_id_name.getValue().toString().split("-")[1];
				}else {
					source_id = txt_source_id.getValue().toString().split("-")[0];
					source_name=txt_source_id.getValue().toString().split("-")[1];
				}
				 
				String element_id=com_element_id_name.getValue().toString().split("-")[0];
				String element_name=com_element_id_name.getValue().toString().split("-")[1];
				String tag_id=txt_tag_id.getValue().toString();
				String tag_name=txt_tag_name.getValue().toString();
				String tag_ref=txt_tag_ref.getValue().toString();
				String tag_reg=txt_tag_reg.getValue().toString();
				String datatype=txt_tag_datatype.getValue().toString();
				String unit=txt_tag_unit.getValue().toString();
				
				if(!service.check_tag_source(source_id, tag_id,element_id))
				{
					SourceElementTagTable sett= new SourceElementTagTable();
					sett.setSource_id(source_id);
					sett.setSource_name(source_name);
					sett.setElement_id(element_id);
					sett.setElement_name(element_name);
					sett.setTag_id(tag_id);
					sett.setTag_name(tag_name);
					sett.setTag_ref_address(tag_ref);
					sett.setTag_reg_address(tag_reg);
					sett.setUnit(unit);
					sett.setDatatype(datatype);
					sett.setRowid(genUniqueID());
					service.source_element_tag_save(sett);
					
					txt_tag_id.clear();
					txt_tag_name.clear();
					txt_tag_ref.clear();
					txt_tag_reg.clear();
					txt_tag_datatype.clear();
					txt_tag_unit.clear();
				}
				else
				{
					Notification.show("Source Element Already Mapped for this tag....!");
				} 
				
			}
		});
		
		
		
		btn_next.addClickListener(s->{
			
			String src="";
			if(peUtil.isNullString(src_id)) {
				
				src= com_source_id_name.getValue().toString().toString().split("-")[0];
				
			}else {
				src =txt_source_id.getValue().toString().toString().split("-")[0];
			}
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+src);
			
			
		});
		
		
		btn_skip.addClickListener(e -> {
			String id =src_id.split("-")[0];
			if(service.check_source_tag_element_id_only(id) ) {
				
				UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+id);
			}
			
			
		});
		
		btn_cancel.addClickListener(s->{
			
				UI.getCurrent().navigate( SourceList.class);
			
			
		});
           btn_back.addClickListener(e -> {
			
			String parameters=txt_source_id.getValue().toString();
			long id = service.get_main_source_id(parameters);
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/"+id);
			
			
			
			
		});		
		
		v1.removeAll();
		if(peUtil.isNullString(src_id)) {
		btn_h1.add(btn_back,btn_submit,btn_cancel);
		}else {
			String id =src_id.split("-")[0];
			//if(service.check_source_tag_element_id_only(id) ) {
			   btn_h1.add(btn_back,btn_submit,btn_next,btn_skip,btn_cancel);
			//}
		}
		
		
		ele_h1.add(com_element_id_name,btn_element); // add new btn element vew 22-11-2022
		if(peUtil.isNullString(src_id)) {
	    	v1.add(com_source_id_name,ele_h1,tag_h1,tag_h2,tag_h3,btn_h1); // change 22-11-2022
		}else {
			v1.add(txt_source_id,ele_h1,tag_h1,tag_h2,tag_h3,btn_h1); // change 22-11-2022
		}
		
		 v2_grid.add(btn_tag);
		 v2_grid.add(grid_tag);
		add(v1,v2_grid);
	}
	
	public void update() {

		List<TagTable> list =null;
		list = service.get_grid1();
		dataProvider = new ListDataProvider<>(list);
		grid_tag.removeAllColumns();
		grid_tag.setItems(list);
		grid_tag.setDataProvider(dataProvider);
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

@Override
public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
	
		init(parameter);
	
}
}
