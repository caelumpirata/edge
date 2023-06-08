package com.edge.application.views.BACNET;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.OPCUA.OPCUAandIMmappingForm;
import com.edge.application.views.Table.BacnetTagUtilityTable;
import com.edge.application.views.Table.OPCUATagUtilityTable;
import com.edge.application.views.form.BacnetTagTreeForm;
import com.edge.application.views.form.sourcePublisherForm;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("IM Mapping Form")
@Route(value = "bacnetimmapping" ,layout = MainLayout.class)
public class BacnetAndIMmappingForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="bacnetimmapping";
	
	TextField txt_boardcast_ip= new TextField("Boardcast IP:");
	TextField txt_port = new TextField("Port:");
	TextField txt_server_name = new TextField("Server Name:");
	TextField txt_source = new TextField("Source:");
	TextField txt_im = new TextField("IM Tag:");
	
	Button btn_submit = new Button("Mapping",new Icon(VaadinIcon.LINK));
	Button btn_tree = new Button("MQTT Tree",new Icon(VaadinIcon.FILE_TREE));
	Button btn_im = new Button("IM ",new Icon(VaadinIcon.FILE_TREE));
	Button btn_back = new Button("Back ",new Icon(VaadinIcon.ARROW_LEFT));
	Button btn_finish = new Button("Finished ",new Icon(VaadinIcon.CHECK));
	Button btn_skip = new Button("Skip ",new Icon(VaadinIcon.CHECK));
	
	HorizontalLayout h1_local= new HorizontalLayout();
	HorizontalLayout h2_im = new HorizontalLayout();
	HorizontalLayout h3_btn = new HorizontalLayout();
	
	Grid<BacnetTagUtilityTable> grid = new Grid<BacnetTagUtilityTable>();
	ListDataProvider<BacnetTagUtilityTable> dataProvider;
	
	@Autowired
	private EdgeService service;
	
	private void init(String para) 
	{
//		System.out.println("ID...."+para); // id only get
		btn_submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_tree.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_finish.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		
		btn_tree.getStyle().set("margin-top", "36px");
		btn_im.getStyle().set("margin-top", "36px");
		
		txt_im.setWidth("30%");
		txt_boardcast_ip.setWidth("20%");
		txt_server_name.setWidth("20%");
		txt_port.setWidth("20%");
		txt_source.setWidth("20%");
		h1_local.setWidthFull();
		h2_im.setWidthFull();
		h3_btn.setWidthFull();
		
		String server_name=service.bacnet_server_name(para);
		txt_server_name.setValue(server_name);
		String port_add=service.get_broadcast_port(server_name);
		txt_port.setValue(port_add);
		String boardcard_ip=service.get_broadcast_ip(server_name);
		txt_boardcast_ip.setValue(boardcard_ip);
		
		
		txt_source.setId("txttopic");
		txt_boardcast_ip.setReadOnly(true);
		txt_port.setReadOnly(true);
		txt_server_name.setReadOnly(true);
		
		btn_tree.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('BacnetTree.html?url="+server_name+"','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_im.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('https://cat.terrestrialplatform.link/login','popUpWindow','height=700,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_submit.addClickListener(s->{
			getUI().get().getCurrent().getPage().executeJs("var node_id=document.getElementById('txttopic').value;"
					+ "return node_id;").then(String.class,abc->{
			
				String obj_type="",obj_name="",obj_id="";
				
				if(!peUtil.isNullString(abc)) {
				String[] aaray1=abc.split(" ");
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
			     
				}
				
				String source="";
				String ip = "";
				String port ="";
				String topic ="";
				
				
				
				source = abc ;
				ip =boardcard_ip;
				port =port_add;
				topic =txt_im.getValue().toString();
				
				if(peUtil.isNullString(source)) {
					Notification.show("Source can not Blank", 3000, Position.MIDDLE);
				}
				
				if(peUtil.isNullString(topic)) {
					Notification.show("Topic can not Blank", 3000, Position.MIDDLE);
				}
				
				if(!peUtil.isNullString(source) && !peUtil.isNullString(topic) ) {

					if(!service.check_bacnet_utility(ip, port,topic,server_name,obj_id,obj_name,para))
					{
						BacnetTagUtilityTable bt = new BacnetTagUtilityTable();
						bt.setBroadcast_ip(boardcard_ip);
						bt.setDevice_id(para);
						bt.setObj_id(obj_id);
						bt.setObj_name(obj_name);
						bt.setPort(port_add);
						bt.setServer_name(server_name);
						bt.setTopic(topic);
						service.bacnet_utility_save(bt);
						
						txt_im.setValue("");
						getUI().get().getCurrent().getPage().executeJs("document.getElementById('txttopic').value='';"
								+ "");
						UI.getCurrent().navigate(BacnetAndIMmappingForm.ROUTE_NAME + "/"+para);
					}
					else
					{
						Notification.show("Already Exists", 3000, Position.MIDDLE);
					}
				
				
				}
				
				
			});
//			UI.getCurrent().navigate(BacnetAndIMmappingForm.ROUTE_NAME + "/"+para);
		});
		
		btn_back.addClickListener(r->{
			UI.getCurrent().navigate(BacnetTagTreeForm.ROUTE_NAME + "/"+para);
		});
		btn_finish.addClickListener(r->{
			UI.getCurrent().navigate(SourceList.class);
		});
		
		btn_skip.addClickListener(r->{
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+para);
		});
		
		// grid code
				grid.removeAllColumns();
				update(server_name);
				grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
				
				Grid.Column<BacnetTagUtilityTable> bacnet_device = grid.addColumn(BacnetTagUtilityTable::getDevice_id).setHeader("Device Id")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<BacnetTagUtilityTable> bacnet_server = grid.addColumn(BacnetTagUtilityTable::getServer_name).setHeader("Server Name")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				
				
				Grid.Column<BacnetTagUtilityTable> bacnet_ojname = grid.addColumn(BacnetTagUtilityTable::getObj_name).setHeader("Object Name")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<BacnetTagUtilityTable> bacnet_objid = grid.addColumn(BacnetTagUtilityTable::getObj_id).setHeader("Object Id")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				

