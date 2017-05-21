package com.xsimo.crawler;

import org.apache.http.*;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class MyWebGet {
	public static HttpResponse getPage(HttpHost target, String requestedResource)throws Exception{
		// general setup
        SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        supportedSchemes.register(new Scheme("http", 
                PlainSocketFactory.getSocketFactory(), 80));

        // prepare parameters
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(params, true);

        ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params, 
                supportedSchemes);
        DefaultHttpClient httpclient = new DefaultHttpClient(connMgr, params);

        HttpGet req = new HttpGet(requestedResource);
        System.out.println("executing request to " + target);

        HttpResponse rsp = httpclient.execute(target, req);
        int code = rsp.getStatusLine().getStatusCode();
        System.out.println("status code == "+code);
        if(code>=400){
        	throw new HttpResponseException(rsp.getStatusLine().getStatusCode(),
        			rsp.getStatusLine().getReasonPhrase());
        }else if (code >299 && code <400){
        	System.out.println("receiving a 3xx response code : assuming page was moved");
        	HeaderElementIterator it = new BasicHeaderElementIterator(rsp.headerIterator("Location"));
        	if (it. hasNext()){
        		it.next();
        		String moved = it.nextElement().getValue();
        		String whithoutHTTP_s = moved.substring(moved.indexOf(':')).substring(2);
        		String host = "";
        		String movedResource = "";
        		if(whithoutHTTP_s.contains("/")){
        			host = whithoutHTTP_s.substring(0,whithoutHTTP_s.indexOf('/'));
        			movedResource = whithoutHTTP_s.substring(whithoutHTTP_s.indexOf('/'));
        		}else{
        			host = whithoutHTTP_s;
        		}
        		return getPage(new HttpHost(host),movedResource);
        	}else{
        		throw new HttpResponseException(rsp.getStatusLine().getStatusCode(),
            			rsp.getStatusLine().getReasonPhrase());
        	}
        }else{
        	return rsp;
        }
	}
	public static void main(String[] args){
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 
			    HttpStatus.SC_OK, "OK");
			response.addHeader("Set-Cookie", 
			    "c1=a; path=/; domain=localhost");
			response.addHeader("Set-Cookie", 
			    "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
			System.out.println("LA VALEUR EST "+response.getHeaders("Set-Cookie")[0].getValue());
			HeaderElementIterator it = new BasicHeaderElementIterator(
			    response.headerIterator("Set-Cookie"));

			while (it.hasNext()) {
			    HeaderElement elem = it.nextElement(); 
			    System.out.println("MAIN : "+elem.getName() + " === " + elem.getValue());
			    NameValuePair[] params = elem.getParameters();
			    for (int i = 0; i < params.length; i++) {
			        System.out.println(" " + params[i]);
			    }
			}

	}
}
