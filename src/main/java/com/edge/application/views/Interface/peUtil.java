package com.edge.application.views.Interface;


import java.util.*;
import java.util.Date;
import java.io.*;
import java.util.LinkedHashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
public class peUtil {
	public static String time_stamp="";
	public static String starting_timer_mg="";
	public static int	debug = 2;
	public static boolean isNullString(String value )
	{
		
		if( value.equals(null) || value.equals("") || value.equals("null"))	{
			return true;
		}
		return false;
	}
	public static String obj2str(Object value )
	{
		String str  = (String)value.toString();
		return str;
	}

	public static int obj2Int(String value )
	{
		int val  = Integer.parseInt(value);
		
		return val;
	}
	public static String getCurrentYear()	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date dt = new java.util.Date(); 
		String date = dateFormat.format(dt); 
		return date.toString();
	}
	public synchronized static long timeDifference(String date1,String date2){
		
		System.out.println(date1+"Date-2"+date2);
	 long i=0;
	 long timeDifInMilliSec=0;
	///////date 1
	 try	{
	String []spli_1=date1.split(" ");

	String []spli_1_1=obj2str(spli_1[0]).split("-");
	int yr_1	=	obj2Int(spli_1_1[0]);
	int m_1	=	obj2Int(spli_1_1[1]);
	int d_1	=	obj2Int(spli_1_1[2]);

	String []spli_1_2=obj2str(spli_1[1]).split(":");
	int hh_1	=	obj2Int(spli_1_2[0]);
	int mm_1	=	obj2Int(spli_1_2[1]);
	int ss_1	=obj2Int(spli_1_2[2]);

	//////

	///////date 2
	String []spli_3=date2.split(" ");

	String []spli_1_3=obj2str(spli_3[0]).split("-");
	int yr_2	=	obj2Int(spli_1_3[0]);
	int m_2	=	obj2Int(spli_1_3[1]);
	int d_2	=	obj2Int(spli_1_3[2]);

	String []spli_1_4=obj2str(spli_3[1]).split(":");
	int hh_2	=	obj2Int(spli_1_4[0]);
	int mm_2	=	obj2Int(spli_1_4[1]);

	System.out.println("Dot value-"+spli_1_4[2]);
	int len =spli_1_4[2].length();
	String dot=obj2str(spli_1_4[2]).substring(0, 2);
	System.out.println("dot-"+dot);

	int ss_2	=	obj2Int(dot);

	//////
	//Date1
	Calendar cal1 = Calendar.getInstance();
	cal1.set(yr_1, m_1, d_1, hh_1, mm_1, ss_1);
	Date dt1 = cal1.getTime();

	//Date2
	Calendar cal2 = Calendar.getInstance();
	cal2.set(yr_2, m_2, d_2, hh_2, mm_2, ss_2);
	Date dt2 = cal2.getTime();


	//Time Difference Calculations Begin
	long milliSec1 = dt1.getTime();
	long milliSec2 = dt2.getTime();


	if(milliSec1 >= milliSec2)
	{
	    timeDifInMilliSec = milliSec1 - milliSec2;
	}
	else
	{
	    timeDifInMilliSec = milliSec2 - milliSec1;
	}

	/*long timeDifSeconds = timeDifInMilliSec / 1000;
	long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
	long timeDifHours = timeDifInMilliSec / (60 * 60 * 1000);
	long timeDifDays = timeDifInMilliSec / (24 * 60 * 60 * 1000);

	System.out.println("Time differences expressed in various units are given below");
	System.out.println(timeDifInMilliSec + " Milliseconds");
	System.out.println(timeDifSeconds + " Seconds");
	System.out.println(timeDifMinutes + " Minutes");
	System.out.println(timeDifHours + " Hours");
	System.out.println(timeDifDays + " Days");*/
	 }catch(Exception e){
		 System.out.println("Milliseconds error -"+e);
	 }
	return timeDifInMilliSec;
	}
	public synchronized static long returnMinute(String date1,String date2){
		
		System.out.println(date1+"Date-2"+date2);
	 long i=0;
	 long timeDifInMilliSec=0;
	 long timeDifMinutes =0;
	///////date 1
	 try	{
	String []spli_1=date1.split(" ");

	String []spli_1_1=obj2str(spli_1[0]).split("-");
	int yr_1	=	obj2Int(spli_1_1[0]);
	int m_1	=	obj2Int(spli_1_1[1]);
	int d_1	=	obj2Int(spli_1_1[2]);

	String []spli_1_2=obj2str(spli_1[1]).split(":");
	int hh_1	=	obj2Int(spli_1_2[0]);
	int mm_1	=	obj2Int(spli_1_2[1]);
	int ss_1	=	obj2Int(spli_1_2[2]);

	//////

	///////date 2
	String []spli_3=date2.split(" ");

	String []spli_1_3=obj2str(spli_3[0]).split("-");
	int yr_2	=	obj2Int(spli_1_3[0]);
	int m_2	=	obj2Int(spli_1_3[1]);
	int d_2	=	obj2Int(spli_1_3[2]);

	String []spli_1_4=obj2str(spli_3[1]).split(":");
	int hh_2	=	obj2Int(spli_1_4[0]);
	int mm_2	=	obj2Int(spli_1_4[1]);

	System.out.println("Dot value-"+spli_1_4[2]);
	int len =spli_1_4[2].length();
	String dot=obj2str(spli_1_4[2]).substring(0, 2);
	System.out.println("dot-"+dot);

	int ss_2	=	obj2Int(dot);

	//////
	//Date1
	Calendar cal1 = Calendar.getInstance();
	cal1.set(yr_1, m_1, d_1, hh_1, mm_1, ss_1);
	Date dt1 = cal1.getTime();

	//Date2
	Calendar cal2 = Calendar.getInstance();
	cal2.set(yr_2, m_2, d_2, hh_2, mm_2, ss_2);
	Date dt2 = cal2.getTime();


	//Time Difference Calculations Begin
	long milliSec1 = dt1.getTime();
	long milliSec2 = dt2.getTime();


	if(milliSec1 >= milliSec2)
	{
	    timeDifInMilliSec = milliSec1 - milliSec2;
	}
	else
	{
	    timeDifInMilliSec = milliSec2 - milliSec1;
	}

	//long timeDifSeconds = timeDifInMilliSec / 1000;
	 timeDifMinutes = timeDifInMilliSec / (60 * 1000);
	//long timeDifHours = timeDifInMilliSec / (60 * 60 * 1000);
	//long timeDifDays = timeDifInMilliSec / (24 * 60 * 60 * 1000);

	/*System.out.println("Time differences expressed in various units are given below");
	System.out.println(timeDifInMilliSec + " Milliseconds");
	System.out.println(timeDifSeconds + " Seconds");
	System.out.println(timeDifMinutes + " Minutes");
	System.out.println(timeDifHours + " Hours");
	System.out.println(timeDifDays + " Days");*/
	 }catch(Exception e){
		 System.out.println("Milliseconds error -"+e);
	 }
	return timeDifMinutes;
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
	public static String db_datetime(int yr, int mo, int dt, int hr, int min, int sec, String ampm)
	{
	  StringBuffer str = new StringBuffer();

	  str.append(db_date(yr, mo, dt));
	  if (str.length() > 0) {
	    str.append(" ");
	    str.append(db_time(hr, min, sec, ampm));
	  }

	  return str.toString();
	}

	public static String db_datetime()
	{
	  StringBuffer str = new StringBuffer();

	  str.append(db_date());
	   if (str.length() > 0) {
	    str.append(" ");
	    str.append(db_time());
	  }

	  return str.toString();
	}

	public static String db_datetime(long utn)
	{
	  StringBuffer str = new StringBuffer();

	  str.append(db_date(utn));
	  if (str.length() > 0) {
	    str.append(" ");
	    str.append(db_time(utn));
	  }

	  return str.toString();
	}

	public static String db_datetime(String prefix, LinkedHashMap htQueryString)
	{
	  StringBuffer str = new StringBuffer();

	  str.append(db_date(prefix, htQueryString));
	  if (str.length() > 0) {
	    str.append(" ");
	    str.append(db_time(prefix, htQueryString));
	  }

	  return str.toString();
	}
	public static String db_date()
	{
		  long utn = System.currentTimeMillis();
		    utn = new Date().getTime();
		    utn = utn / 1000L;
	  return db_date(utn);
	}

	public static String db_date(long utn)
	{
	  StringBuffer str = new StringBuffer();

	  Calendar cal = Calendar.getInstance();
	  cal.setTimeInMillis(utn * 1000L);

	  int yr = cal.get(1);
	  int mo = cal.get(2) + 1;
	  int dt = cal.get(5);
	  
	  return db_date(yr, mo, dt);
	}

	public static String db_date(String prefix, LinkedHashMap htQueryString)
	{
	  int yr = obj2Int(htQueryString.get(prefix + "_yr").toString());
	  int mo = obj2Int(htQueryString.get(prefix + "_mo").toString());
	  int dt = obj2Int(htQueryString.get(prefix + "_dt").toString());

	  return db_date(yr, mo, dt);
	}

	public static String db_date(int yr, int mo, int dt)
	{
	  String yr_str;
	  String mo_str;
	  String dt_str;
	  StringBuffer str = new StringBuffer();

	  if ((yr == 0) || (mo == 0) || (dt == 0))
	  {
	    return "";
	  }

	  if (yr < 10) {
	    yr_str = "0" + yr;
	  }
	  else {
	    yr_str = String.valueOf(yr);
	  }

	  if (mo < 10) {
	    mo_str = "0" + mo;
	  }
	  else {
	    mo_str = String.valueOf(mo);
	  }

	  if (dt < 10) {
	    dt_str = "0" + dt;
	  }
	  else {
	    dt_str = String.valueOf(dt);
	  }
	  str.append(yr_str);
	  str.append("-");
	  str.append(mo_str);
	  str.append("-");
	  str.append(dt_str);
	  /*if (driver.equalsIgnoreCase("oracle")) {
	    str.append(dt_str);
	    str.append("-");
	    str.append(mo_str);
	    str.append("-");
	    str.append(yr_str);
	  }
	  else if (driver.equalsIgnoreCase("mysql")) {
	    str.append(yr_str);
	    str.append("-");
	    str.append(mo_str);
	    str.append("-");
	    str.append(dt_str);
	  }*/

	  return str.toString();
	}

	  

	public static String db_time(int hr, int min, int sec, String ampm)
	{
	  String hr_str;
	  String min_str;
	  String sec_str;
	  StringBuffer str = new StringBuffer();

	  if ((hr < 12) && (ampm.equalsIgnoreCase("PM")))
	  {
	    hr += 12;
	  }
	  else if ((hr == 12) && (ampm.equalsIgnoreCase("AM")))
	  {
	    hr = 0;
	  }
	  if (hr == 24)
	  {
	    hr = 0;
	  }

	  if (hr < 10) {
	    hr_str = "0" + hr;
	  }
	  else {
	    hr_str = String.valueOf(hr);
	  }

	  if (min < 10) {
	    min_str = "0" + min;
	  }
	  else {
	    min_str = String.valueOf(min);
	  }

	  if (sec < 10) {
	    sec_str = "0" + sec;
	  }
	  else {
	    sec_str = String.valueOf(sec);
	  }

	  str.append(hr_str);
	  str.append(":");
	  str.append(min_str);
	  str.append(":");
	  str.append(sec_str);

	  return str.toString();
	}

	public static String db_time()
	{
	  Calendar cal = new GregorianCalendar();
	  StringBuffer str = new StringBuffer();
	  int hr = cal.get(11);
	  int min = cal.get(12);
	  int sec = cal.get(13);

	  return db_time(hr, min, sec, "");
	}

	public static String db_time(long utn)
	{
	  StringBuffer str = new StringBuffer();

	  Calendar cal = Calendar.getInstance();
	  cal.setTimeInMillis(utn * 1000L);

	  int hr = cal.get(11);
	  int min = cal.get(12);
	  int sec = cal.get(13);

	  return db_time(hr, min, sec, "");
	}

	public static String db_time(String prefix, LinkedHashMap htQueryString)
	{
	  int hr = obj2Int(htQueryString.get(prefix + "_hr").toString());
	  int min = obj2Int(htQueryString.get(prefix + "_min").toString());
	  int sec = obj2Int(htQueryString.get(prefix + "_sec").toString());
	  String ampm = obj2str(htQueryString.get(prefix + "_ampm"));

	  return db_time(hr, min, sec, ampm);
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
	public synchronized static String getCurentWebTime()	{

		String  curr="";
		
		try{
			URL url = new URL("http://www.silcore.in/servlet/tgtDateTime");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.connect();
					 
			 	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				StringBuffer buffer = new StringBuffer();
				while ((line = rd.readLine()) != null)
				{
				buffer.append(line);
				}
				curr	=	buffer.toString();
				rd.close();
				conn.disconnect();
			
			}catch(Exception e2){
				System.out.println(e2);
			}
		
		
		return curr.toString();
		
	}
	public static String genSmallUniqueID()
	{
	    
	    Random diceRoller = new Random();
		String str="";
		for (int i = 0; i < 10; i++) {
		  int roll = diceRoller.nextInt(6) + 1;
		  str=str+""+roll;
		}
	   
	    return str;
	}
	public static LinkedHashMap getMonth()	{

		LinkedHashMap	ht = new LinkedHashMap();


		ht.put( "January", "January" );
		ht.put( "February", "February" );
		ht.put( "March", "March" );
		ht.put( "April", "April" );
		ht.put( "May", "May" );
		ht.put( "June", "June" );
		ht.put( "Jully", "Jully" );
		ht.put( "August", "August" );
		ht.put( "September", "September" );
		ht.put( "October", "October" );
		ht.put( "November", "November" );
		ht.put( "December", "December" );
		return( ht );
		}
	public static String[] getHour()	{

		String hours[]=new String[24];
		for(int i=0;i<24;i++)
		{
			hours[i]=""+i;
		}
		
		return hours;
	}


	public static String[] getMinutes()	{
		
		String min[]=new String[60];
		for(int i=0;i<60;i++)
		{
			min[i]=""+i;
		}
		
		return min;
	}



	public static String[] getSeconds()	{
		
		String sec[]=new String[60];
		for(int i=0;i<60;i++)
		{
			sec[i]=""+i;
		}
		
		return sec;
	}

	public static String returnMonthName(int month)
	{
	  String name="";
	  if(month==1){
		  name="January"; 
	  }
	  else if(month==2){
		  name="February"; 
	  }
	  else if(month==3){
		  name="March"; 
	  }
	  else if(month==4){
		  name="April"; 
	  }
	  else if(month==5){
		  name="May"; 
	  }
	  else if(month==6){
		  name="June"; 
	  }
	  else if(month==7){
		  name="July"; 
	  }
	  else if(month==8){
		  name="August"; 
	  }
	  else if(month==9){
		  name="September"; 
	  }
	  else if(month==10){
		  name="October"; 
	  }
	  else if(month==11){
		  name="November"; 
	  }
	  else if(month==12){
		  name="December"; 
	  }
	  return name.toString();
	}
	public static double format(double val, int min_digits, int max_digits)
	{
	  NumberFormat nf = NumberFormat.getInstance();
	  nf.setMinimumFractionDigits(min_digits);
	  nf.setMaximumFractionDigits(max_digits);

	  return Double.parseDouble(nf.format(val));
	}
	 public static String munge(String str)
	    {
	        StringBuffer munged = new StringBuffer();
	        for(int i = 0; i < str.length(); i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
	            {
	                munged.append(ch);
	            }
	        }

	        String rtn = munged.toString();
	        rtn = rtn.toLowerCase();
	        rtn.trim();
	        return rtn;
	    }

	    public static String ucf(String lc)
	    {
	        if(isNullString(lc))
	        {
	            return lc;
	        }
	        int len = lc.length();
	        String uc = lc.toUpperCase();
	        StringBuffer rtn = new StringBuffer();
	        boolean boundary = true;
	        for(int i = 0; i < len; i++)
	        {
	            char ch = lc.charAt(i);
	            if(ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '_')
	            {
	                if(boundary)
	                {
	                    rtn.append(uc.charAt(i));
	                } else
	                {
	                    rtn.append(lc.charAt(i));
	                }
	                boundary = false;
	            } else
	            {
	                boundary = true;
	                rtn.append(lc.charAt(i));
	            }
	        }

	        return rtn.toString();
	    }

	    public static String substring(String str, int begin, int end)
	    {
	        int len = str.length();
	        if(len < end)
	        {
	            return str.substring(begin);
	        } else
	        {
	            return str.substring(begin, end);
	        }
	    }
	    public static boolean isDigit(char ch)
	    {
	        String str = String.valueOf(ch);
	        return isDigit(str);
	    }

	    public static boolean isDigit(String str)
	    {
	        int strlen = str.length();
	        boolean f = true;
	        for(int i = 0; i < strlen; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= '0' && ch <= '9')
	            {
	                continue;
	            }
	            f = false;
	            break;
	        }

	       
	        return f;
	    }

	    public static byte[] copy(byte src[], byte dst[], int src_offset, int dst_offset, int src_nbytes)
	    {
	        for(int i = 0; i < src_nbytes; i++)
	        {
	            dst[dst_offset + i] = src[src_offset + i];
	        }

	        return src;
	    }

	    public static boolean isAlpha(char ch)
	    {
	        String str = String.valueOf(ch);
	        return isAlpha(str);
	    }
	    
	    public static boolean isAlpha(String str)
	    {
	        int strlen = str.length();
	        boolean f = true;
	        for(int i = 0; i < strlen; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
	            {
	                continue;
	            }
	            f = false;
	            break;
	        }

	        if(debug > 1)
	        {
	           System.out.println("peUtil.isAlpha(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", f = ");
	           System.out.println(f);
	        }
	        return f;
	    }

	    public static boolean isAlphaNumeric(char ch)
	    {
	        String str = String.valueOf(ch);
	        return isAlphaNumeric(str);
	    }

	    public static boolean isAlphaNumeric(String str)
	    {
	        int strlen = str.length();
	        boolean f = true;
	        for(int i = 0; i < strlen; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9')
	            {
	                continue;
	            }
	            f = false;
	            break;
	        }

	        if(debug > 1)
	        {
	           System.out.println("peUtil.isAlphaNumeric(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", f = ");
	           System.out.println(f);
	        }
	        return f;
	    }

	    public static boolean isAllowedChar(char ch, char allowedChars[])
	    {
	        String str = String.valueOf(ch);
	        return isAllowedChar(str, allowedChars);
	    }

	    public static boolean isAllowedChar(String str, char allowedChars[])
	    {
	        int strlen = str.length();
	        boolean f = false;
	        for(int i = 0; i < strlen; i++)
	        {
	            char ch = str.charAt(i);
	            for(int j = 0; j < allowedChars.length; j++)
	            {
	                if(ch == allowedChars[j])
	                {
	                    f = true;
	                }
	            }

	        }

	        if(debug > 1)
	        {
	           System.out.println("peUtil.isAllowedChar(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", allowedChars = ");
	           System.out.println(allowedChars.toString());
	           System.out.println(", f = ");
	           System.out.println(f);
	        }
	        return f;
	    }

	    public static boolean isUnprintable(char ch)
	    {
	        String str = String.valueOf(ch);
	        return isUnprintable(str);
	    }

	    public static boolean isUnprintable(String str)
	    {
	        int strlen = str.length();
	        boolean f = false;
	        for(int i = 0; i < strlen; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= ' ' && ch <= '~')
	            {
	                continue;
	            }
	            f = true;
	            break;
	        }

	        if(debug > 1)
	        {
	           System.out.println("peUtil.isUnprintable(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", f = ");
	           System.out.println(f);
	        }
	        return f;
	    }

	    public static String unprintable2br(String str)
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < str.length(); i++)
	        {
	            char ch = str.charAt(i);
	            if(isUnprintable(ch))
	            {
	                sb.append("<br>");
	                sb.append(ch);
	            } else
	            {
	                sb.append(ch);
	            }
	        }

	        if(debug > 1)
	        {
	           System.out.println("peUtil.unprintable2br(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", sb = ");
	           System.out.println(sb.toString());
	        }
	        return sb.toString();
	    }

	    public static double obj2double(Object obj)
	    {
	        return atod(obj2str(obj));
	    }

	    public static long obj2long(Object obj)
	    {
	        return atol(obj2str(obj));
	    }

	    public static boolean obj2boolean(Object obj)
	    {
	        Boolean bool = new Boolean(obj2str(obj));
	        return bool.booleanValue();
	    }

	    public static int atoi(String str)
	    {
	        int num = 0;
	        if(!isNullString(str))
	        {
	            try
	            {
	                num = Integer.parseInt(str);
	            }
	            catch(NumberFormatException e)
	            {
	                System.out.println(e);
	            }
	        }
	        return num;
	    }

	    public static float atof(String str)
	    {
	        float num = 0.0F;
	        if(!isNullString(str))
	        {
	            try
	            {
	                num = Float.parseFloat(str);
	            }
	            catch(NumberFormatException e)
	            {
	            	System.out.println(e);
	            }
	        }
	        return num;
	    }

	    public static long atol(String str)
	    {
	        long num = 0L;
	        if(!isNullString(str))
	        {
	            try
	            {
	                num = Long.parseLong(str);
	            }
	            catch(NumberFormatException e)
	            {
	            	System.out.println(e);
	            }
	        }
	        return num;
	    }

	    public static double atod(String str)
	    {
	        double num = 0.0D;
	        if(!isNullString(str))
	        {
	            try
	            {
	                num = Double.parseDouble(str);
	            }
	            catch(NumberFormatException e)
	            {
	            	System.out.println(e);
	            }
	        }
	        return num;
	    }

	    public static boolean atob(String str)
	    {
	        boolean bool = Boolean.valueOf(str).booleanValue();
	        return bool;
	    }

	    public static String getString(LinkedHashMap ht, String key)
	    {
	        return obj2str(ht.get(key));
	    }

	    public static long today()
	    {
	        Calendar cal = Calendar.getInstance();
	        long utn = cal.getTimeInMillis() / 1000L;
	        int hr = cal.get(11);
	        int min = cal.get(12);
	        int sec = cal.get(13);
	        utn -= sec;
	        utn -= min * 60;
	        utn -= hr * 3600;
	        return utn;
	    }

	    public static long utn()
	    {
	        long utn = utn_in_ms() / 1000L;
	        return utn;
	    }

	    public static long utn_in_ms()
	    {
	        long utn = System.currentTimeMillis();
	        utn = (new Date()).getTime();
	        return utn;
	    }

	    public static String getCurrentTime()
	    {
	        StringBuffer sb = new StringBuffer();
	        Calendar cal = Calendar.getInstance();
	        int hr = cal.get(10);
	        int min = cal.get(12);
	        int sec = cal.get(13);
	        if(hr < 10)
	        {
	            sb.append("0");
	        }
	        sb.append(hr);
	        sb.append(":");
	        if(min < 10)
	        {
	            sb.append("0");
	        }
	        sb.append(min);
	        sb.append(":");
	        if(sec < 10)
	        {
	            sb.append("0");
	        }
	        sb.append(sec);
	        sb.append(" ");
	        sb.append(getCurrentAMPM(cal));
	        return sb.toString();
	    }
	    public static String getCurrentAMPM()
	    {
	        Calendar cal = Calendar.getInstance();
	        return getCurrentAMPM(cal);
	    }

	    public static String getCurrentAMPM(long utn)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(utn * 1000L);
	        return getCurrentAMPM(cal);
	    }

	    public static String getCurrentAMPM(Calendar cal)
	    {
	        int hr = cal.get(11);
	        if(hr < 12)
	        {
	            return "AM";
	        } else
	        {
	            return "PM";
	        }
	    }
	    
	    public static LinkedHashMap swap(LinkedHashMap src)
	    {
	        LinkedHashMap dst = new LinkedHashMap();
	        Set keys = src.keySet();
	        Object key;
	        Object val;
	        for(Iterator iter = keys.iterator(); iter.hasNext(); dst.put(val, key))
	        {
	            key = iter.next();
	            val = src.get(key);
	        }

	        return dst;
	    }
	    public static LinkedHashMap prefixKeys(LinkedHashMap src, String prefix)
	    {
	        LinkedHashMap dst = new LinkedHashMap();
	        Set keys = src.keySet();
	        Object key;
	        Object val;
	        for(Iterator iter = keys.iterator(); iter.hasNext(); dst.put(prefix + key, val))
	        {
	            key = iter.next();
	            val = src.get(key);
	        }

	        return dst;
	    }
	    
	    public static LinkedHashMap removeNulls(LinkedHashMap src)
	    {
	        LinkedHashMap dst = new LinkedHashMap();
	        Set keys = src.keySet();
	        for(Iterator iter = keys.iterator(); iter.hasNext();)
	        {
	            String key = obj2str(iter.next());
	            String val = obj2str(src.get(key));
	            if(!isNullString(val))
	            {
	                dst.put(key, val);
	            }
	        }

	        return dst;
	    }
	    public static LinkedHashMap map2hash(Map map)
	    {
	        LinkedHashMap ht = new LinkedHashMap();
	        ht.putAll(map);
	        return ht;
	    }

	 

	    public static String numeric(String str)
	    {
	        StringBuffer sb = new StringBuffer();
	        int len = str.length();
	        if(len == 0)
	        {
	            return "";
	        }
	        for(int i = 0; i < len; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= '0' && ch <= '9')
	            {
	                sb.append(ch);
	            } else
	            if(ch == '.' || ch == '-')
	            {
	                sb.append(ch);
	            }
	        }

	        return sb.toString();
	    }

	    public static String array2string(int array[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < array.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(", ");
	            }
	            sb.append(array[i]);
	        }

	        return sb.toString();
	    }

	    public static String array2string(float array[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < array.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(", ");
	            }
	            sb.append(array[i]);
	        }

	        return sb.toString();
	    }

	    public static String array2string(double array[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < array.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(", ");
	            }
	            sb.append(array[i]);
	        }

	        return sb.toString();
	    }

	    public static String array2string(boolean array[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < array.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(", ");
	            }
	            sb.append(array[i]);
	        }

	        return sb.toString();
	    }

	    public static String array2string(String array[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < array.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(", ");
	            }
	            sb.append(array[i]);
	        }

	        return sb.toString();
	    }

	    public static String args2string(String args[])
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < args.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append(" ");
	            }
	            sb.append(args[i]);
	        }

	        return sb.toString();
	    }

	    public static String extractDigits(String str)
	    {
	        StringBuffer sb = new StringBuffer();
	        int len = str.length();
	        for(int i = 0; i < len; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch >= '0' && ch <= '9')
	            {
	                sb.append(ch);
	            }
	        }

	        return sb.toString();
	    }
	    public static String UpperCaseFirstLetter(String str, boolean all)
	    {
	        StringBuffer sb = new StringBuffer();
	        boolean uc = true;
	        int len = str.length();
	        for(int i = 0; i < len; i++)
	        {
	            char ch = str.charAt(i);
	            if(uc)
	            {
	                uc = false;
	                sb.append(Character.toUpperCase(ch));
	                if(all)
	                {
	                    continue;
	                }
	                sb.append(str.substring(i + 1));
	                break;
	            }
	            if(ch == ' ')
	            {
	                uc = true;
	                sb.append(ch);
	            } else
	            {
	                sb.append(ch);
	            }
	        }

	        if(debug > 0)
	        {
	            System.out.println("peUtil.UpperCaseFirstLetter(): ");
	            System.out.println("str = ");
	            System.out.println(str);
	            System.out.println(", all = ");
	            System.out.println(all);
	            System.out.println(", sb = ");
	            System.out.println(sb.toString());
	        }
	        return sb.toString();
	    }
	    public static String vector2str(Vector v)
	    {
	        String delim = ", ";
	        return vector2str(v, delim);
	    }

	    public static String vector2str(Vector v, String delim)
	    {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < v.size(); i++)
	        {
	            sb.append(obj2str(v.elementAt(i)));
	            sb.append(delim);
	        }

	        if(sb.length() > 0)
	        {
	            sb.setLength(sb.length() - delim.length());
	        }
	        return sb.toString();
	    }
	    public static int getMaxKeyLength(LinkedHashMap ht)
	    {
	        Set keys = ht.keySet();
	        Iterator iter = keys.iterator();
	        int len = 0;
	        while(iter.hasNext()) 
	        {
	            String key = obj2str(iter.next());
	            int klen = key.length();
	            if(klen > len)
	            {
	                len = klen;
	            }
	        }
	        iter = keys.iterator();
	        return len;
	    }
	    public static LinkedHashMap copy(LinkedHashMap src)
	    {
	        LinkedHashMap dst = new LinkedHashMap();
	        dst.putAll(src);
	        return dst;
	    }
	    public static Vector copy(Vector src)
	    {
	        Vector dst = new Vector();
	        for(int i = 0; i < src.size(); i++)
	        {
	            dst.add(src.elementAt(i));
	        }

	        return dst;
	    }

	    public static Vector splitOnDot(String str)
	    {
	        return splitOnChar(str, '.');
	    }

	    public static Vector splitOnNonAlphaNumeric(String str)
	    {
	        String regex = "\\b";
	        regex = "[^a-zA-Z0-9-]";
	        String arr[] = str.split(regex);
	        return array2vector(arr);
	    }

	    public static String dec2hex(String str)
	    {
	        int val = Integer.parseInt(str);
	        return Integer.toHexString(val);
	    }

	    public static String hex2dec(String str)
	    {
	        int val = Integer.parseInt(str, 16);
	        return String.valueOf(val);
	    }

	    public static String char2dec(char ch)
	    {
	        int val = ch;
	        return String.valueOf(val);
	    }

	    public static String dec2char(String str)
	    {
	        int val = atoi(str);
	        Character ch = new Character((char)val);
	        return ch.toString();
	    }

	    public static Vector array2vector(Object array[])
	    {
	        Vector v = new Vector();
	        if(array != null)
	        {
	            for(int i = 0; i < array.length; i++)
	            {
	                v.add(array[i]);
	            }

	        }
	        return v;
	    }

	    public static String[] vector2array(Vector v)
	    {
	        String arr[] = new String[v.size()];
	        for(int i = 0; i < v.size(); i++)
	        {
	            arr[i] = v.elementAt(i).toString();
	        }

	        return arr;
	    }

	    public static LinkedHashMap vector2hash(Vector v)
	    {
	        LinkedHashMap ht = new LinkedHashMap();
	        for(int i = 0; i < v.size(); i++)
	        {
	            String str = obj2str(v.elementAt(i));
	            ht.put(str, str);
	        }

	        return ht;
	    }

	    public static LinkedHashMap vector2hash(Vector v, String val)
	    {
	        LinkedHashMap ht = new LinkedHashMap();
	        for(int i = 0; i < v.size(); i++)
	        {
	            String str = obj2str(v.elementAt(i));
	            ht.put(str, val);
	        }

	        return ht;
	    }

	    public static HashMap vector2hashmap(Vector v)
	    {
	        HashMap ht = new HashMap();
	        for(int i = 0; i < v.size(); i++)
	        {
	            String str = obj2str(v.elementAt(i));
	            ht.put(str, str);
	        }

	        return ht;
	    }

	    public static HashMap vector2hashmap(Vector v, String val)
	    {
	        HashMap ht = new HashMap();
	        for(int i = 0; i < v.size(); i++)
	        {
	            String str = obj2str(v.elementAt(i));
	            ht.put(str, val);
	        }

	        return ht;
	    }

	    public static Vector hash2vector(LinkedHashMap ht)
	    {
	        Vector v = new Vector();
	        Set keys = ht.keySet();
	        for(Iterator iter = keys.iterator(); iter.hasNext(); v.add(iter.next().toString())) { }
	        return v;
	    }

	    public static Vector set2vector(Set s)
	    {
	        Vector v = new Vector();
	        for(Iterator iter = s.iterator(); iter.hasNext(); v.add(iter.next().toString())) { }
	        return v;
	    }
	
	    public static Vector splitOnChar(String str, char delim)
	    {
	        Vector v = new Vector();
	        int len = str.length();
	        String s = "";
	        for(int i = 0; i < len; i++)
	        {
	            char ch = str.charAt(i);
	            if(ch == delim)
	            {
	                v.add(s);
	                s = "";
	            } else
	            {
	                s = s + ch;
	            }
	        }

	        v.add(s);
	        if(debug > 2)
	        {
	            System.out.println("peUtil.splitOnChar(): ");
	            System.out.println("str = ");
	            System.out.println(str);
	            System.out.println(", delim = ");
	            System.out.println(delim);
	            System.out.println(" (");
	            System.out.println(String.valueOf(delim));
	            System.out.println("), v = ");
	            System.out.println(v.toString());
	        }
	        return v;
	    }
	    public static Vector splitOnComma(String str)
	    {
	        Vector v1 = split(str, ", ");
	        Vector v2 = split(str, ",");
	        Vector v;
	        if(v1.size() > 1)
	        {
	            v = v1;
	        } else
	        {
	            v = v2;
	        }
	        if(debug > 0)
	        {
	           System.out.println("peUtil.splitOnComma(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", v = ");
	           System.out.println(v.toString());
	        }
	        return v;
	    }
	    public static Vector split(String str, String delim)
	    {
	        Vector v = new Vector();
	        if(isNullString(str))
	        {
	            return v;
	        }
	        if(delim.length() == 1)
	        {
	            return splitOnChar(str, delim.charAt(0));
	        }
	        String tokens[] = str.split(delim, -1);
	        for(int i = 0; i < tokens.length; i++)
	        {
	            if(debug > 1)
	            {
	                System.out.println("peUtil.split(): ");
	                System.out.println(tokens[i]);
	            }
	            v.add(tokens[i]);
	        }

	        if(debug > 2)
	        {
	           System.out.println("peUtil.split(): ");
	           System.out.println("str = ");
	           System.out.println(str);
	           System.out.println(", delim = ");
	           System.out.println(delim);
	           System.out.println(" (");
	           System.out.println(String.valueOf(delim));
	           System.out.println("), v = ");
	           System.out.println(v.toString());
	        }
	        return v;
	    }
	    public static String genSmallUniqueID(int maxlen)
	    {
	        StringBuffer sb = new StringBuffer();
	        String str = genUniqueID();
	        int strlen = str.length();
	        for(int i = 0; i < maxlen; i++)
	        {
	            double rand = Math.random();
	            int indx = (int)(rand * (double)strlen);
	            if(debug > 2)
	            {
	                System.out.println("peUtil.genSmallUniqueID(): ");
	                System.out.println("strlen = ");
	                System.out.println(strlen);
	                System.out.println(", indx = ");
	                System.out.println(indx);
	            }
	            char ch = str.charAt(indx);
	            if(ch == '0' || ch == 'O' || ch == 'o' || ch == '1' || ch == 'L' || ch == 'l')
	            {
	                i--;
	            } else
	            if(isAlphaNumeric(ch))
	            {
	                sb.append(ch);
	            } else
	            {
	                i--;
	            }
	        }

	        String str2 = sb.toString().toUpperCase();
	        if(debug > 2)
	        {
	            System.out.println("peUtil.genSmallUniqueID(): ");
	            System.out.println(str + " -> " + str2);
	        }
	        return str2;
	    }

	    public static String genNumericUniqueID()
	    {
	        return genNumericUniqueID(9);
	    }

	    public static String genNumericUniqueID(int maxlen)
	    {
	        StringBuffer sb = new StringBuffer();
	        String str = genUniqueID();
	        int strlen = str.length();
	        for(int i = 0; i < maxlen; i++)
	        {
	            double rand = Math.random();
	            int indx = (int)(rand * (double)strlen);
	            if(debug > 2)
	            {
	                System.out.println("peUtil.genNumericUniqueID(): ");
	                System.out.println("strlen = ");
	                System.out.println(strlen);
	                System.out.println(", indx = ");
	                System.out.println(indx);
	            }
	            char ch = str.charAt(indx);
	            if(ch == '0' || ch == 'O' || ch == 'o' || ch == '1' || ch == 'L' || ch == 'l')
	            {
	                i--;
	            } else
	            if(isDigit(ch))
	            {
	                sb.append(ch);
	            } else
	            {
	                i--;
	            }
	        }

	        String str2 = sb.toString().toUpperCase();
	        if(debug > 2)
	        {
	            System.out.println("peUtil.genNumericUniqueID(): ");
	            System.out.println(str + " -> " + str2);
	        }
	        return str2;
	    }

	    public static String genGUID()
	    {
	        StringBuffer sb = new StringBuffer();
	        String str;
	        for(str = ""; str.length() < 32; str = str + genUniqueID().toLowerCase()) { }
	        sb.append("{");
	        sb.append(str.substring(0, 8));
	        sb.append("-");
	        sb.append(str.substring(8, 4));
	        sb.append("-");
	        sb.append(str.substring(12, 4));
	        sb.append("-");
	        sb.append(str.substring(16, 4));
	        sb.append("-");
	        sb.append(str.substring(20, 12));
	        sb.append("}");
	        return sb.toString();
	    }
	    
	    public static boolean isLeapYear(int yr)
	    {
	        return yr % 4 == 0;
	    }

	    public static int getNumDaysInMonth(int mo, int yr)
	    {
	        int ndays = 31;
	        if(mo == 2)
	        {
	            ndays = isLeapYear(yr) ? 29 : 28;
	        } else
	        if(mo == 4 || mo == 6 || mo == 9 || mo == 11)
	        {
	            ndays = 30;
	        }
	        return ndays;
	    }
	    public static String getDayOfWeek()
	    {
	        Calendar cal = Calendar.getInstance();
	        String type = "";
	        return getDayOfWeek(cal, type);
	    }

	    public static String getDayOfWeek(Calendar cal)
	    {
	        String type = "";
	        return getDayOfWeek(cal, type);
	    }

	    public static String getDayOfWeek(String type)
	    {
	        Calendar cal = Calendar.getInstance();
	        return getDayOfWeek(cal, type);
	    }

	    public static String getDayOfWeek(Calendar cal, String type)
	    {
	        boolean abbrev = false;
	        if(!isNullString(type) && type.equals("abbrev"))
	        {
	            abbrev = true;
	        }
	        int dy = cal.get(7);
	        if(dy == 1)
	        {
	            return abbrev ? "Sun" : "Sunday";
	        }
	        if(dy == 2)
	        {
	            return abbrev ? "Mon" : "Monday";
	        }
	        if(dy == 3)
	        {
	            return abbrev ? "Tue" : "Tuesday";
	        }
	        if(dy == 4)
	        {
	            return abbrev ? "Wed" : "Wednesday";
	        }
	        if(dy == 5)
	        {
	            return abbrev ? "Thu" : "Thursday";
	        }
	        if(dy == 6)
	        {
	            return abbrev ? "Fri" : "Friday";
	        }
	        if(dy == 7)
	        {
	            return abbrev ? "Sat" : "Satday";
	        } else
	        {
	            return "UNKNOWN";
	        }
	    }

	    public static String getDayOfWeek(long utn)
	    {
	        String type = "";
	        return getDayOfWeek(utn, type);
	    }

	    public static String getDayOfWeek(long utn, String type)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(utn * 1000L);
	        return getDayOfWeek(cal, type);
	    }
	    
	   

}
