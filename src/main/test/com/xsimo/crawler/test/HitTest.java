package com.xsimo.crawler.test;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpHost;
import static org.junit.Assert.* ;
import org.junit.*;

import com.xsimo.crawler.Hit;
public class HitTest{
	class HitTester{
		public HitTester(String expected,Hit hit){
			this.hit = hit;
			this.expected = expected;
		}
		public Hit hit;
		public String expected;
	}
	@Test
	public void test_getFilename() throws URISyntaxException{
		ArrayList<HitTester> list = new ArrayList<HitTester>();
		//the external http reference case
		list.add(new HitTester(null,new Hit("http://www.example.com/")));
		list.add(new HitTester("filename.html",new Hit("http://www.example.com/dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("http://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("http://www.example.com/dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("http://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("http://www.example.com/filename.html")));
		list.add(new HitTester("nofilename",new Hit("http://www.example.com/nofilename")));
		list.add(new HitTester(null,new Hit("http://www.example.com/dir/")));
		list.add(new HitTester(null,new Hit("http://www.example.com/dir1/dir2/")));
		list.add(new HitTester(null,new Hit("http://www.example.com")));
		
		//the external ftp reference case
		list.add(new HitTester(null,new Hit("ftp://www.example.com/")));
		list.add(new HitTester("filename.html",new Hit("ftp://www.example.com/dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("ftp://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("ftp://www.example.com/dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("ftp://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("ftp://www.example.com/filename.html")));
		list.add(new HitTester("nofilename",new Hit("ftp://www.example.com/nofilename")));
		list.add(new HitTester(null,new Hit("ftp://www.example.com/dir/")));
		list.add(new HitTester(null,new Hit("ftp://www.example.com/dir1/dir2/")));
		list.add(new HitTester(null,new Hit("ftp://www.example.com")));
		
		//the absolute reference case
		list.add(new HitTester(null,new Hit("/")));
		list.add(new HitTester("filename.html",new Hit("/dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("/dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("/dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("/dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("/filename.html")));
		list.add(new HitTester("nofilename",new Hit("/nofilename")));
		list.add(new HitTester(null,new Hit("/dir/")));
		list.add(new HitTester(null,new Hit("/dir1/dir2/")));
		
		//the simple relative path reference case
		list.add(new HitTester("filename.html",new Hit("dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("filename.html")));
		list.add(new HitTester("nofilename",new Hit("nofilename")));
		list.add(new HitTester(null,new Hit("dir/")));
		list.add(new HitTester(null,new Hit("dir1/dir2/")));
		
		//the relative reference case (dot)
		list.add(new HitTester(null,new Hit("./")));
		list.add(new HitTester("filename.html",new Hit("./dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("./dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("./dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("./dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("./filename.html")));
		list.add(new HitTester("nofilename",new Hit("./nofilename")));
		list.add(new HitTester(null,new Hit("./dir/")));
		list.add(new HitTester(null,new Hit("./dir1/dir2/")));
		
		//the relative to parent reference case (dot)
		list.add(new HitTester(null,new Hit("../")));
		list.add(new HitTester("filename.html",new Hit("../dir/filename.html")));
		list.add(new HitTester("filename.html",new Hit("../dir1/dir2/filename.html")));
		list.add(new HitTester("nofilename",new Hit("../dir/nofilename")));
		list.add(new HitTester("nofilename",new Hit("../dir1/dir2/nofilename")));
		list.add(new HitTester("filename.html",new Hit("../filename.html")));
		list.add(new HitTester("nofilename",new Hit("../nofilename")));
		list.add(new HitTester(null,new Hit("../dir/")));
		list.add(new HitTester(null,new Hit("../dir1/dir2/")));
		
		//additional combined case (dot)
		list.add(new HitTester(null,new Hit("../..")));
		list.add(new HitTester(null,new Hit("../../")));
		list.add(new HitTester(null,new Hit("./..")));
		list.add(new HitTester(null,new Hit("./../")));
		list.add(new HitTester(null,new Hit("./../.")));
		list.add(new HitTester(null,new Hit("./.././")));
		list.add(new HitTester("filename.html",new Hit("../dir/../filename.html")));
		list.add(new HitTester("filename.html",new Hit("../dir/./filename.html")));
		list.add(new HitTester("filename.html",new Hit("../dir1/../dir2/filename.html")));
		list.add(new HitTester("filename.html",new Hit("../dir1/./dir2/filename.html")));

		File f = new File ( Hit.class.getResource("/bootstrap").toURI());
		
		HttpHost host = new HttpHost("www.example.com");
		
		for(HitTester hitTester : list){
			hitTester.hit.compute(f.getParent()+"/current/page/directory/", host);
			Assert.assertEquals(hitTester.expected,hitTester.hit.getFilename());
		}
	}
	@Test
	public void test_getDirectory() throws URISyntaxException{
		ArrayList<HitTester> list = new ArrayList<HitTester>();
		File f = new File ( Hit.class.getResource("/bootstrap").toURI());
		
		//the external http reference case
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("http://www.example.com/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("http://www.example.com/dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("http://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("http://www.example.com/dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("http://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("http://www.example.com/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("http://www.example.com/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("http://www.example.com/dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("http://www.example.com/dir1/dir2/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("http://www.example.com")));
		
		//the external ftp reference case
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("ftp://www.example.com/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("ftp://www.example.com/dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("ftp://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("ftp://www.example.com/dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("ftp://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("ftp://www.example.com/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("ftp://www.example.com/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("ftp://www.example.com/dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("ftp://www.example.com/dir1/dir2/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("ftp://www.example.com")));
		
		//the absolute reference case
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("/dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("/dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("/dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("/dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/",new Hit("/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir/",new Hit("/dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/dir1/dir2/",new Hit("/dir1/dir2/")));
		
		//the simple relative path reference case
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/",new Hit("filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/",new Hit("nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("dir1/dir2/")));
		
		//the relative reference case (dot)
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/",new Hit("./")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("./dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("./dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("./dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("./dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/",new Hit("./filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/",new Hit("./nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir/",new Hit("./dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/directory/dir1/dir2/",new Hit("./dir1/dir2/")));
		
		//the relative to parent reference case (dot)
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("../")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir/",new Hit("../dir/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir1/dir2/",new Hit("../dir1/dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir/",new Hit("../dir/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir1/dir2/",new Hit("../dir1/dir2/nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("../filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("../nofilename")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir/",new Hit("../dir/")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir1/dir2/",new Hit("../dir1/dir2/")));
		
		//additional combined case (dot)
		list.add(new HitTester(f.getParent()+"/www.example.com/current/",new Hit("../..")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/",new Hit("../../")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("./..")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("./../")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("./../.")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("./.././")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/",new Hit("../dir/../filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir/",new Hit("../dir/./filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir2/",new Hit("../dir1/../dir2/filename.html")));
		list.add(new HitTester(f.getParent()+"/www.example.com/current/page/dir1/dir2/",new Hit("../dir1/./dir2/filename.html")));

		HttpHost host = new HttpHost("www.example.com");
		
		for(HitTester hitTester : list){
			hitTester.hit.compute(f.getParent()+"/www.exemple.com/current/page/directory/", host);
			System.out.println(hitTester.hit.getDirectory());
			Assert.assertEquals(hitTester.expected,hitTester.hit.getDirectory());
		}
	}
	@Test
	public void test_getRequestedResource() throws URISyntaxException{
		ArrayList<HitTester> list = new ArrayList<HitTester>();
		//the external http reference case
		list.add(new HitTester("/",new Hit("http://www.example.com/")));
		list.add(new HitTester("/dir/filename.html",new Hit("http://www.example.com/dir/filename.html")));
		list.add(new HitTester("/dir1/dir2/filename.html",new Hit("http://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("/dir/nofilename",new Hit("http://www.example.com/dir/nofilename")));
		list.add(new HitTester("/dir1/dir2/nofilename",new Hit("http://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester("/filename.html",new Hit("http://www.example.com/filename.html")));
		list.add(new HitTester("/nofilename",new Hit("http://www.example.com/nofilename")));
		list.add(new HitTester("/dir/",new Hit("http://www.example.com/dir/")));
		list.add(new HitTester("/dir1/dir2/",new Hit("http://www.example.com/dir1/dir2/")));
		list.add(new HitTester("/",new Hit("http://www.example.com")));
		
		//the external ftp reference case
		list.add(new HitTester("/",new Hit("ftp://www.example.com/")));
		list.add(new HitTester("/dir/filename.html",new Hit("ftp://www.example.com/dir/filename.html")));
		list.add(new HitTester("/dir1/dir2/filename.html",new Hit("ftp://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("/dir/nofilename",new Hit("ftp://www.example.com/dir/nofilename")));
		list.add(new HitTester("/dir1/dir2/nofilename",new Hit("ftp://www.example.com/dir1/dir2/nofilename")));
		list.add(new HitTester("/filename.html",new Hit("ftp://www.example.com/filename.html")));
		list.add(new HitTester("/nofilename",new Hit("ftp://www.example.com/nofilename")));
		list.add(new HitTester("/dir/",new Hit("ftp://www.example.com/dir/")));
		list.add(new HitTester("/dir1/dir2/",new Hit("ftp://www.example.com/dir1/dir2/")));
		list.add(new HitTester("/",new Hit("ftp://www.example.com")));
		
		//the absolute reference case
		list.add(new HitTester("/",new Hit("/")));
		list.add(new HitTester("/dir/filename.html",new Hit("/dir/filename.html")));
		list.add(new HitTester("/dir1/dir2/filename.html",new Hit("/dir1/dir2/filename.html")));
		list.add(new HitTester("/dir/nofilename",new Hit("/dir/nofilename")));
		list.add(new HitTester("/dir1/dir2/nofilename",new Hit("/dir1/dir2/nofilename")));
		list.add(new HitTester("/filename.html",new Hit("/filename.html")));
		list.add(new HitTester("/nofilename",new Hit("/nofilename")));
		list.add(new HitTester("/dir/",new Hit("/dir/")));
		list.add(new HitTester("/dir1/dir2/",new Hit("/dir1/dir2/")));
		
		//the simple relative path reference case
		list.add(new HitTester("/current/page/directory/dir/filename.html",new Hit("dir/filename.html")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/filename.html",new Hit("dir1/dir2/filename.html")));
		list.add(new HitTester("/current/page/directory/dir/nofilename",new Hit("dir/nofilename")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/nofilename",new Hit("dir1/dir2/nofilename")));
		list.add(new HitTester("/current/page/directory/filename.html",new Hit("filename.html")));
		list.add(new HitTester("/current/page/directory/nofilename",new Hit("nofilename")));
		list.add(new HitTester("/current/page/directory/dir/",new Hit("dir/")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/",new Hit("dir1/dir2/")));
		
		//the relative reference case (dot)
		list.add(new HitTester("/current/page/directory/",new Hit("./")));
		list.add(new HitTester("/current/page/directory/dir/filename.html",new Hit("./dir/filename.html")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/filename.html",new Hit("./dir1/dir2/filename.html")));
		list.add(new HitTester("/current/page/directory/dir/nofilename",new Hit("./dir/nofilename")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/nofilename",new Hit("./dir1/dir2/nofilename")));
		list.add(new HitTester("/current/page/directory/filename.html",new Hit("./filename.html")));
		list.add(new HitTester("/current/page/directory/nofilename",new Hit("./nofilename")));
		list.add(new HitTester("/current/page/directory/dir/",new Hit("./dir/")));
		list.add(new HitTester("/current/page/directory/dir1/dir2/",new Hit("./dir1/dir2/")));
		
		//the relative to parent reference case (dot)
		list.add(new HitTester("/current/page/",new Hit("../")));
		list.add(new HitTester("/current/page/dir/filename.html",new Hit("../dir/filename.html")));
		list.add(new HitTester("/current/page/dir1/dir2/filename.html",new Hit("../dir1/dir2/filename.html")));
		list.add(new HitTester("/current/page/dir/nofilename",new Hit("../dir/nofilename")));
		list.add(new HitTester("/current/page/dir1/dir2/nofilename",new Hit("../dir1/dir2/nofilename")));
		list.add(new HitTester("/current/page/filename.html",new Hit("../filename.html")));
		list.add(new HitTester("/current/page/nofilename",new Hit("../nofilename")));
		list.add(new HitTester("/current/page/dir/",new Hit("../dir/")));
		list.add(new HitTester("/current/page/dir1/dir2/",new Hit("../dir1/dir2/")));
		
		//additional combined case (dot)
		list.add(new HitTester("/current/",new Hit("../..")));
		list.add(new HitTester("/current/",new Hit("../../")));
		list.add(new HitTester("/current/page/",new Hit("./..")));
		list.add(new HitTester("/current/page/",new Hit("./../")));
		list.add(new HitTester("/current/page/",new Hit("./../.")));
		list.add(new HitTester("/current/page/",new Hit("./.././")));
		list.add(new HitTester("/current/page/filename.html",new Hit("../dir/../filename.html")));
		list.add(new HitTester("/current/page/dir/filename.html",new Hit("../dir/./filename.html")));
		list.add(new HitTester("/current/page/dir2/filename.html",new Hit("../dir1/../dir2/filename.html")));
		list.add(new HitTester("/current/page/dir1/dir2/filename.html",new Hit("../dir1/./dir2/filename.html")));

		
		
		File f = new File ( Hit.class.getResource("/bootstrap").toURI());
		
		HttpHost host = new HttpHost("www.example.com");
		
		for(HitTester hitTester : list){
			hitTester.hit.compute(f.getParent()+"/www.exemple.com/current/page/directory/", host);
			Assert.assertEquals(hitTester.expected,hitTester.hit.getRequestedResource());
		}
	}
	@Test
	public void test_getTarget() throws URISyntaxException{
		HttpHost host = new HttpHost("localhost");
		
		ArrayList<HitTester> list = new ArrayList<HitTester>();
		
		//the external http reference case
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir/dir2/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir/")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com/dir1/dir2/")));
		list.add(new HitTester("www.example.com",new Hit("http://www.example.com")));
		
		//the external ftp reference case
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir1/dir2/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir/dir2/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/filename.html")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/nofilename")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir/")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com/dir1/dir2/")));
		list.add(new HitTester("www.example.com",new Hit("ftp://www.example.com")));
		
		//the absolute reference case
		list.add(new HitTester("localhost",new Hit("/")));
		list.add(new HitTester("localhost",new Hit("/dir/filename.html")));
		list.add(new HitTester("localhost",new Hit("/dir1/dir2/filename.html")));
		list.add(new HitTester("localhost",new Hit("/dir/nofilename")));
		list.add(new HitTester("localhost",new Hit("/dir/dir2/nofilename")));
		list.add(new HitTester("localhost",new Hit("/filename.html")));
		list.add(new HitTester("localhost",new Hit("/nofilename")));
		list.add(new HitTester("localhost",new Hit("/dir/")));
		list.add(new HitTester("localhost",new Hit("/dir1/dir2/")));
		
		//the relative reference case (dot)
		list.add(new HitTester("localhost",new Hit("./")));
		list.add(new HitTester("localhost",new Hit("./dir/filename.html")));
		list.add(new HitTester("localhost",new Hit("./dir1/dir2/filename.html")));
		list.add(new HitTester("localhost",new Hit("./dir/nofilename")));
		list.add(new HitTester("localhost",new Hit("./dir/dir2/nofilename")));
		list.add(new HitTester("localhost",new Hit("./filename.html")));
		list.add(new HitTester("localhost",new Hit("./nofilename")));
		list.add(new HitTester("localhost",new Hit("./dir/")));
		list.add(new HitTester("localhost",new Hit("./dir1/dir2/")));
		
		//the parent relative reference case (dot dot)
		list.add(new HitTester("localhost",new Hit("../")));
		list.add(new HitTester("localhost",new Hit("../dir/filename.html")));
		list.add(new HitTester("localhost",new Hit("../dir1/dir2/filename.html")));
		list.add(new HitTester("localhost",new Hit("../dir/nofilename")));
		list.add(new HitTester("localhost",new Hit("../dir/dir2/nofilename")));
		list.add(new HitTester("localhost",new Hit("../filename.html")));
		list.add(new HitTester("localhost",new Hit("../nofilename")));
		list.add(new HitTester("localhost",new Hit("../dir/")));
		list.add(new HitTester("localhost",new Hit("../dir1/dir2/")));
		
		File f = new File ( Hit.class.getResource("/bootstrap").toURI());
		for(HitTester hitTester : list){
			hitTester.hit.compute(f.getParent()+"/locahost/current/page/directory/", host);
			Assert.assertEquals(hitTester.expected,hitTester.hit.getTarget().getHostName());
		}
	}
}
