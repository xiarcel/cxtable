package cxtable.comm;

//import cxtable.data.*;
import java.net.*;
import java.io.*;

public class CommandServer extends Thread implements CommandServerListener{

	private boolean running=false;	
	private ServerSocket server;
	private int port=0; //default to random
	private CommandServerListener gate=this;
	
	public CommandServer()
	{
	}
	
	public void add(CommandServerListener csl)
	{
		gate=csl;
	}
	
	public CommandServer(int port)
	{
		this.port=port;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public CommandServer connect() throws Exception
	{
		server=new ServerSocket(port);
		port=server.getLocalPort();
		return this;
	}
	
	public void run()
	{
		running=true;
		System.err.println("Listening at port:"+getPort());
		while (running)
		{
			Socket cxsock=getNextSocket(server);
			if (cxsock!=null){gate.process(cxsock);}
		}
	}
	
	private Socket getNextSocket(ServerSocket ss)
	{
		Socket s=null;
		try 
		{
			s=ss.accept(); return s;
		}
		catch (Exception e){}
		return null;
	}
			
	public void kill() {running=false;}
	public void process(Socket s)
	{
	ClientConnection cc=new ClientConnection(s);
	cc.start();
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length >= 3 && args[0].equals("connect"))
		{
			new ConnectTest(args[1],args[2],args).start();
			
		}
		else 
		{
		new CommandServer().connect().start();
		}
	}
}

class ConnectTest extends Thread
{
	private String host;
	private int port;
	private String[] args;
	ConnectTest(String h, String p, String[] a)
	{
		port=Integer.parseInt(p);
		host=h;
		args=a;
	}
	
	public void run()
	{
		try {
		Socket s =new Socket(host,port);
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		pw.println("Messages from client");
		for (int i=3; i<args.length;i++)
		{
			pw.println("\t"+args[i]);
		}
		s.close();
		}
		catch (Exception e) {e.printStackTrace(); System.exit(0);}
	}
}

	
		
