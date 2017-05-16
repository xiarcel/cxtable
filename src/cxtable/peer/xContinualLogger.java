package cxtable.peer;
import cxtable.gui.*;
import cxtable.*;
import cxtable.core_comm.*;


/*this is the first thing called to connect.  It tries the traditional client to "server"
end connection, and then calls the ffail method, indicating that it has not
worked... then the second thing tried is peer-enable (swap a second 
connection that has succeeded from a client to the server end here... 
making it act as the server for this client end... the last fail method is the
relay enable at the xRegistryServer*/
   

import java.net.*;

    public class xContinualLogger extends Thread{
      private Socket ss=null;
      private boolean debug=true;
      private String inet;
      private int port;

      private String svid;
      private String name;
      private String regid;
      private String namezero;
      private xRegistry xreggy;
      private xReadSorter sort;
      private xClientContinual contin;
      private xPanel report;
      private xConnect xcon;
      private xConnectHook xchook;
   
      public xContinualLogger(xConnect x,xClientContinual xh,xRegistry for_servs,xPanel rept)
      {	report=rept;
         xcon=x; contin=xh;
         svid=xcon.get_ServReg();
         namezero=xcon.get_ServName();
         regid=xcon.get_RegID();
         name=xcon.get_Name();
         port = xcon.get_Port();
         inet =xcon.get_IP();
         xreggy=for_servs;
         if(debug==true){
            System.out.println("name(yours)=="+name);
            System.out.println("name(other)=="+namezero);
            System.out.println("Running xContinualLogger with connect pool");
         }
      
      }

	public void run()

	{
	boolean failed=true;
	String mess="";
	for (int att=0; att<4; att++)
	{
	failed = run_connect();
	if (failed==false){complete(); return;}
	String msg=new String("Failed at connection:(try#):"+(att+1));
	System.out.println(msg);
	if (report !=null){report.post(msg);}
	
	}
	   System.out.println("Failed at connection\n"+mess+"\nattempting enabled");
           if (report !=null)
           {report.post("A failed connect (xConnect-type), delegating to peer-enabler");}
           contin.ffail(xcon);
	}

	
	
   
      public boolean run_connect()
      {
        
            try{
            
               System.out.println("Attempting client connect to "+inet+" at port:"+port);
               ss = new Socket(inet,port);
               System.out.println("Socket connected as client");
	       return false;
           	 }
               catch(Exception e){
               System.out.println("Error in sock connect:"+e.toString());
           
               }
	return true;
         
      }
        
	public void complete(){

         xClientConn xx=new xClientConn(ss);
      
         System.out.println("xClientConn created(XCL:ConnPool)");
         xx.setServerID(svid);
         xx.setClientID(regid);
         xx.setListen(sort);
         xClientNeg xn = new xClientNeg(xx,name,regid,contin);
         new Thread(xx).start();
         xx.setListen(xn);
         xReadSorter xrs = contin.getxRead();
         xPlugClientListen xpcl = new xPlugClientListen(xx,xreggy,xrs);
         new Thread(xpcl).start();
         xx.set_Name(namezero+"(as_client)");
      
      }
   
   /*done*/
   
   }
