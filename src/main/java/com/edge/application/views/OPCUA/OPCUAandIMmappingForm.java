package com.edge.application.views.OPCUA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.OPCUATagUtilityTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.form.MqttProtocolForm;
import com.edge.application.views.form.OpcuaProtocolForm;
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
@Route(value = "opcuaimmapping" ,layout = MainLayout.class)
public class OPCUAandIMmappingForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="opcuaimmapping";
	
	TextField txt_nodeid= new TextField("Node Id:");
	TextField txt_url = new TextField("URL:");
	TextField txt_im = new TextField("IM Tag:");
	
	Button btn_submit = new Button("Mapping",new Icon(VaadinIcon.LINK));
	Button btn_tree = new Button("OPCUA Tree",new Icon(VaadinIcon.FILE_TREE));
	Button btn_im = new Button("IM ",new Icon(VaadinIcon.FILE_TREE));
	Button btn_back = new Button("Back ",new Icon(VaadinIcon.ARROW_LEFT));
	Button btn_finish = new Button("Finished ",new Icon(VaadinIcon.CHECK));
	Button btn_skip = new Button("Skip ",new Icon(VaadinIcon.CHECK));
	
	
	HorizontalLayout h1_local= new HorizontalLayout();
	HorizontalLayout h2_im = new HorizontalLayout();
	HorizontalLayout h3_btn = new HorizontalLayout();
	
	Grid<OPCUATagUtilityTable> grid = new Grid<OPCUATagUtilityTable>();
	ListDataProvider<OPCUATagUtilityTable> dataProvider;
	
	
	
	@Autowired
	private EdgeService service;
	
	private void init(String para) 
	{
		removeAll();
//		System.out.println("ID...."+para); // id only get
		btn_submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_tree.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_finish.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_skip.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		
		btn_tree.getStyle().set("margin-top", "36px");
		btn_im.getStyle().set("margin-top", "36px");
		
		txt_im.setWidth("30%");
		txt_nodeid.setWidth("30%");
		txt_url.setWidth("30%");
		h1_local.setWidthFull();
		h2_im.setWidthFull();
		
		String endpoint_url=service.geturl(para);
		txt_url.setValue(endpoint_url);
		
		
		txt_nodeid.setId("txtnode");
		txt_url.setReadOnly(true);
		
		btn_tree.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('tree.html?url="+endpoint_url+"','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_im.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('https://cat.terrestrialplatform.link/login','popUpWindow','height=700,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_submit.addClickListener(s->{
			getUI().get().getCurrent().getPage().executeJs("var node_id=document.getElementById('txtnode').value;"
					+ "return node_id;").then(String.class,abc->{
				System.out.println("New Tag Id::::"+abc);
				
				
				String url =endpoint_url;
				String node =endpoint_url;
				String topic =txt_im.getValue().toString();
				if(peUtil.isNullString(url)) {
					Notification.show("Url can not Blank", 3000, Position.MIDDLE);
				}
				if(peUtil.isNullString(node)) {
					Notification.show("Node can not Blank", 3000, Position.MIDDLE);
				}
				if(peUtil.isNullString(topic)) {
					Notification.show("Topic can not Blank", 3000, Position.MIDDLE);
				}
				
				if(!peUtil.isNullString(url) && !peUtil.isNullString(node) && !peUtil.isNullString(topic)) {
				if(!service.check_opc_utility(node, url,topic))
				{
					OPCUATagUtilityTable op=new OPCUATagUtilityTable();
					op.setNodeid(abc);
					op.setTopic(topic);
					op.setUrl(endpoint_url);
					op.setSource_id(para);
					service.opcua_utility_save(op);
					update(endpoint_url);	
					txt_im.setValue("");
					getUI().get().getCurrent().getPage().executeJs("document.getElementById('txtnode').value='';"
							+ "");
					UI.getCurrent().navigate(OPCUAandIMmappingForm.ROUTE_NAME + "/"+para);
				}
				else
				{
					Notification.show("Already Exists", 3000, Position.MIDDLE);
				}
				}
				
				
			});
			
		});
		btn_back.addClickListener(r->{
			UI.getCurrent().navigate(OpcuaProtocolForm.ROUTE_NAME + "/"+para);
		});
		btn_finish.addClickListener(r->{
			UI.getCurrent().navigate(SourceList.class);
		});
		
		btn_skip.addClickListener(r->{
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+para);
		});
		
		// grid code
		grid.removeAllColumns();
		update(endpoint_url);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		Grid.Column<OPCUATagUtilityTable> opc_url = grid.addColumn(OPCUATagUtilityTable::getUrl).setHeader("URL")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<OPCUATagUtilityTable> opc_nodeid = grid.addColumn(OPCUATagUtilityTable::getNodeid).setHeader("NodeId")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<OPCUATagUtilityTable> opc_source = grid.addColumn(OPCUATagUtilityTable::getSource_id).setHeader("SourceID")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		
		Grid.Column<OPCUATagUtilityTable> opc_topic = grid.addColumn(OPCUATagUtilityTable::getTopic).setHeader("IM Topic")
				.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
				.setTextAlign(ColumnTextAlign.CENTER);
		// delete code for grid
		
		@SuppressWarnings({ "unused", "deprecation" })
		Grid.Column<OPCUATagUtilityTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
							
                            service.delete_opc_utility(sourcedata.getId());
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.toString());
						}
						dialog.close();
						update(sourcedata.getUrl());
						UI.getCurrent().navigate(OPCUAandIMmappingForm.ROUTE_NAME + "/"+para);

						

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
		h1_local.add(txt_url,txt_nodeid,btn_tree);
		h2_im.add(txt_im,btn_im);
		
		h3_btn.removeAll();
		if(service.check_opc_utility_url(para)) {
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

		List<OPCUATagUtilityTable> list = service.list_opcua(url);
		dataProvider = new ListDataProvider<>(list);
		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
}