//				Grid.Column<BacnetTagUtilityTable> bacnet_ip = grid.addColumn(BacnetTagUtilityTable::getBroadcast_ip).setHeader("Boardcast IP")
//						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
//						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<BacnetTagUtilityTable> bacnet_topic = grid.addColumn(BacnetTagUtilityTable::getTopic).setHeader("IM Topic")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				
				// delete code for grid
				
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<BacnetTagUtilityTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

					// create edit button for each row
					Button delete = new Button("DELETE");

					// set icon
					delete.setIcon(new Icon(VaadinIcon.TRASH));

					// set theme
					delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
					

					// on click operation
					delete.addClickListener(ed -> {

						
							Dialog dialog = new Dialog();
							dialog.add(new Text("Are you sure you want to delete Mapping...?"));
							dialog.setCloseOnEsc(false);
							dialog.setCloseOnOutsideClick(false);

							Button confirmButton = new Button("Confirm", event -> {
								try {
									
		                            service.delete_bacnet_utility(sourcedata.getId());
									
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.toString());
								}
								dialog.close();

								update(sourcedata.getServer_name());
								UI.getCurrent().navigate(BacnetAndIMmappingForm.ROUTE_NAME + "/"+para);

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
		h1_local.add(txt_boardcast_ip,txt_port,txt_server_name,txt_source,btn_tree);
		h2_im.add(txt_im,btn_im);
		h3_btn.removeAll();
		if(service.check_bacnet_utility_source(para,txt_boardcast_ip.getValue().toString(),txt_port.getValue().toString(),txt_server_name.getValue().toString())) {
			h3_btn.add(btn_back,btn_submit,btn_finish);//,btn_skip
			}else {
			h3_btn.add(btn_back,btn_submit);
			}
		add(h1_local,h2_im,h3_btn,grid);
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) 
	{
		init(parameter);	
	}
	public void update(String url) {

		List<BacnetTagUtilityTable> list = service.list_bacnet(url);
		dataProvider = new ListDataProvider<>(list);
		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
}
