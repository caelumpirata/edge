package com.edge.application.views.form;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.SourceTagMappingTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.edge.PlatformView;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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

@SuppressWarnings("serial")
@PageTitle("Source Tag Mapping Form")
@Route(value = "sourcetagmappingform" , layout = MainLayout.class)
public class SourceTagMappingForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="sourcetagmappingform";
	
	ComboBox<String> com_modbus_point = new ComboBox<String>("Modbus Point Type:");
	
	TextField txt_source_id     = new TextField("Source Id");
	TextField txt_tag_name = new TextField("Register Name:");
	TextField txt_tag_ref = new TextField("Register Address:");
	TextField txt_tag_reg  = new TextField("Register Lenght:");
	ComboBox<String> txt_tag_datatype = new ComboBox<String>("Register DataType:");
	ComboBox<String> txt_tag_unit  = new ComboBox<String>("Multiplier:");
	TextField txt_element_name  = new TextField("Element Name:");
	
	Button btn_back = new Button("BACK");
	Button btn_submit = new Button("SUBMIT");
	Button btn_next = new Button("NEXT");
	Button btn_skip = new Button("SKIP");
	Button btn_im = new Button("IM");
	
	Button btn_pdf = new Button("Register List Download PDF");
	Button btn_xls = new Button("Register List Download XLS");
	Button btn_print = new Button("PRINT");

	
	VerticalLayout v1 = new VerticalLayout();
	VerticalLayout v2 = new VerticalLayout();
	HorizontalLayout h1 = new HorizontalLayout();
	HorizontalLayout btn_layout = new HorizontalLayout();
	HorizontalLayout btn_layout2 = new HorizontalLayout();
	
	Grid<SourceTagMappingTable> grid_tag_edit = new Grid<SourceTagMappingTable>();
	ListDataProvider<SourceTagMappingTable> dataProvider_tag_edit;
	Dialog dialog_tag = new Dialog(); //22-11-2022
	VerticalLayout centerButton_tag = new VerticalLayout(); //22-11-2022
	Editor<SourceTagMappingTable> editor_tag;
	SourceTagMappingTable tag_table = new SourceTagMappingTable();
	Binder<SourceTagMappingTable> binder_tag     = new Binder<SourceTagMappingTable>();
	
	
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
	@Autowired
	private EdgeService service;
	
	public void init(String para)
	{
		v1.removeAll();
		v2.removeAll();
	
		String source_id_name=para.replace("%20", " ").toString();
		txt_source_id.setValue(source_id_name);
		txt_source_id.setReadOnly(true);
		
		txt_source_id.setWidthFull();
		txt_element_name.setWidthFull();
		com_modbus_point.setWidthFull();
		txt_tag_name.setWidthFull();
		txt_tag_ref.setWidthFull();
		txt_tag_reg.setWidthFull();
		txt_tag_datatype.setWidthFull();
		txt_tag_unit.setWidthFull();
		//v1.setWidthFull();
		//v2.setWidthFull();
		h1.setWidthFull();
		
		
		
		txt_tag_datatype.setItems("float","int","double","boolean");
		txt_tag_unit.setItems("1","100","1000","10000");
		com_modbus_point.setItems("01:COIL STATUS","02:INPUT STATUS","03:HOLDING REGISTER","04:INPUT REGISTER"); 
		
		txt_tag_ref.setAllowedCharPattern("[0-9]");
		txt_tag_reg.setAllowedCharPattern("[0-9]");
		txt_tag_name.setAllowedCharPattern("[A-Z a-z _]");
		txt_element_name.setAllowedCharPattern("[A-Z a-z _]");
		
		txt_tag_ref.setMaxLength(5);
		txt_tag_ref.setMinLength(3);
		txt_tag_ref.setErrorMessage("Min 3 & Max 5");
		txt_tag_reg.setMaxLength(2);
		txt_tag_reg.setErrorMessage("Max 2 Only");
		
		btn_back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_pdf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_xls.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_print.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_skip.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		txt_tag_name.focus();
		
	
		
		binder.forField(txt_tag_name).asRequired("Enter Register Name").bind(Validation::getString,Validation::setString);
		binder.forField(txt_tag_ref).asRequired("Enter Register Address").bind(Validation::getString,Validation::setString);
		binder.forField(txt_tag_reg).asRequired("Enter Register Length").bind(Validation::getString,Validation::setString);
		binder.forField(txt_tag_datatype).asRequired("Select DataType").bind(Validation::getString,Validation::setString);
		binder.forField(txt_tag_unit).asRequired("Select Multiplier").bind(Validation::getString,Validation::setString);
		binder.forField(txt_element_name).asRequired("Enter Element Name").bind(Validation::getString,Validation::setString);
		binder.forField(com_modbus_point).asRequired("Select ModBus Point").bind(Validation::getString,Validation::setString);
		
		
		
		
		btn_submit.addClickListener(e->{
			
			if (binder.writeBeanIfValid(entity)) 
			{
				//String source_id=txt_source_id.getValue().split("-")[0];
				//String source_name=txt_source_id.getValue().split("-")[1];
				
				String source_id = txt_source_id.getValue().toString();
				String source_name =service.get_Source_name(source_id);
				String element=txt_element_name.getValue().toString();
				String point = com_modbus_point.getValue().toString();
				String tag_name=txt_tag_name.getValue().toString();
				String tag_ref=txt_tag_ref.getValue().toString();
				String tag_reg=txt_tag_reg.getValue().toString();
				String datatype=txt_tag_datatype.getValue().toString();
				String unit = txt_tag_unit.getValue().toString();
				
				if(!service.check_source_tag_element(source_id,source_name,element,tag_name)) {
				
				SourceTagMappingTable st = new SourceTagMappingTable();
				st.setSource_id(source_id);
				st.setSource_name(source_name);//.replace("%20", " ")
				st.setElement_name(element);
				st.setTag_name(tag_name);
				st.setRef_add(tag_ref);
				st.setReg_len(tag_reg);
				st.setDatatype(datatype);
				st.setUnit(unit);
				st.setModbus_point(point);
				service.source_tag_save(st);
				
				txt_tag_name.setValue("");
				txt_tag_ref.setValue("");
				txt_tag_reg.setValue("");
				txt_element_name.setValue("");
				
				UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME+"/"+para);//.replace("%20", " ")
				
				
				}else {
										
					Notification.show("Register and Element Name Already Mapped!");
					txt_tag_name.focus();
				}
				
			    
			}
			

			
			
			
		});
		
		btn_pdf.addClickListener(pdf->{
			//String source_id=txt_source_id.getValue().toString();
			String parameters=txt_source_id.getValue().toString();
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			//UI.getCurrent().navigate("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().getPage().executeJavaScript("window.open('SourceTagPDF?para="+parameters+"', \"_blank\");");
		});
		
		btn_xls.addClickListener(pdf->{
			//String source_id=txt_source_id.getValue().toString();
			String parameters=txt_source_id.getValue().toString();
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			//UI.getCurrent().navigate("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().getPage().executeJavaScript("window.open('SourceTagXLS?para="+parameters+"', \"_blank\");");
		});
		
		btn_back.addClickListener(pdf->{
			String parameters=txt_source_id.getValue().toString();
			long id = service.get_main_source_id(parameters);
			//fi.setSrc("SourceTagPDF?source_id=" +source_id+"&source_name="+source_name);
			UI.getCurrent().navigate(SourceForm.ROUTE_NAME+"/"+id);
		});
		
		update_tag(para);
		editor_tag = grid_tag_edit.getEditor();
		grid_tag_edit.setWidthFull();
		grid_tag_edit.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		Grid.Column<SourceTagMappingTable> tag_name = grid_tag_edit.addColumn(SourceTagMappingTable::getTag_name).setHeader("Register Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> tag_add = grid_tag_edit.addColumn(SourceTagMappingTable::getRef_add).setHeader("Register Address")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> tag_len = grid_tag_edit.addColumn(SourceTagMappingTable::getReg_len).setHeader("Register Length")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> tag_datatype = grid_tag_edit.addColumn(SourceTagMappingTable::getDatatype).setHeader("DataType")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> tag_unit = grid_tag_edit.addColumn(SourceTagMappingTable::getUnit).setHeader("Multiplier")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> element = grid_tag_edit.addColumn(SourceTagMappingTable::getElement_name).setHeader("Element")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<SourceTagMappingTable> modbus_point = grid_tag_edit.addColumn(SourceTagMappingTable::getModbus_point).setHeader("Modbus Point")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		TextField tag_name_field = new TextField();
		binder_tag.forField(tag_name_field).bind(SourceTagMappingTable::getTag_name,SourceTagMappingTable::setTag_name);
		tag_name.setEditorComponent(tag_name_field);
		
		TextField tag_add_field = new TextField();
		binder_tag.forField(tag_add_field).bind(SourceTagMappingTable::getRef_add,SourceTagMappingTable::setRef_add);
		tag_add.setEditorComponent(tag_add_field);
		
		TextField tag_len_field = new TextField();
		binder_tag.forField(tag_len_field).bind(SourceTagMappingTable::getReg_len,SourceTagMappingTable::setReg_len);
		tag_len.setEditorComponent(tag_len_field);
		
		ComboBox<String> datatype_field = new ComboBox<String>();
		datatype_field.setItems("float","int","double","boolean");
		binder_tag.forField(datatype_field).bind(SourceTagMappingTable::getDatatype, SourceTagMappingTable::setDatatype);
		tag_datatype.setEditorComponent(datatype_field);
		
		ComboBox<String> unit_field = new ComboBox<String>();
		unit_field.setItems("1","100","1000","10000");
		binder_tag.forField(unit_field).bind(SourceTagMappingTable::getUnit, SourceTagMappingTable::setUnit);
		tag_unit.setEditorComponent(unit_field);
		
		TextField element_field = new TextField();
		binder_tag.forField(element_field).bind(SourceTagMappingTable::getElement_name,SourceTagMappingTable::setElement_name);
		element.setEditorComponent(element_field);
		
		ComboBox<String> point_field = new ComboBox<String>();
		point_field.setItems("01:COIL STATUS","02:INPUT STATUS","03:HOLDING REGISTER","04:INPUT REGISTER");
		binder_tag.forField(point_field).bind(SourceTagMappingTable::getModbus_point, SourceTagMappingTable::setModbus_point);
		modbus_point.setEditorComponent(point_field);
		
		editor_tag.setBinder(binder_tag);
		Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
		Grid.Column<SourceTagMappingTable> editorColumn = grid_tag_edit.addComponentColumn(tData -> {
			Button edit = new Button("EDIT");
			edit.setIcon(new Icon(VaadinIcon.EDIT));
			edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			//edit.addClassName("edit");
			edit.addClickListener(e -> {
				editor_tag.editItem(tData);
				tag_name_field.focus();

			});
			edit.setEnabled(!editor_tag.isOpen());
			editButtons.add(edit);
			return edit;
		}).setHeader("Edit").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
		editor_tag.addOpenListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_tag.isOpen())));
		editor_tag.addCloseListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor_tag.isOpen())));

		Button save = new Button("Save");
		save.addClassName("save");

		save.addClickListener(event -> {
			
			if (binder_tag.writeBeanIfValid(tag_table)) {

				service.update_sourcetagmapping(tag_name_field.getValue().toString(),tag_add_field.getValue().toString(),tag_len_field.getValue().toString(),datatype_field.getValue().toString(),unit_field.getValue().toString(),element_field.getValue().toString(),point_field.getValue().toString(),editor_tag.getItem().getId());

				Notification notification = new Notification("Edit Action", 2000, Notification.Position.BOTTOM_CENTER);
				notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
				notification.open();

				editor_tag.save();
			}
			editor_tag.closeEditor();
		});

		Button cancel = new Button("Cancel");
		cancel.addClassName("cancel");
		cancel.addClickListener(event -> {
			editor_tag.cancel();
		});

		grid_tag_edit.getElement().addEventListener("keyup", event -> editor_tag.cancel())
				.setFilter("event.key === 'Escape' || event.key === 'Esc'");

		Div buttons = new Div(save, cancel);

		save.getElement().setAttribute("padding-left", "20px");

		editorColumn.setEditorComponent(buttons);
    	// edit code end for grid
		
		// delete code for grid
		
		@SuppressWarnings({ "unused" })
		Grid.Column<SourceTagMappingTable> deleteColumn = grid_tag_edit.addComponentColumn(tedata -> {

			// create edit button for each row
			Button delete = new Button("DELETE");

			// set icon
			delete.setIcon(new Icon(VaadinIcon.TRASH));

			// set theme
			delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			
			// on click operation
			delete.addClickListener(ed -> {

				//List<String> validatesourceelement = service.getBysourceelement(deletedata.getElement_id());
				
					Dialog dialog = new Dialog();
					dialog.add(new Text("Are you sure you want to delete Ragister?"));
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);

					Button confirmButton = new Button("Confirm", event -> {
						try {
							service.DeleteBySource_Tag(tedata.getId());
							//service.delete_sourceelementmapping(deletedata.getElement_id());
							//eti.deleteById(TagTableData.getId());
							UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME+"/"+para);//.replace("%20", " ")
							
							
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();

						//update_tag();
						

					});
					Button cancelButton = new Button("Cancel", event -> {
						dialog.close();
						
					});

					dialog.add(new HorizontalLayout(confirmButton, cancelButton));

					dialog.open();

				

			});
			return delete;
		}).setHeader("Delete").setTextAlign(ColumnTextAlign.CENTER);
		
		// delete end
		
		btn_next.addClickListener(e->{
			String src="";
//			if(peUtil.isNullString(src_id)) {
//				
//				src= com_source_id_name.getValue().toString().toString().split("-")[0];
//				
//			}else {
				src =txt_source_id.getValue().toString().toString();
//			}
				UI.getCurrent().navigate(RTUandTCPandIMmappingForm.ROUTE_NAME + "/"+src);
		});
		
		btn_skip.addClickListener(e -> {
			String id =txt_source_id.getValue().toString().toString();
//			if(service.check_source_tag_element_id_only(id) ) {
				
			UI.getCurrent().navigate(RTUandTCPandIMmappingForm.ROUTE_NAME + "/"+id);
//			}
			
			
		});
		
		btn_im.addClickListener(e -> {
//			UI.getCurrent().getPage().executeJavaScript("window.open('https://cat.terrestrialplatform.link/login', \"_blank\");");
			String id =txt_source_id.getValue().toString();

				
				UI.getCurrent().navigate(RTUandTCPandIMmappingForm.ROUTE_NAME + "/"+id);

		
		});
		btn_layout.removeAll();
		if(service.check_source_mapping_tagid(para)){
		btn_layout.add(btn_back,btn_submit,btn_next,btn_skip,btn_im);
		}else {
			btn_layout.add(btn_back,btn_submit);
		}
		btn_layout2.removeAll();
		btn_layout2.add(btn_pdf,btn_xls);
		v1.removeAll();
		v1.add(txt_source_id,txt_tag_name,txt_tag_ref,txt_tag_reg,btn_layout);
		v2.removeAll();
		v2.add(txt_tag_datatype,txt_tag_unit,txt_element_name,com_modbus_point,btn_layout2);
		h1.removeAll();
		h1.add(v1,v2);
		add(h1,grid_tag_edit);
	}
	
	
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	
	@SuppressWarnings("deprecation")
	public void update_tag(String source_id) {

		List<SourceTagMappingTable> list =null;
		list = service.source_id_finadAll(source_id);
		dataProvider_tag_edit = new ListDataProvider<>(list);
		grid_tag_edit.removeAllColumns();
		grid_tag_edit.setItems(list);
		grid_tag_edit.setDataProvider(dataProvider_tag_edit);
	}
}
