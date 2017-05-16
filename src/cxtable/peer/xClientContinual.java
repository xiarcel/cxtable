package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.*;
import cxtable.gui.*;


/*this class is created when a client connection is made with the
Registry server...  it handles dispatching enable-client and peer-client
as well..  

However, it's primary function is to listen for new connects at the Registry
Server, and then spawn the client connects to the new users on...all the
while making sure that if one connect is taking too long, it does not
spawn a new connect for the user...if it is already working on one...
*/
  


 import java.net.*;

    public class xClientContinual extends Thread implements xListener,xConnectHook{
   
	private static final String[] key =new String[0];
      private xRegistry clients,serv;
      private xMessageConverter xmc=new xMessageConverter();
      private xClientConn xcc; private xLineSplit xls;
      private String name;
      boolean debug=false;
      private String eip,ept;
      private String clienttag = "(as_client)";
      private xReadSorter xrso;
      private String reg_id;
      private xEnableContinual xenc;
      private boolean test=false;
      private xPanel reporter;
	private String my_ip,my_port;
      private xListenerRegistry xlr=new xListenerRegistry();
      private xConnectPool xcpool = new xConnectPool();
      public xClientContinual(xClientConn x, xRegistry xr, xRegistry sv,xReadSorter xrs,String n, String r_k)
      {	xrso=xrs;
         reg_id = r_k;
         System.out.println("Reg_id:"+r_k);
         name=n;serv=sv;
         clients = xr;
      
         xcc=x;xls= new xLineSplit();
         xenc = new xEnableContinual(xcc,serv,reg_id);
      
      }
   
      public void run()
      {new Thread(xenc).start();
         xlr.add(xenc);
			clients.setConnectPool(xcpool);
         xcc.setListen(this);

      if (reporter !=null)
	{new Thread(new xAutoProcessor(xcc,reporter)).start();}
	else{
		new Thread(new xAutoProcessor(xcc)).start();
	        }

      }

public String getIP()
{return my_ip;}
public String getPort()
{return my_port;}

public boolean readAll()
{return true;}

public String[] readKeys()
{return key;  }


	public void setIP_Port(String ii, String pp)
	{my_ip=ii; my_port=pp;}
   
      public void setReport(xPanel r)
      {reporter=r;}
   
      public void process(xConnect x)
      {
         if (check(x.get_ServReg())==false){
            System.out.println("We have an xClientConn w/ this ID, killing this xConnect");
            x.kill();return;
         }
         new Thread(new xContinualLogger(x,this,serv,reporter)).start();
      
      }
   
      public void read(String s)
      {new Thread(new xListenerDispatch(xlr,xmc,s)).start();
      
         String[] users = xls.split("USER",s);
         new Thread(new xConnectWorker(users,name,reg_id,xcpool,this,my_ip,my_port)).start();
		}
   
      public void add(xClientConn x)
      {	
         clients.add(x);
         if(debug==true){x.setListen(new xPrinter("xClientContinual\n"));}
      }
      public void firstfail(String i, String n)
      {
         if (reporter !=null) {reporter.post("..failed at "+n+" connection\nRequesting peer_enable..");}
         new Thread(new xPeerRequester(this,i,n,name,reg_id,xcc.get_IP(),xcc.get_Port(),serv,3)).start();
      }
      public void ffail(xConnect x)
      {
         if (reporter !=null){reporter.post("..failed connect is:"+x.get_ServName());}
         new Thread(new xPeerRequester(this,x,serv,3)).start();
      }
   
      public xRegistry getClientDB()
      {
         return clients;
      }
   
      public String who(){
         return "xClientContinual";}
   
      private boolean check(String k)
      {
         xClientConn[] xcl = clients.on();
         for (int i =0; i<xcl.length;i++)
         {System.out.println("Evaluating ("+k+") versus ("+xcl[i].getServerID()+")");
            if (xcl[i].getServerID().equals(k) == true){
               return false;}
         }
         return true;
      }
   
      public void setEs(String i, String p)
      {
         eip=i; ept=p;
      }
   
      public xReadSorter getxRead()
      {
         return xrso;
      }
   
   
	/*the first enab_log will be a legacy method once
		the one below it is verified to work*/

   
      public void enab_log(String _k, String svname, String clname)
      {
         eip = xenc.getInet();
         ept = xenc.getPort();
         if ((eip.equals("")) | (ept.equals("")))
         {
            System.out.println("XCC:No Enable Data yet");
            return;}
         System.out.println("xCCtoxEAS:"+eip+":"+ept+":"+reg_id+":"+_k+":"+svname+":"+clname);
      
         new Thread(new xEnabAsClient(eip,ept,clients,serv,reg_id,_k,svname,clname,xrso)).start();
      }

	public void enab_log(xConnect xc)
	{
	eip= xenc.getInet();
	ept= xenc.getPort();
	if ((eip.equals(""))|(ept.equals("")))
		{
		System.out.println("xCC:No Enab data--> w/ xConnect");
		xc.kill();
		return;
		}
	System.out.println("xCCtoxEAS--> enabling a client piece (xConnect)");
	new Thread(new xEnabAsClient(eip,ept,clients,serv,xc,xrso,240000L)).start();
      }

   
   /*end*/
   }





