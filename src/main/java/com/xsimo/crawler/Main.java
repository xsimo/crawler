package com.xsimo.crawler;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
/**
 * This class contains the main method along with small functions.
 * When run against an URL, it will write index to disc and also try to save every
 * link contained in the index.
 * TODO make it more like "wget" but the JAVA way... see http://www.xsimo.com/mantis for specific choices of preferred modes
 * @author Simon Arame
 *
 */
public class Main {
	
	private static int MAXDEPTH = 0;
	
	
	/**
	 * This recursive method is where the action lies.
	 *  
	 * @param currentDir the currentDirectory, to determine where to write the file
	 * @param target org.apache.http.HttpHost, the current host where to fetch the requestedResource
	 * @param requestedResource a relative path from the root directory (/) of the target
	 * @param currentDepth return null if MAXDEPTH is reached
	 * @param filename the name of the file to write, if null file will be written to index.html
	 */
	public static void fonctionRecursive(Hit TheHit,int currentDepth){
		System.out.println(TheHit.toString());
		String downloaded = null;
		String contentType = null;
		HttpResponse rsp = null;
		try{
			try{
				rsp = MyWebGet.getPage(TheHit.getTarget(),TheHit.getRequestedResource());
			}catch(HttpResponseException e){
				return;
			}
			contentType = determineContentType(rsp);
			
			if(TheHit.getFilename()==null){
				TheHit.filename = BaseUrl.getDefaultFileName(contentType);
			}
			downloaded = fileWriter(rsp,TheHit.getDirectory()+TheHit.getFilename());

		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		if(contentType.contains("text/html")&&currentDepth<MAXDEPTH){
			//possible temporary fall down : return;
			
			ArrayList<Hit> hits = seekRef(downloaded);
			for(Hit hit : hits){
				System.out.println("hit.address : "+hit.address);
				hit.compute(TheHit.getDirectory(), TheHit.getTarget());
				
				fonctionRecursive(hit,currentDepth++);
			}
		}else{
			System.out.println("content type is "+contentType);
		}
	}
	
	/**
	 * This is the main method.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
	//TODO interactively choosable domain
		System.out.print("Enter base url :  http://");
		String http = Keyboard.readString();
		String hostname="";
		Hit firstHit = new Hit("http://"+http);
		File f = new File ( Hit.class.getResource("/bootstrap").toURI());
		String dir = "";
		if(http.contains("/")){
			hostname = http.substring(0,http.indexOf("/"));
			dir = http.substring(hostname.length());
			assert(dir.startsWith("/"));
			if(dir.equals("/")){
				
			}else{
				int ls = dir.lastIndexOf("/");
				if(ls!=0){
					dir = dir.substring(0,dir.lastIndexOf("/")+1);
				}else{
					dir="/";
				}
			}
		}else{
			hostname = http;
			dir="/";
		}
		HttpHost host = new HttpHost(hostname);
		MAXDEPTH = Keyboard.readInt();
		firstHit.compute(f.getParent()+dir, host);
		System.out.println("firstHit\n"+firstHit.toString()+"------");
		fonctionRecursive(firstHit,0);
	}
	
	
	
	
	/**
	 * Finds all 'href="..."' occurences in the given parameter
     * @param downloaded the string to analyse
     * @return an ArrayList of Hits
     * @author Simon Arame
     * @todo double check the regex
     */
	public static ArrayList<Hit> seekRef(String downloaded) {
		String regex = "(?i)(href) ?= ?\\\"(.+?)\\\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(downloaded);
		ArrayList<Hit> hits = new ArrayList<Hit>();
		while(m.find()) {
			String adresse = m.group();
			Hit hit = new Hit();
			hit.address = adresse.substring(adresse.indexOf("\"")+1,adresse.length()-1);
			hit.offset = m.start();
			hits.add(hit);
		}
		return hits;
	}

	/**
	 * Writes entity of given HttpResponse parameter to the file with given abolute path parameter 
	 * @return the string written to absolutePath parameter file
	 * @param rsp the unread HttpResponse to persist 
	 * @param absolutePath the complete filename along with its path
	 * @return the HttpResponse entity as a string
	 * @throws IllegalStateException  if this HttpResponse entity is not repeatable and the stream
     *  has already been obtained previously
	 * @throws IOException if file does not exists or if the stream could not be created or if any other I/O error occurs
	 */
	public static String fileWriter(HttpResponse rsp,String absolutePath) throws IllegalStateException, IOException {
		InputStream is = null;
		BufferedReader br = null;
		String entity = "";
		FileWriter fw = null;
		try{
			File file = new File ( absolutePath );
			File parent = new File ( file.getParent() );
			parent.mkdirs();
			fw = new FileWriter(file);
			is = rsp.getEntity().getContent();
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line=br.readLine())!=null){
				entity+=line;
				fw.write(line);
			}
			return entity;
		}finally{
			try{is.close();}catch(Exception e){;}
			try{br.close();}catch(Exception e){;}
			try{fw.close();}catch(Exception e){;}
		}
	}
	/**
	 * Simply fetches the HttpResponse contentType
	 * @param rsp
	 * @return
	 */
	public static String determineContentType(HttpResponse rsp) {
		Header h = rsp.getEntity().getContentType();
		return h.getValue();
	}
	
}
