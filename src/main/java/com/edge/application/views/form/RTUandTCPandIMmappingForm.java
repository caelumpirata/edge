package com.edge.application.views.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.MQTTTagUtilityTable;
import com.edge.application.views.Table.RTUTagUtilityTable;
import com.edge.application.views.Table.SourceTagMappingTable;
import com.edge.application.views.Table.TCPTagUtilityTable;
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
@Route(value = "rtutcpimmapping" ,layout = MainLayout.class)
public class RTUandTCPandIMmappingForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="rtutcpimmapping";
	

	
	TextField txt_im = new TextField("IM Tag:");
	
	ComboBox<String>  com_tag = new ComboBox<String>("Register Name:");
	
	Button btn_submit = new Button("Mapping",new Icon(VaadinIcon.LINK));
//	Button btn_tree = new Button("MQTT Tree",new Icon(VaadinIcon.FILE_TREE));
	Button btn_im = new Button("IM ",new Icon(VaadinIcon.FILE_TREE));
	Button btn_back = new Button("Back ",new Icon(VaadinIcon.ARROW_LEFT));
	Button btn_finish = new Button("Finished ",new Icon(VaadinIcon.CHECK));
	Button btn_skip = new Button("Skip ",new Icon(VaadinIcon.CHECK));
	
	HorizontalLayout h1_local= new HorizontalLayout();
	HorizontalLayout h2_im = new HorizontalLayout();
	HorizontalLayout h3_btn = new HorizontalLayout();
	
	Grid<RTUTagUtilityTable> grid = new Grid<RTUTagUtilityTable>();
	ListDataProvider<RTUTagUtilityTable> dataProvider;
	
	Grid<TCPTagUtilityTable> grid2 = new Grid<TCPTagUtilityTable>();
	ListDataProvider<TCPTagUtilityTable> dataProvider2;
	
	List<String> ls=new ArrayList<String>();
	
	@Autowired
	private EdgeService service;
	
	private void init(String para) 
	{
		String getprotocol=service.getprotocol(para);
//		System.out.println("ID...."+para); // id only get
		btn_submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		btn_tree.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_im.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_finish.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_skip.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		
//		btn_tree.getStyle().set("margin-top", "36px");
		btn_im.getStyle().set("margin-top", "36px");
		
		txt_im.setWidth("30%");
		com_tag.setWidth("20%");
		h1_local.setWidthFull();
		h2_im.setWidthFull();
		h3_btn.setWidthFull();
		
		ls=new ArrayList<String>();
		for(SourceTagMappingTable st:service.source_id_finadAll(para))
		{
			ls.add(""+st.getTag_name());
			
			
		}
		com_tag.setItems(ls);
		
		
		btn_im.addClickListener(e->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('https://cat.terrestrialplatform.link/login','popUpWindow','height=700,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		btn_submit.addClickListener(s->{
			String tagname=com_tag.getValue().toString();
			String topic =txt_im.getValue().toString();
			
			if(peUtil.isNullString(tagname)) {
				Notification.show("Register Name can not Blank", 3000, Position.MIDDLE);
			}
			if(peUtil.isNullString(topic)) {
				Notification.show("ImTag can not Blank", 3000, Position.MIDDLE);
			}
			if(!peUtil.isNullString(tagname) && !peUtil.isNullString(topic) ) {
			if(getprotocol.equalsIgnoreCase("MODBUS RTU"))
			{
				if(!service.check_rtu_utility(tagname,para))
				{
					RTUTagUtilityTable rt=new RTUTagUtilityTable();
					rt.setSource_id(para);
					rt.setTagname(tagname);
					rt.setTopic(topic);
					service.rtu_utility_save(rt);
				}
				else
				{
					Notification.show("Already Exists", 3000, Position.MIDDLE);
				}
				
			}
			else if(getprotocol.equalsIgnoreCase("MODBUS TCP"))
			{
				if(!service.check_tcp_utility(tagname,para))
				{
					TCPTagUtilityTable rt=new TCPTagUtilityTable();
					rt.setSource_id(para);
					rt.setTagname(tagname);
					rt.setTopic(topic);
					service.tcp_utility_save(rt);
				}
				else
				{
					Notification.show("Already Exists", 3000, Position.MIDDLE);
				}
			}
			txt_im.setValue("");
			UI.getCurrent().navigate(RTUandTCPandIMmappingForm.ROUTE_NAME + "/"+para);
		}
			
		});
		
		btn_back.addClickListener(r->{
			UI.getCurrent().navigate(SourceTagMappingForm.ROUTE_NAME + "/"+para);
		});
		btn_finish.addClickListener(r->{
			UI.getCurrent().navigate(SourceList.class);
		});
		
		btn_skip.addClickListener(r->{
			UI.getCurrent().navigate(sourcePublisherForm.ROUTE_NAME + "/"+para);
		});
		
		// grid code
				grid.removeAllColumns();
				update(para);
				grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
				Grid.Column<RTUTagUtilityTable> rtu_source = grid.addColumn(RTUTagUtilityTable::getSource_id).setHeader("RTU Source ID")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<RTUTagUtilityTable> rtu_tag = grid.addColumn(RTUTagUtilityTable::getTagname).setHeader("RTU Ragister Name")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<RTUTagUtilityTable> rtu_topic = grid.addColumn(RTUTagUtilityTable::getTopic).setHeader("RTU Topic")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				// delete code for grid
				
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<RTUTagUtilityTable> deleteColumn = grid.addComponentColumn(sourcedata -> {

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
									
		                            service.delete_rtu_utility(sourcedata.getId());
									
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.toString());
								}
								dialog.close();

								update(sourcedata.getSource_id());

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
				
		// second grid code
				
				grid2.removeAllColumns();
				update2(para);
				grid2.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
				Grid.Column<TCPTagUtilityTable> tcp_source = grid2.addColumn(TCPTagUtilityTable::getSource_id).setHeader("TCP Source ID")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<TCPTagUtilityTable> tcp_tag = grid2.addColumn(TCPTagUtilityTable::getTagname).setHeader("TCP Ragister Name")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				Grid.Column<TCPTagUtilityTable> tcp_topic = grid2.addColumn(TCPTagUtilityTable::getTopic).setHeader("TCP Topic")
						.setAutoWidth(true).setResizable(true).setSortable(true).setAutoWidth(true)
						.setTextAlign(ColumnTextAlign.CENTER);
				
				// delete code for grid
				
				@SuppressWarnings({ "unused", "deprecation" })
				Grid.Column<TCPTagUtilityTable> deleteColumn2 = grid2.addComponentColumn(sourcedata -> {

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
									
		                            service.delete_tcp_utility(sourcedata.getId());
									
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.toString());
								}
								dialog.close();

								update2(sourcedata.getSource_id());

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
				
			
				grid2.setSizeUndefined();
				grid2.setHeightByRows(true);
				
		h1_local.add(com_tag,txt_im,btn_im);
//		h2_im.add();
		
		
		if(getprotocol.equalsIgnoreCase("MODBUS RTU"))
		{
			h3_btn.removeAll();
			if(service.check_rtu_utility_src_id(para)) {
			h3_btn.add(btn_back,btn_submit,btn_finish);//,btn_skip
			}else {
			h3_btn.add(btn_back,btn_submit);
			}
			add(h1_local,h3_btn,grid);//h2_im,
		}
		else if(getprotocol.equalsIgnoreCase("MODBUS TCP"))
		{
			h3_btn.removeAll();
			if(service.check_tcp_utility_src_id(para)) {
			h3_btn.add(btn_back,btn_submit,btn_finish,btn_skip);
			}else {
			h3_btn.add(btn_back,btn_submit);
			}
			add(h1_local,h3_btn,grid2);//h2_im,
		}
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) 
	{
		init(parameter);	
	}
	public void update(String url) {

		List<RTUTagUtilityTable> list = service.list_rtu(url);
		dataProvider = new ListDataProvider<>(list);
		grid.setItems(list);
		grid.setDataProvider(dataProvider);
	}
	public void update2(String url) {

		List<TCPTagUtilityTable> list = service.list_tcp(url);
		dataProvider2 = new ListDataProvider<>(list);
		grid2.setItems(list);
		grid2.setDataProvider(dataProvider2);
	}
}
