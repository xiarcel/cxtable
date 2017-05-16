package cxtable.core_comm;
import cxtable.gui.xPanel;
import cxtable.peer.xSwitchListener;
import cxtable.xLineSplit;


/*this class handles the server side validation of a socket connection*/

    public class xValidator extends Thread implements xListener{
      private final String _who = "xValidator";
      private xValidListener xvl;
      private xLineSplit line = new xLineSplit();
      private xClientConn xcc;
      private boolean pass_req=false; private String storedpass="";
      private String lab ="";
      private xSwitchListener xsl;
      private xPanel reporter=null;
   	private static final String[] key =new String[]{"<SERVERPEER>","<LOGIN>"};
      public xValidator(xClientConn x, xValidListener v)
      {
         xvl=v;xcc=x;
      }
   
      public xValidator(xClientConn x, xValidListener v, String s)
      {
         xvl=v; xcc=x;
         lab=s;
      }
      public String who(){
         return _who;}
      public void setReport(xPanel r)
      {reporter=r;}
   
      public void run()
      {
         xcc.setListen(this);
         xcc.send("<COMMAND>LOGIN</COMMAND>");
      }
      public void setSwitchListen(xSwitchListener xx)
      {
         xsl=xx;
      }
   
      public void read(String s)
      {	String swit ="";
         swit=line.ssplit("SERVERPEER",s);
         if (swit.equals("")==false)
         {
            String ids = line.ssplit("REG_ID",swit);
            String nms = line.ssplit("NAME",swit);
            xcc.setServerID(ids);
            xcc.set_Name(nms+"(as_client_pe)");
            System.out.println("xcc.setName("+nms+"(as_client_pe))");
            xsl.switch_to_client(xcc);
            if (reporter!=null)
            {reporter.post("This is a serverpeer request");}
         
            xcc.offListen(this);
            return;
         }
      
         String[] logins = line.split("LOGIN",s);
         if (logins.length < 1)
         {
            return;}
         String[] name = line.split("NAME",logins[0]);
         String[] ip = line.split("IP",logins[0]);
         String[] port = line.split("PORT",logins[0]);
         String[] pwd = line.split("PWD",logins[0]);
         String ids = line.ssplit("REG_ID",logins[0]);
      
         int n=name.length; int i=ip.length; int p=port.length;
      
         if ( (n<1) | (i<1) | (p<1) )
         {xcc.send("<COMMAND>FAIL</COMMAND>"); 
            return;}
      
         if (pass_req == true)
         {
            if (pwd.length <1)
            {xcc.send("<COMMAND>FAIL</COMMAND>"); 
               return;}
            if (pwd[0].equals(storedpass)==false)
            {xcc.send("<COMMAND>FAIL</COMMAND>"); 
               return;}
         }
         xcc.set_Name(name[0]);
         System.out.println("Received connection from "+name[0]+" at "+ip[0]+" on port "+port[0]+" with RegID:"+ids);
        	if (reporter!=null){reporter.post(name[0]+", has logged in.");}
         xcc.send("<COMMAND>LOGGED</COMMAND>");
         xcc.setClientID(ids);
         xcc.setRegID(ids);
         xcc.set_IP(ip[0]);
         xcc.set_Port(port[0]);
         xcc.offListen(this);
         xvl.validated(xcc);
      }
public boolean readAll()
{
 return false;
}

public String[] readKeys()
{
return key;
}

   
   /*end*/
   }

