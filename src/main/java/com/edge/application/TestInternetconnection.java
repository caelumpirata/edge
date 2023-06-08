//package com.edge.application;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//
//public class TestInternetconnection {
//   public static void main(String[] args) {
//	   
//	   boolean status = false;
//	   
//	   status=check_status();
//	   System.out.println("Internet Status...."+status);
//      
//   }
//   
//   public static boolean check_status()
//   {
//	   boolean status=false;
//	   try {
//	         URL url = new URL("http://www.google.com");
//	         URLConnection connection = url.openConnection();
//	         connection.connect();
//	         System.out.println("Internet is connected");
//	         status=true;
//	      } catch (MalformedURLException e) {
//	         System.out.println("Internet is not connected");
//	         status=false;
//	      } catch (IOException e) {
//	         System.out.println("Internet is not connected");
//	         status=false;
//	      }
//	   
//	   return status;
//   }
//}