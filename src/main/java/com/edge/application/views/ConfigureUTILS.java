package com.edge.application.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ConfigureUTILS {

	public static String auth_method = "simple";
	// set the LDAP client Version
	public static String ldap_version = "3";
	//public static String ldap_host = "139.59.20.178";
	public static String ldap_host = "localhost";
	// This is our LDAP Server's Port
	public static String ldap_port = "10389";
	//public static String base_dn = "DC=innovationintellect,DC=com";
	public static String base_dn = "DC=silcore,DC=in";
	public static String Companyname = "MIPL";

//	public static String defaultUser = "cn=admin,dc=innovationintellect,dc=com";
//	public static String defaultPass = "innovation@123";
	
	public static String defaultUser = "cn=admin,dc=silcore,dc=in";
	public static String defaultPass = "silcore";

	public static String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	public static String PROVIDER_URL = "ldap://" + ldap_host + ":" + ldap_port;
	public static String SECURITY_AUTHENTICATION = auth_method;
	public static String LdapVersion_javamapping = "java.naming.ldap.version";
	//public static String OrgUnit = "ou=" + Companyname + ",dc=innovationintellect,dc=com";
	
	public static String OrgUnit = "ou=" + Companyname + ",dc=silcore,dc=in";

	public static String emsapplication = "ou=EMS," + OrgUnit + "";

	public static String emsmanager = "ou=Manager," + emsapplication + "";
	public static String emsoperator = "ou=Operator," + emsapplication + "";
	public static String emssupervisor = "ou=Supervisor," + emsapplication + "";

	public static String umsapplication = "ou=UMS," + OrgUnit + "";

	public static String umsmanager = "ou=Manager," + umsapplication + "";
	public static String umsoperator = "ou=Operator," + umsapplication + "";
	public static String umssupervisor = "ou=Supervisor," + umsapplication + "";

	public static String hvacapplication = "ou=HVAC," + OrgUnit + "";
	
	public static String ahuapplication = "ou=AHU," +   OrgUnit + "";

	public static String ahumanager = "ou=Manager," + ahuapplication + "";
	public static String ahuoperator = "ou=Operator," + ahuapplication + "";
	public static String ahusupervisor = "ou=Supervisor," + ahuapplication + "";

	public static String csmapplication = "ou=CSM,"  + OrgUnit + "";

	public static String csmmanager = "ou=Manager," + csmapplication + "";
	public static String csmoperator = "ou=Operator," + csmapplication + "";
	public static String csmsupervisor = "ou=Supervisor," + csmapplication + "";

	public static String plantapplication = "ou=Plant Manager," + OrgUnit + "";

	public static String plantmanager = "ou=Manager," + plantapplication + "";
	public static String plantoperator = "ou=Operator," + plantapplication + "";
	public static String plantsupervisor = "ou=Supervisor," + plantapplication + "";

	public static String userlist = "ou=Users," + OrgUnit + "";
	public static String adminapplication = "ou=Administrator," + OrgUnit;
	public static String superadmin = "ou=SuperAdmin,ou=Administrator," + OrgUnit;

	public ConfigureUTILS() {

	}

//	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    String name = auth.getName(); //get logged in username

	@SuppressWarnings("rawtypes")
	public static boolean isSaveUser(String cn, String f_name, String uid1, String l_name, String email, String mobile,
			String password1, Hashtable env) {

		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			final Attributes container = new BasicAttributes();
			final Attribute commonName = new BasicAttribute("cn", cn);
			final Attribute givenName = new BasicAttribute("givenName", f_name);
			final Attribute uid = new BasicAttribute("uid", uid1);
			final Attribute surName = new BasicAttribute("sn", l_name);
			final Attribute Email12 = new BasicAttribute("mail", email);
			final Attribute Mobile2 = new BasicAttribute("mobile", mobile);
			final Attribute objClasses = new BasicAttribute("objectClass");
			objClasses.add("inetOrgPerson");

			// Add password
			final Attribute userPassword = new BasicAttribute("userpassword", password1);

			// Add these to the container
			container.put(objClasses);
			container.put(commonName);
			container.put(givenName);
			// container.put(groupid1);
			container.put(uid);
			container.put(surName);
			container.put(Email12);
			container.put(Mobile2);
			container.put(userPassword);

			// Create the entry

			ctx.createSubcontext("cn=" + cn + "," + userlist + "", container);

			return true;
		} catch (Exception ex) {
		}

		return false;
	}
	

	@SuppressWarnings("rawtypes")
	public static Vector UserList(Hashtable env, String fildename) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", fildename, userlist);

		return vc_cnname;
	}

	public static Vector AttributesFetch(Hashtable env, String obj, String attr1, String ldap_dn) {
		Vector vc_name = new Vector();
		DirContext ctx = null;
		// Hashtable<String, Object> env = new Hashtable<String, Object>();

		Attributes attrs;

		// env=memberUtil.eventfetch(bssConstants.defaultUser,bssConstants.defaultPass);
		try {
			ctx = new InitialDirContext(env);
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			String searchFilter = "(objectClass=" + obj + ")";
			String searchBase = ldap_dn;
			int totalResults = 0;
			String returnedAtts[] = { "" + attr1 + "" };
			searchCtls.setReturningAttributes(returnedAtts);

			NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
			Vector vc_membername = new Vector();

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				String aa = attrs.get("" + attr1 + "").get().toString();
				vc_name.add(aa);
			}

		} catch (Exception ex) {
		}

		return vc_name;
	}

	public static String AttributesFetch2(Hashtable env, String obj, String attr1, String ldap_dn) {
		// Vector vc_name=new Vector();
		String abc = "";
		DirContext ctx = null;
		// Hashtable<String, Object> env = new Hashtable<String, Object>();

		Attributes attrs;

		// env=memberUtil.eventfetch(bssConstants.defaultUser,bssConstants.defaultPass);
		try {
			ctx = new InitialDirContext(env);
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(objectClass=" + obj + ")";
			String searchBase = ldap_dn;
			int totalResults = 0;
			String returnedAtts[] = { "" + attr1 + "" };
			searchCtls.setReturningAttributes(returnedAtts);

			NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
			Vector vc_membername = new Vector();

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				String aa = attrs.get("" + attr1 + "").get().toString();
				abc = aa;
			}

		} catch (Exception ex) {
		}

		return abc;
	}

	public static Hashtable eventfetch(String loginuser, String password) {
		Hashtable<String, Object> env = new Hashtable<String, Object>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		// env.put(Context.SECURITY_AUTHENTICATION, auth_method);
		env.put(Context.SECURITY_PRINCIPAL, loginuser);
		env.put(Context.SECURITY_CREDENTIALS, password);
		// env.put(Context.SECURITY_CREDENTIALS, ldap_pw);
		env.put(LdapVersion_javamapping, ldap_version);

		return env;
	}

	public static boolean isMainAdmin(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", superadmin);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isEmsManager(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", emsmanager);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isEmsOperator(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", emsoperator);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isEmsSupervisor(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", emssupervisor);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isUmsManager(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", umsmanager);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isUmsOperator(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", umsoperator);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isUmsSupervisor(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", umssupervisor);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isAHUManager(String username,Hashtable env)
	  {
		  
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", ahumanager);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}
	public static boolean isAHUOprator(String username,Hashtable env)
	  {
		  
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", ahuoperator);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static boolean isAHUSupervisor(String username,Hashtable env)
	  {
		  
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", ahusupervisor);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}
	public static boolean isCSMManager(String username,Hashtable env)
	  {
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", csmmanager);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}
	public static boolean isCSMOprator(String username,Hashtable env)
	  {
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", csmoperator);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static boolean isCSMSupervisor(String username,Hashtable env)
	  {
		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", csmsupervisor);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isPlantManager(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", plantmanager);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isPlantOperator(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", plantoperator);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isPlantSupervisor(String username, Hashtable env) {

		String app_username = "";

		Vector vc_cnname = new Vector();
		vc_cnname = AttributesFetch(env, "inetOrgPerson", "cn", plantsupervisor);
		if (!vc_cnname.isEmpty()) {
			for (int i = 0; i < vc_cnname.size(); i++) {
				app_username = vc_cnname.get(i).toString();
				if (app_username.equals(username)) {
					return true;
				}
			}
		}

		return false;
	}

	public static String applicationcheck(String application_name) {
		String staus = "NO";
		Vector vc_ou = new Vector();

		DirContext ctx = null;
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		Attributes attrs1;

		env = eventfetch(defaultUser, defaultPass);

		try {

			ctx = new InitialDirContext(env);

			vc_ou = AttributesFetch(env, "organizationalUnit", "ou", OrgUnit);

			if (!vc_ou.isEmpty()) {
				for (int t = 0; t < vc_ou.size(); t++) {
					String items2 = vc_ou.get(t).toString();

					if (application_name.equals(items2)) {
						staus = "Yes";
					}

				}
			}

			// System.out.println("EMSS::"+vc_ou);

		} catch (Exception authEx) {
			authEx.printStackTrace();
			// System.out.println("<b>LDAP authentication failed! Try Again</b> <br><a
			// href=\""+bssConstants.DomainName+bssConstants.ServletRoot+"LoginForm\">Login</a>");
		}

		return staus;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getApplicationList()
	{
		Vector vc_app = new Vector();
		Vector vc_app_final = new Vector();
		
		DirContext ctx = null;
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		
		env = ConfigureUTILS.eventfetch(ConfigureUTILS.defaultUser, ConfigureUTILS.defaultPass);
		Collection<String> cls2 = new ArrayList<String>();

		try 
		{
			ctx = new InitialDirContext(env);
			
			vc_app = ConfigureUTILS.AttributesFetch(env, "organizationalUnit", "ou",ConfigureUTILS.OrgUnit);
				if (!vc_app.isEmpty())
				{
					for (int j = 0; j < vc_app.size(); j++)
					{
						String items = vc_app.get(j).toString();
						if (!items.equals("Administrator") && !items.equals("Users")) //
						{
							vc_app_final.add(items.trim());
						}
					}
					
				}

			
		}
		catch(Exception ez)
		{
			System.out.println("Error application name get...."+ez);
		}
		
		return vc_app_final;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getGroupList(String applications)
	{
		Vector vc_app = new Vector();
		Vector vc_app_final = new Vector();
		
		DirContext ctx = null;
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		
		env = ConfigureUTILS.eventfetch(ConfigureUTILS.defaultUser, ConfigureUTILS.defaultPass);

		try 
		{
			ctx = new InitialDirContext(env);
			
			vc_app = ConfigureUTILS.AttributesFetch(env, "organizationalUnit", "ou","ou="+applications+","+ConfigureUTILS.OrgUnit);
				if (!vc_app.isEmpty())
				{
					for (int j = 0; j < vc_app.size(); j++)
					{
						String items = vc_app.get(j).toString();
						
							vc_app_final.add(items.trim());
						
					}
					
				}

			
		}
		catch(Exception ez)
		{
			System.out.println("Error application name get...."+ez);
		}
		
		return vc_app_final;
	}
	
	public static boolean isUsers(String username1,String groupname,String application)
	  {
		  
		String app_username = "";
		
		String linked="ou="+groupname+",ou="+application+","+ConfigureUTILS.OrgUnit;
		DirContext ctx = null;
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		
		env = ConfigureUTILS.eventfetch(ConfigureUTILS.defaultUser, ConfigureUTILS.defaultPass);
		
		try 
		{
			ctx = new InitialDirContext(env);
			Vector vc_cnname = new Vector();
			vc_cnname =ConfigureUTILS.AttributesFetch(env, "inetOrgPerson", "cn", linked);
			if (!vc_cnname.isEmpty()) {
				for (int i = 0; i < vc_cnname.size(); i++) {
					app_username = vc_cnname.get(i).toString();
					if (app_username.equals(username1)) {
						return true;
					}
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error for user check......"+ex);
		}
		

		return false;
	}

}
