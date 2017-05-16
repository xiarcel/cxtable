package cxtable.core_comm;

import cxtable.xLineSplit;
import cxtable.peer.xClientContinual;


/*this class handles login negotiating for the client end of a connection...
once the actual socket has been connected..this handles negotiating 
a real status with the other end...used for both connects to Registry and 
connects to other peers*/
  

  public class xClientNeg extends Thread implements xListener{
      private final String _who = "xClientNeg";
      private xLineSplit xls; private xClientContinual xclco;
      private xClientConn xclient; private String mssg = "";
      private String name,reg_id,serv_id;
	private String my_ip,my_port;
   	private static final String[] key= new String[]{"<COMMAND>"};
      public xClientNeg(xClientConn x, String nm, String r_k, xClientContinual xcc)
      {	reg_id=r_k;
	my_ip=xcc.getIP();my_port=xcc.getPort();
         name = nm; mssg = "<LOGIN><REG_ID>"+reg_id+"</REG_ID><IP>"+my_ip+"</IP><PORT>"+my_port+"</PORT><NAME>"+name+"</NAME></LOGIN>";
         xclient = x; xclco = xcc;
         xls = new xLineSplit();}
   
      public void run()
      {xclient.send(mssg);}
   
      public String who(){
         return _who;}
   
      public void read(String s)
      {
         String[] cmd = xls.split("COMMAND",s);
      
         if (cmd.length < 1 ) {
            return;}
      
         for (int i=0; i<cmd.length; i++)
         {
         
            if (cmd[i].equals("LOGIN")==true)
            {
               new Thread(this).start();
               return;
            }
            if (cmd[i].equals("LOGGED")==true)
            {
               System.out.println("Logged in as "+name);
               xclient.offListen(this);
               xclco.add(xclient);
               xclient.setClientID(reg_id);
            
            }
         }
      }

public boolean readAll()
{return false;}

public String[] readKeys()
{return key; 
}

   /*end*/
   
   }
