package cxtable.peer;
import cxtable.*;
import cxtable.core_comm.*;


/*this class is the counterpart to the xEnabAsClient piece...
it tells the Registry server that it is part of the enable..the server end...

Again..this is part of the last fallback method (relay through Registry) that
should rarely, if ever, ..be used*/
 

  import java.net.*;

    public class xEnabAsServer extends Thread implements xListener{
   
	private static final String[] key =new String[]{"<COMMAND>","<LOGIN>"};
      private xLineSplit xls = new xLineSplit();
      private String inet,prt,client_id,reg_id; 
      private boolean connected;
      private xRegistry servers;
      private xClientConn xcc;private xEnableContinual xenci;
      private int port;
   
      public xEnabAsServer(String i, String p, String c, String r, xRegistry x, xEnableContinual xen)
      {
         connected=false;xenci=xen;
         inet=i; prt=p;
         port =0;
         try{port = Integer.parseInt(prt);}
            catch(NumberFormatException nfe){
               port=0;}
         servers= x; reg_id=r; client_id=c;
      }
   
      public String who(){
         return "xEnabAsServer";}
      public void run()
      {	
         do {
            inet = xenci.getInet();
            prt=xenci.getPort();
         }
         while (inet.equals("")|prt.equals(""));
         System.out.println("xEnabAsServer:inet:"+inet+":port:"+prt);
         try {port=Integer.parseInt(prt);}
            catch(Exception e){
               port=0;}
      
         try{
            System.out.println("Try for socket");
         	/*create socket*/
            Socket s = new Socket(inet,port);
         
            xcc = new xClientConn(s);
            xcc.setListen(this);
            new Thread(xcc).start();
            System.out.println("xcc::socket connected");
         }
            catch(Exception e){
               System.out.println("Failed at socket:xcc creation");
               return;}
      }
   
      public void read(String s)
      {
         if (connected==true) {process_serv(s);
            return;}
         String cmmd=""; cmmd=xls.ssplit("COMMAND",s);
         if (cmmd.equals("")){
            return;}
         if (cmmd.equals("WHO"))
         {
            String z ="<WHO><TYPE>SERVER</TYPE><SERVER>"+reg_id+"</SERVER>";
            z=z+"<CLIENT>"+client_id+"</CLIENT></WHO>";
            xcc.send(z);
         }
         if (cmmd.equals("CONNECT"))
         {	xcc.send("<COMMAND>CONNECT</COMMAND>");
            connected = true; 
            System.out.println("Connected server side of an enabled connection");
            xcc.send("<COMMAND>LOGIN</COMMAND>");
         }
      }
   
      public void process_serv(String _s)
      {
         String conn = "";
         conn=xls.ssplit("LOGIN",_s);
         if (conn.equals("")){
            return;}
         String name=""; String ips = ""; String prts=""; String cid="";
         name = xls.ssplit("NAME",conn); ips=xls.ssplit("IP",conn);
         cid = xls.ssplit("REG_ID",conn); prts=xls.ssplit("PORT",conn);
         if (name.equals("") | cid.equals(""))
         {xcc.send("<COMMAND>LOGIN</COMMAND>");
            return;}
         xcc.setServerID(reg_id);
         xcc.setClientID(cid);
         xcc.set_Name(name);
         xcc.set_Port(prts);
         xcc.set_IP(ips);
      	xcc.setEnableBit(true);
         xcc.send("<COMMAND>LOGGED</COMMAND>");
         servers.add(xcc);
         xcc.offListen(this);
      }
   
public boolean readAll()
{return false;}

public String[] readKeys()
{return key; 
}

   /*end*/
   }
