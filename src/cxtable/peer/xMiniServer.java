package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.gui.xPanel;



/*this class handles processing the various server-side sockets it
receives..and the xClientConn(s)...  it is the xServerListener for the peer's
ServerSocket piece...*/


   import java.net.*;

    public class xMiniServer extends Thread implements xServerListener, xValidListener, xSwitchListener{
   
      private xRegistry xreggy,client; 
      private String inet,port;
      private xServerListener xservlisten;
      private String lab="";
      private String name="";
      private xClientContinual xcont;
	private xPanel reporter;
   
   
      public xMiniServer (xRegistry xr, xServerListener xsl)
      {
         xreggy=xr; xservlisten=xsl;
      
      }
   	public void setReport(xPanel r)
	{reporter=r;}

      public void run()
      {
         new Thread(new xServSocket(this)).start();
      
      }
   
      public void add(Socket s)
      {	xClientConn xxx= new xClientConn(s);
         xxx.setServerID(lab);
         xValidator xval = new xValidator(xxx,this);
	 if(reporter!=null){reporter.post("Receiving a connection..");}
         xval.setSwitchListen(this);
	 xval.setReport(reporter);
         new Thread(xval).start();
         new Thread(xxx).start();
      }
   
      public void validated(xClientConn x)
      {
         xreggy.add(x);
         x.send("Inet:"+inet+",Port:"+port+" has logged you in");
      
      }
   
      public void regserver(String i, String p)
      {inet=i; port=p;
         xservlisten.regserver(inet,port);
      }
   
      public String get_Inet(){
         return inet;}
      public String get_Port(){
         return port;}
   
      public void setLabel(String s)
      {
         lab=s;
      }
   /*new methods for peer-to-peer enabling*/
      public void setClientReg(xRegistry x)
      {
         client=x;
      }
   
      public void set_Name(String s)
      {
         name=s;
      }
   
      public void setContinual(xClientContinual xx)
      {
         xcont=xx;
      }
   
      public void switch_to_client(xClientConn x)
      {	System.out.println("Switch_to_client:name:"+name+":lab:"+lab+"::");
         xClientNeg xnegs=new xClientNeg(x,name,lab,xcont);
         x.setListen(xnegs);
      	xReadSorter xrs = xcont.getxRead();
      	
      	xPlugClientListen xpcl = new xPlugClientListen(x,xreggy,xrs);
      	x.setListen(xpcl); new Thread(xpcl).start();
      	
      }
   
   /*done with new peer-to-peer methods*/
   
   /*end*/
   }
