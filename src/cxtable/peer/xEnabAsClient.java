package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.*;



/*I believe that this class handles making the connection as a client
for and requesting that the xRegistry make request the same from
what is to be the server...this is the part of the last fallback method
(relay through Registry)...and should be used only rarely..*/


  import java.net.*;

    public class xEnabAsClient extends Thread implements xListener,xTimed{
   
      private int port; private String inet; private xLineSplit xls=new xLineSplit();
      private String reg_key,serv_key,svname,clname;
      private boolean connected=false;
      private xRegistry clients,servers;
      private Socket s;
      private xClientConn xcc;
	private xConnect xcon;
      private xReadSorter xrso;
	private long set_delay;
	private static final String[] key=new String[]{"<COMMAND>"};
	



	public xEnabAsClient (String i, String p, xRegistry c, xRegistry s, xConnect xc, xReadSorter xrs, long to)
     {
	set_delay = to;
	xrso=xrs; servers=s; inet=i; port=0;
	try{port=Integer.parseInt(p);}
	    catch(Exception e){
		port=0;
		}
	clients=c;
	xcon=xc;
	svname = xcon.get_ServName();
	clname = xcon.get_Name();
	reg_key = xcon.get_RegID();
	serv_key = xcon.get_ServReg();
     }

	
      
	public xEnabAsClient (String i, String p, xRegistry c, xRegistry s,String rk, String sk, String sn, String cn,xReadSorter xrs)
      {	xrso=xrs;servers=s;
         svname=sn; clname=cn; reg_key=rk; serv_key=sk; inet=i;
         port=0; 
         try{port = Integer.parseInt(p);}
            catch(Exception e){
               port =0;}
         clients = c;
      }

	

      public String who(){
         return "xEnabAsClient";}
      public void run()
      {
         try{
            System.out.println("Connecting socket to:"+inet+"@port:"+port);
            s = new Socket(inet,port);
         }
            catch(Exception e){
               System.out.println("Failed at requesting client_enable"); 
               if (xcon !=null){xcon.kill();}
		return;}
         xcc = new xClientConn(s);
         xcc.setListen(this);
         new Thread(xcc).start();
	new Thread(new xTimer(set_delay,this,false)).start();

      }
   
      public void read(String s)
      {
         if (connected == true) {proc(s);
            return;}
         String comm = xls.ssplit("COMMAND",s);
         if (comm.equals("WHO"))
         {
            xcc.send("<WHO><TYPE>CLIENT</TYPE><SERVER>"+serv_key+"</SERVER><CLIENT>"+reg_key+"</CLIENT></WHO>");
         }
         if (comm.equals("CONNECT"))
         {
            connected = true; 
            return;
         }
         if (comm.equals("LOGIN")|comm.equals("LOGGED"))
         {
            proc(s);
         }
      }
   
      public void proc(String _s)
      {
         String xs = xls.ssplit("COMMAND",_s);
         if (xs.equals("LOGIN"))
         {
            xcc.send("<LOGIN><NAME>"+clname+"</NAME><IP></IP><PORT></PORT><REG_ID>"+reg_key+"</REG_ID></LOGIN>");
         }
         if (xs.equals("LOGGED"))
         {
            xcc.set_Name(svname+"(as_client_en)");
            xcc.setServerID(serv_key);
            xcc.setRegID(serv_key);
            xcc.setClientID(reg_key);
         	xcc.setEnableBit(true);
            clients.add(xcc); 
            new Thread(new xPlugClientListen(xcc,servers,xrso)).start();
            xcc.setListen(xrso);
            xcc.offListen(this);
         }
      }
	public String[] readKeys()
	{return key; 
	}

	public boolean readAll()
	{return false;}

	public void go()
	{
	 String s="anon";
	if (xcon !=null)
	   {
	   try{
	    s=xcon.get_ServName();
	    System.out.println("Killing xConnect-data for "+s+" connection");
	    xcon.kill();
	    }
	    catch(Exception e){System.out.println("Killing xConnect failed");}
	   }

	try{
	System.out.println("Killing xClientConn for:"+s);
	xcc.kill();
	}
	catch(Exception ee)
		{System.out.println("Killing xClientConn failed");
		}
	}


   	
   /*end*/
   }

