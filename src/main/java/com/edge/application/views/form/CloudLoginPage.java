package com.edge.application.views.form;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.edge.application.views.ConfigureUTILS;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value="cloudlogin")
public class CloudLoginPage extends VerticalLayout{
	
	public static final String  RouteName="cloudlogin";
	
	boolean status_internet=false;
	
	TextField  txt_username= new TextField("User Name:");
	TextField  txt_password= new TextField("Password:");
	
	Button btn_submit= new Button("SUBMIT");
	
	TextField  txt_topic= new TextField("topic:");
	Button btn= new Button("IM");
	
	Label msg_print = new Label();
	
	DirContext ctx = null;
	Hashtable<String, Object> env = new Hashtable<String, Object>();
	
	Dialog dialog = new Dialog();
	Label label = new Label();
	VerticalLayout centerButton = new VerticalLayout();
	
	@PostConstruct
	public void init()
	{
		status_internet=check_status();
		
		txt_username.setWidthFull();
		txt_password.setWidthFull();
		btn_submit.setWidthFull();
		
		btn_submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		msg_print.setText("Pelase Conected Internet ......!");
		
//		https://cat.terrestrialplatform.link/tag/tagTreeIframe
		btn_submit.addClickListener(e->{
			
			String username=txt_username.getValue();
			String pwd=txt_password.getValue().toString();
			env = ConfigureUTILS.eventfetch("cn="+username+","+ConfigureUTILS.userlist, pwd);
			try
			{
				ctx = new InitialDirContext(env);
//				Vector applications = new Vector();
//				Vector group = new Vector();
//				
//				applications=ConfigureUTILS.getApplicationList();
//				//System.out.println("Application list....."+applications);
//				
//				for(int i=0;i<applications.size();i++)
//				{
//					String aap_name1=applications.get(i).toString();
//					//String app_icon2=repo_app_icon.getAppIcon(aap_name1);
//					
//					group=ConfigureUTILS.getGroupList(applications.get(i).toString());
//					//System.out.println("\nApplication.."+applications.get(i).toString());
//					
//					for(int t=0;t<group.size();t++)
//					{
//						//System.out.println("Group...."+group.get(t).toString());
//						if(ConfigureUTILS.isUsers(username,group.get(t).toString(), applications.get(i).toString()))
//						{
//							
//						}
//						
//					}
//				}
				System.out.println("Wlcom");
			}
			catch(Exception ex)
			{
				System.out.println("Error for login......."+ex);
				centerButton.removeAll();
				dialog.open();
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
				label.setText("Select Application..!");
				Button confirmButton = new Button("Okay", ev -> {
					dialog.close();
				});
				centerButton.add(label, confirmButton);
				centerButton.setHorizontalComponentAlignment(Alignment.CENTER, label, confirmButton);
				centerButton.setWidth("100%");

				centerButton.add(label, new HorizontalLayout(confirmButton));
				dialog.add(centerButton);
			}
		});
		
		txt_topic.setId("txttopic");
		btn.addClickListener(er->{
			UI.getCurrent().getPage().executeJavaScript(
					"window.open('BacnetTree.html?url=BACnetDeviceServer1','popUpWindow','height=300,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');");
		});
		
		add(txt_topic,btn);
		
		if(status_internet)
		{
			add(txt_username,txt_password,btn_submit);
		}
		else
		{
			add(msg_print);
		}
		
		
	}
	
	
	
	public static boolean check_status()
	   {
		   boolean status=false;
		   try {
		         URL url = new URL("http://www.google.com");
		         URLConnection connection = url.openConnection();
		         connection.connect();
//		         System.out.println("Internet is connected");
		         status=true;
		      } catch (MalformedURLException e) {
//		         System.out.println("Internet is not connected");
		         status=false;
		      } catch (IOException e) {
//		         System.out.println("Internet is not connected");
		         status=false;
		      }
		   
		   return status;
	   }

}
