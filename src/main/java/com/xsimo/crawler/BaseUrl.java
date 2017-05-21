package com.xsimo.crawler;
/**
 * Simple URL java Bean
 * @author Administrator
 *
 */
public class BaseUrl {
	String domainName;
	String relativePath;
	public static String getDefaultFileName(String contentType){
		//defaultBinding to extends ... 
		if(contentType.equals("text/xml")){
			return "index.xml";
		}
		if(contentType.equals("text/html")){
			return "index.html";
		}
		if(contentType.equals("image/jpg")){
			return "index.jpg";
		}
		return "index.noextension";
	}
}
