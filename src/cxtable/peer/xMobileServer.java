package cxtable.peer;
import cxtable.*;
import cxtable.gui.*;
import cxtable.core_comm.*;
import cxtable.registry.xRegDisplay;




/*This is the main class for running a client-peer....  It handles creating
a server (xMiniServer), and then creating a login piece to the Registry server...
From that login piece (xClientLogManager) an xClientContinual is created..
That piece reads the persistent connection with the Registry and
receives new user broadcasts...

This also contains the reporter xPanel which is used by the GUI (and many 
of the elements) to report activity..

It also is a central place for the xRegistries (server side {outies} and client
side {innies})... */

/*check this one !!!! does it need to be an xListener????*/

 import java.net.Socket;

    public class xMobileServer extends Thread implements xServerListener,xListener,xPostDispatch,xNameListen{
   
      private xClientLogManager xclm;
      private xMiniServer xms;
      private String name, port, inet;
      private String where="";
      private xRegistry xreggy,for_clients;
	private boolean GUI_DEBUG=false;
      private xReadSorter xrs=new xReadSorter();
   	private xPanel reporter = new xPanel("Report from system::","REPORT");
	private final static String[] key =new String[0];
   
   
      public xMobileServer(String listensite)
      {  for_clients = new xRegistry();
         where=listensite;
         xreggy=new xRegistry();
	 reporter.setSize(6,30,true);
      
      }

	public xMobileServer()
	{for_clients=new xRegistry();
	 where="";
	 xreggy=new xRegistry();
	 reporter.setSize(6,30,true);
	}
   
      public void run()
      {xreggy.setReport(reporter);
	for_clients.setReport(reporter);
         xms = new xMiniServer(xreggy,this);
	xms.setReport(reporter);
      	xms.setClientReg(for_clients);
         new Thread(xms).start();
      }
   
      public String who(){
         return "xMobileServer";}
      public void add(Socket s)
      {}
      public void read(String s)
      {}
   
   
      public void regserver(String s, String t)
      {
         System.out.println("Registered a server at "+s+","+t);
         inet = s; port = t;
	xClientLogManager xclm=null;
	if (where.equals("")==true){
	xclm = new xClientLogManager(for_clients,xreggy,where,inet,port,xrs,xms,this,true);
	}
	else{
	xclm = new xClientLogManager(for_clients,xreggy,where,inet,port,xrs,xms,this,false);
            }
	xclm.setReport(reporter);
	 new Thread(xclm).start();
      }
   
      public xRegistry client_reg()
      {
         return for_clients;}
   
      public xRegistry server_reg()
      {
         return xreggy;}
   
      public void login_name(String n)
      {	
if (GUI_DEBUG)
   {new Thread(new xReDirect()).start();}

else{
new Thread(new xReDirect("xmobserv_"+n+".cgt")).start();
	}
/*used to be xCommShell routine

xCommShell xcs = new xCommShell(xreggy,for_clients,xrs,n);
xcs.setReport(reporter);
         new Thread(xcs).start();
*/
/*below replaces it*/

xLinkableComm xlc =new xLinkableComm(xreggy,for_clients,xrs,n);
xCommPluginable xcp = new xCommAsPlug(n);
xcp.setLinkable(xlc);
xcp.setReport(reporter);
xcp.setRegDisplay(new xRegDisplay(xreggy));

new Thread(xcp).start();


	
      }
   
      public void setMssgListen(xMssgListen xml)
      {}

public boolean readAll()
{return true;}

public String[] readKeys()
{return key;}

      public static void main(String[] args)
      {
        /*
	 new Thread(new xMobileServer("http://www.mywebsite.com/directory/filename")).start();
      	*/
	new Thread(new xMobileServer()).start();

      }
   /*end*/
   }
