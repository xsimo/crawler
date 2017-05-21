package com.xsimo.crawler;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;

/**
 * Simple java bean for href matches
 * @author Administrator
 *
 */
public class Hit {
	public int offset;
	public String address;
	public HttpHost target;
	public String requestedResource;
	public String filename;
	public String directory;
	public String toString(){
		String s = "address : "+address+"\n";
		s+="target : "+target.getHostName()+"\n";
		s+="requestedResource : "+requestedResource+"\n";
		s+="filename : "+filename+"\n";
		s+="directory : "+directory+"\n";
		return s;
	}
	public HttpHost getTarget(){
		return target;
	}
	public String getRequestedResource(){
		return requestedResource;
	}
	public String getFilename(){
		return filename;
	}
	public String getDirectory(){
		return directory;
	}
	public Hit(String address,int offset){
		this.address = address;
		this.offset = offset;
	}
	public Hit(String address){
		this.address = address;
	}
	public Hit(){	
	}
	public void compute(String currentDir, HttpHost host){
		this.setRequestedResource(currentDir);
		this.setTarget(host);
		this.setFilename();
		this.setDirectory(currentDir,host);
	}
	public void setTarget(HttpHost target){
		/**
		 * @TODO traiter aussi https
		 * 
		 * */ 
		if(this.address.startsWith("http://")){
			String a = this.address.substring(7);
			int endOfHost = a.indexOf("/");
			if(endOfHost==-1){
				HttpHost host = new HttpHost(a);
				this.target = host;
				return;
			}else{
				String h = a.substring(0,endOfHost);
				HttpHost host = new HttpHost(h);
				this.target = host;
				return;
			}
		}
		if(this.address.startsWith("ftp://")){
			String a = this.address.substring(6);
			int endOfHost = a.indexOf("/");
			if(endOfHost==-1){
				HttpHost host = new HttpHost(a);
				this.target = host;
				return;
			}else{
				String h = a.substring(0,endOfHost);
				HttpHost host = new HttpHost(h);
				this.target = host;
				return;
			}
		}
		this.target = target;
	}
	public void setDirectory(String currentDir,HttpHost host){
		File f;
		try {
			f = new File ( Hit.class.getResource("/bootstrap").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			this.directory = currentDir;
			return;
		}
		String baseDir = f.getParent()+"/";
		String dir = this.requestedResource;
		if(this.filename!=null){
			dir = this.requestedResource.substring(0,this.requestedResource.lastIndexOf(this.filename));
		}
		this.directory = baseDir+this.target.getHostName()+dir;
	}
	public void setFilename(){
		if (this.requestedResource.endsWith("/")){
			this.filename = null;
			return;
		}else{
			this.filename = this.requestedResource.substring(this.requestedResource.lastIndexOf("/")+1);
		}
	}
	public void setRequestedResource(String currentDir){
		assert(currentDir.startsWith("/"));
		assert(currentDir.endsWith("/"));
		if(this.address.startsWith("http://")){
			String a = this.address.substring(7);
			int endOfHost = a.indexOf("/");
			if(endOfHost==-1){
				this.requestedResource = "/";
				return;
			}else{
				String r = a.substring(endOfHost);
				this.requestedResource = r;
				return;
			}
		}
		if(this.address.startsWith("ftp://")){
			String a = this.address.substring(6);
			int endOfHost = a.indexOf("/");
			if(endOfHost==-1){
				this.requestedResource = "/";
				return;
			}else{
				String r = a.substring(endOfHost);
				this.requestedResource = r;
				return;
			}
		}
		
		if (this.address.startsWith("/")){
			this.requestedResource = this.address;
			return;
		}
		else{
			File f;
			try {
				f = new File ( Hit.class.getResource("/bootstrap").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
				this.requestedResource = this.address;
				return;
			}
			String baseDir = f.getParent()+"/";
			String currentPageDirectory = currentDir.substring(baseDir.length()-1);
			//extraction du hostname
			assert(currentPageDirectory.startsWith("/"));
			currentPageDirectory = currentPageDirectory.substring(1);
			currentPageDirectory = currentPageDirectory.substring(currentPageDirectory.indexOf("/"));
			assert(currentPageDirectory.startsWith("/"));
			String relevant = address;
			if(relevant.equals("..")){
				String almostCurrent = currentPageDirectory.substring(0,currentPageDirectory.length()-1);
				int ls = almostCurrent.lastIndexOf("/");
				assert(!almostCurrent.equals(""));
				this.requestedResource = almostCurrent.substring(0,ls);
				return;
			}
			if(relevant.equals(".")){
				this.requestedResource = currentPageDirectory;
				return;
			}
			if(!relevant.contains("/")){
				this.requestedResource = currentPageDirectory+relevant;
				return;
			}
			String computedPath = currentPageDirectory;
			while(relevant.contains("/")){
				String proceed = relevant.substring(0,relevant.indexOf("/"));
				relevant = relevant.substring(relevant.indexOf("/")+1);
				if(proceed.equals("..")){
					String almostCurrent = computedPath.substring(0,computedPath.length()-1);
					int ls = almostCurrent.lastIndexOf("/");
					assert(!almostCurrent.equals(""));
					computedPath = almostCurrent.substring(0,ls+1);
				}else if(proceed.equals(".")){
					//nothing to do
				}else{
					computedPath += proceed+"/";
				}
			}
			if(relevant.equals("") || relevant.equals(".")){
				this.requestedResource = computedPath;
				return;
			}else if (relevant.equals("..")){
				String almostCurrent = computedPath.substring(0,computedPath.length()-1);
				int ls = almostCurrent.lastIndexOf("/");
				assert(!almostCurrent.equals(""));
				computedPath = almostCurrent.substring(0,ls+1);
				this.requestedResource = computedPath;
				return;
			}else{
				this.requestedResource = computedPath+relevant;
				return;
			}

		}
	}
}
