package com.edge.application.views.form;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.BacnetTagTable;
import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.service.EdgeService;
import com.sun.xml.bind.v2.runtime.property.Property;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;



@PageTitle("Bacnet Tag Edit Form")
@Route(value = "bacnettageditform" ,layout = MainLayout.class)
public class BacnetEditTreeForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="bacnettageditform";
	TextField txt_root     = new TextField("ROOT VALUE");
	Button btn_next = new Button("NEXT");
	Button btn_back = new Button("Back");
	Button btn_tag = new Button("Tag Browser");
	Button btn_save = new Button("Save");
		
		HorizontalLayout btn_layout = new HorizontalLayout();
		FormLayout formLayout       = new FormLayout();
		
		HorizontalLayout panel_layout = new HorizontalLayout();
	
	String str_value="";
	
	String global_var ="";
	
	  Clipboard clipboard = null;
	  
	@Autowired
	private EdgeService service;
	
	VerticalLayout field_connection=new VerticalLayout();
	
	Grid<BacnetTagTable> grid = null;
	ListDataProvider<BacnetTagTable> dataProvider;
	
	public void init(String para)
	{
		global_var = ""+para;
		
		grid = new Grid<BacnetTagTable>();
		
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_back.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btn_tag.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		//btn_save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
	
		txt_root.setWidth("100%");
		txt_root.setId("txttopic");
		panel_layout.setWidth("100%");
		
		if(global_var.contains("&")) {
			System.out.println("---contentEquals----");
			Vector tVc = new Vector();
			tVc= peUtil.split(global_var, "&");
			if(!tVc.isEmpty()) {
				global_var = peUtil.obj2str(tVc.get(0));
				str_value = peUtil.obj2str(tVc.get(1)).replaceAll("%20", " ");
				txt_root.setValue(str_value);
				btn_back.setEnabled(false);
				btn_next.setEnabled(false);
			}
		}
		
	    btn_back.addClickListener(e -> {
				
	    	UI.getCurrent().navigate(SourceList.class );
			
			
			
		});	
	    

		btn_tag.addClickListener(e -> {
//			UI.getCurrent().getPage().executeJavaScript("window.open('http://localhost:8081/bacnettagbrowser/"+global_var+"', 'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400px,height=400px');");
            String server_name = service.get_server_name_bacnet(global_var);
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('BacnetEditTree.html?url="+server_name+"','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
	
		
		btn_save.addClickListener(e -> {
					
		String root_src = 	str_value;
		
		if(!peUtil.isNullString(root_src)) {
			
			String device_id="",server_name="",broadcast_ip="",obj_type="",obj_name="",obj_id="",tag="";
			Vector vcVal	=	new Vector();
			
			vcVal = peUtil.splitOnComma(root_src);
			if(!vcVal.isEmpty()) {
				
			
					
					device_id=peUtil.obj2str(vcVal.get(0)).replaceAll("device_id=", "");
					server_name=peUtil.obj2str(vcVal.get(3)).replaceAll("server_name=", "");
					broadcast_ip=peUtil.obj2str(vcVal.get(4)).replaceAll("broadcast_ip=", "");
					obj_type=peUtil.obj2str(vcVal.get(1)).replaceAll("source=", "");
					obj_name="";
					obj_id="";
					tag=peUtil.obj2str(vcVal.get(2));
					String[] aaray1=obj_type.split(" ");
					Vector vc =new Vector();
					for(int r=0;r<aaray1.length;r++)
					{
						vc.add(aaray1[r]);
					}
				     if(!vc.isEmpty()) {
				    	 
				    	 if(vc.size()==2) {
				    		 obj_name = peUtil.obj2str(vc.get(0));
				    		 obj_id = peUtil.obj2str(vc.get(1));
							 }
						 
						 else if(vc.size()==3) {
							 obj_name = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1));
						 obj_id = peUtil.obj2str(vc.get(2));
						 }
						 else if(vc.size()==4) {
							 
							 obj_name = peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2));
							 obj_id = peUtil.obj2str(vc.get(3));
							 
						 }
						 else if(vc.size()==5) {
							 
							 obj_name =peUtil.obj2str(vc.get(0))+" "+peUtil.obj2str(vc.get(1))+" "+peUtil.obj2str(vc.get(2))+" "+peUtil.obj2str(vc.get(3));
							 obj_id = peUtil.obj2str(vc.get(4));
							 
						 }
				     }
				    	 
				if(!service.check_bacnet_obj(device_id, server_name, broadcast_ip, obj_name, obj_id)) {
					
					BacnetTagTable rdb = new BacnetTagTable();
			        rdb.setDevice_id(device_id);
			        rdb.setServer_name(server_name);
			        rdb.setBroadcast_ip(broadcast_ip);
			        rdb.setObj_name(obj_name);
			        rdb.setObj_id(obj_id);
			        service.bacnet_tag_save(rdb);
			        Notification.show("Tag saved successfully..!");
			        txt_root.setValue("");
			        btn_back.setEnabled(true);
					btn_next.setEnabled(true);
			       	UI.getCurrent().navigate( BacnetEditTreeForm.ROUTE_NAME+ "/" + global_var);

				}else {
					Notification.show("Tag Alredy Exists in Table..!");
			        txt_root.setValue("");
			        btn_back.setEnabled(true);
					btn_next.setEnabled(true);
					
					UI.getCurrent().navigate( BacnetEditTreeForm.ROUTE_NAME+ "/" + global_var);
				}
				
			}// vector close
		}
	           
	          
		
		
		
	});	
	    
	   // field_connection.add(txt_root);
	    btn_layout.add(btn_back, btn_next,btn_tag);
		if(peUtil.isNullString(global_var)) {
		formLayout.add( btn_layout );
		}else {
			formLayout.add(  btn_layout );
		}
		
		//panel_layout.add(field_connection);
		//remove();
		
		
		update(global_var);
		
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

		grid.addComponentColumn(event -> {

			Text id;
			id = new Text("" + event.getId());
			return id;
		});
		
		Grid.Column<BacnetTagTable> Raw_Material = grid.addColumn(BacnetTagTable::getDevice_id).setHeader("Device ID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<BacnetTagTable> sap_id = grid.addColumn(BacnetTagTable::getServer_name).setHeader("Server Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<BacnetTagTable> scada_ip = grid.addColumn(BacnetTagTable::getBroadcast_ip).setHeader("Broadcast IP")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<BacnetTagTable> scada_id = grid.addColumn(BacnetTagTable::getObj_name).setHeader("Object Name")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		Grid.Column<BacnetTagTable> scada_cov = grid.addColumn(BacnetTagTable::getObj_id).setHeader("Object Id")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<BacnetTagTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

			// create edit button for each row
			Button delete = new Button("DELETE");

			// set icon
			delete.setIcon(new Icon(VaadinIcon.TRASH));

			// set theme
			delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			

			// on click operation
			delete.addClickListener(ed -> {

				
					Dialog dialog = new Dialog();
					dialog.add(new Text("Are you sure ! want to delete bacnet Tag...?"));
					dialog.setCloseOnEsc(false);
					dialog.setCloseOnOutsideClick(false);

					Button confirmButton = new Button("Confirm", event -> {
						try {
							service.delete_bacnet_tag(sourcedata.getId());
						
							//UI.getCurrent().navigate(ModbusTCPProtocolForm.ROUTE_NAME + "/" +txt_source_id.getValue().toString());
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();
						update(global_var);

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
		grid.setSizeUndefined();
		grid.setHeightByRows(true);
		
		removeAll();
		add(btn_layout,txt_root,btn_save,grid);
		
		
	}
	public void update(String device_id) {

	
		List<BacnetTagTable> list =null;
		dataProvider = null;
		list = service.bacnet_tag_list(device_id);
		dataProvider = new ListDataProvider<>(list);
		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
	
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
		init(parameter);
	
}
	

}
