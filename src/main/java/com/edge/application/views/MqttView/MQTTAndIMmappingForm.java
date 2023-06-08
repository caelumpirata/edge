package com.edge.application.views.MqttView;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.OPCUA.OPCUAandIMmappingForm;
import com.edge.application.views.Table.MQTTTagUtilityTable;
import com.edge.application.views.Table.OPCUATagUtilityTable;
import com.edge.application.views.form.MqttProtocolForm;
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
@Route(value = "mqttimmapping" ,layout = MainLayout.class)
public class MQTTAndIMmappingForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="mqttimmapping";
	
	TextField txt_tag= new TextField("Tag:");
	TextField txt_broker = new TextField("Broker:");
	TextField txt_port = new TextField("Port:");
	TextField txt_im = new TextField("IM Tag:");
	
	Button btn_submit = new Button("Mapping",new Icon(VaadinIcon.LINK));
	Button btn_tree = new Button("MQTT Tree",new Icon(VaadinIcon.FILE_TREE));
	Button btn_im = new Button("IM ",new Icon(VaadinIcon.FILE_TREE));
	Button btn_back = new Button("Back ",new Icon(VaadinIcon.ARROW_LEFT));
	Button btn_finish = new Button("Finished",new Icon(VaadinIcon.CHECK));
	Button btn_skip = new Button("Skip ",new Icon(VaadinIcon.CHECK));
	
	
	HorizontalLayout h1_local= new HorizontalLayout();
	HorizontalLayout h2_im = new HorizontalLayout();
	HorizontalLayout h3_btn = new HorizontalLayout();
	
	Grid<MQTTTagUtilityTable> grid = new Grid<MQTTTagUtilityTable>();
	ListDataProvider<MQTTTagUtilityTable> dataProvider;
	
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
		txt_tag.setWidth("20%");
		txt_broker.setWidth("20%");
		txt_port.setWidth("20%");
		h1_local.setWidthFull();
		h2_im.setWidthFull();
		h3_btn.setWidthFull();
		
		String broker_ip=service.get_brokerip(para);
		txt_broker.setValue(broker_ip);
		String port_add=service.get_port(para);
		txt_port.setValue(port_add);
		
		
		txt_tag.setId("txttopic");
		txt_broker.setReadOnly(true);
		txt_port.setReadOnly(true);
		
		btn_tree.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('MQTTTree.html?url="+para+"','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_im.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('https://cat.terrestrialplatform.link/login','popUpWindow','height=700,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_submit.addClickListener(s->{
			getUI().get().getCurrent().getPage().executeJs("var node_id=document.getElementById('txttopic').value;"
					+ "return node_id;").then(String.class,abc->{
						String br =txt_broker.getValue().toString();
						String port =txt_port.getValue().toString();
						String topic =txt_im.getValue().toString();
						String tag ="";
						
						if(!peUtil.isNullString(abc)) {
						tag = abc.split("_")[1].toString();
						}
						
						if(peUtil.isNullString(br)) {
							Notification.show("Broker can not Blank", 3000, Position.MIDDLE);
						}
						if(peUtil.isNullString(port)) {
							Notification.show("Port can not Blank", 3000, Position.MIDDLE);
						}
						if(peUtil.isNullString(topic)) {
							Notification.show("Topic can not Blank", 3000, Position.MIDDLE);
						}	
						if(peUtil.isNullString(tag)) {
							Notification.show("Tag can not Blank", 3000, Position.MIDDLE);
						}	
				if(!peUtil.isNullString(br) && !peUtil.isNullString(port) && !peUtil.isNullString(topic) & !peUtil.isNullString(tag)) {
		
				if(!service.check_mqtt_utility(tag, para,topic,port,br))
				{
					MQTTTagUtilityTable op=new MQTTTagUtilityTable();
					op.setBroker_ip(br);
					op.setPort(port);
					op.setTag(tag);
					op.setSource_id(abc.split("_")[0].toString());
					op.setTopic(topic);
					service.mqtt_utility_save(op);
					txt_im.setValue("");
					getUI().get().getCurrent().getPage().executeJs("document.getElementById('txttopic').value='';"
							+ "");
					UI.getCurrent().navigate(MQTTAndIMmappingForm.ROUTE_NAME + "/"+para);
				}
				else
				{
					Notification.show("Already Exists", 3000, Position.MIDDLE);
				}
				}
				
				
				
			});
			
		});
		
		btn_back.addClickListener(r->{
			UI.getCurrent().navigate(MqttProtocolForm.ROUTE_NAME + "/"+para);
		});
		btn_finish.addClickListener(r->{
			UI.getCurrent().navigate(SourceList.class);
		});
		
		btn_skip.addClickListener(r->{
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+para);
		});
		
		// grid code
				grid.removeAllColumns();
				update(broker_ip);
				grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
				Grid.Column<MQTTTagUtilityTable> mqtt_broker = grid.addColumn(MQTTTagUtilityTable::getBroker_ip).setHeader("Broker IP")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<MQTTTagUtilityTable> mqtt_port = grid.addColumn(MQTTTagUtilityTable::getPort).setHeader("Port")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<MQTTTagUtilityTable> opc_source = grid.addColumn(MQTTTagUtilityTable::getSource_id).setHeader("SourceID")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<MQTTTagUtilityTable> mqtt_tag = grid.addColumn(MQTTTagUtilityTable::getTag).setHeader("Tag Name")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				

				Grid.Column<MQTTTagUtilityTable> mqtt_topic = grid.addColumn(MQTTTagUtilityTable::getTopic).setHeader("IM Topic")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				// delete code for grid
				
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<MQTTTagUtilityTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
									
		                            service.delete_mqtt_utility(sourcedata.getId());
									
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.toString());
								}
								dialog.close();

								update(sourcedata.getBroker_ip());

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
		h1_local.add(txt_broker,txt_port,txt_tag,btn_tree);
		h2_im.add(txt_im,btn_im);
		h3_btn.removeAll();
		if(service.check_mqtt_utility_source(para)) {
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

		List<MQTTTagUtilityTable> list = service.list_mqtt(url);
		dataProvider = new ListDataProvider<>(list);
		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
}
