package cxtable.comm;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientConnection extends Thread implements ConnectionReadListener
{

	private Socket sock;
	private PrintWriter write;
	private ClientConnectionReader ccr;
	
	public ClientConnection(Socket s)
	{
		sock=s;
	}
	
	public void write(String s) throws Exception
	{
		if (write == null) {
			write=new PrintWriter(sock.getOutputStream(),true);

		}
		write.println(s);
		write.flush();
	}
	
	public void process_message(String s)
	{
		//s=s.trim();
		if (s.indexOf("</CLOSE_CONNECTION>")>-1) {
			try {ccr.kill();}
			catch (Exception e) {e.printStackTrace();}
		}
		
		else if (s.indexOf("</SYSTEM_EXIT>")>-1) {
			System.exit(0);
		}
		
		
		System.err.println(s);
	}
	
	public void run()
	{

		ccr=new ClientConnectionReader(sock);
		ccr.addListener("default",this);
		ccr.useListener("default");
		ccr.start();
	}
	
}


class ClientConnectionReader extends Thread{
	private static long COUNT=0;
	private Socket sock;
	private BufferedReader read;
	private HashMap listeners=new HashMap();
	private String main_key=null;
	private boolean running=false;
	private long me;
	
	ClientConnectionReader(Socket s)
	{
		sock=s;
		me=COUNT++;
	}
	
	public void addListener(String key, ConnectionReadListener crl)
	{
		listeners.put(key,crl);
	}
	
	public void useListener(String key)
	{
		main_key=key;
	}
	
	public void run()
	{
		running=true;
		System.err.println("Initiated ClientConnectionReader:"+me);
		try {
			read=new BufferedReader(new InputStreamReader(sock.getInputStream()));
	
			String s;
			while(running && (s=read.readLine())!=null)
			{
				((ConnectionReadListener)listeners.get(main_key)).process_message(s);
			}
			read.close();
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	public void kill()
	{
		running=false;
	}
}

