package cxtable.registry;

import cxtable.xLineSplit;
import cxtable.core_comm.*;
import cxtable.peer.*;


/*this class handles the xEnablement :-)  
It determines what exactly the connection is... and adds it where
appropriate.*/


    public class xEnabler extends Thread implements xListener{
   
      private xLineSplit xls = new xLineSplit();
      private xEnableListener enlisten;
      private xRegistry registered;
      private xClientConn who;
	private static final String[] key= new String[]{"<WHO>"};
   
      public xEnabler(xClientConn x, xRegistry r, xEnableListener e)
      {
         enlisten=e; registered=r; who=x;
      }
   
      public void run()
      {
         who.setListen(this);
         who.send("<COMMAND>WHO</COMMAND>");
      }
   public String who(){return "xEnabler";}
      		
      public void read(String s)
      {
         String whom = ""; String what = "";
         whom = xls.ssplit("WHO",s);
         if (whom.equals("")){
            return;}
         what = xls.ssplit("TYPE",whom);
         if (what.equals("")){
            return;}
      
         if (what.equals("SERVER"))
         {
            process(false,s);
         }
         if (what.equals("CLIENT"))
         {
            process(true,s);
         }
      }
   
      public void process(boolean b, String _s)
      {
         String serv=""; String clie="";
         serv=xls.ssplit("SERVER",_s);
         clie=xls.ssplit("CLIENT",_s);
         if ((serv.equals("")==true) | (clie.equals("")==true))
         {System.out.println("Incomplete data");
            return;}
         who.setServerID(serv); who.setClientID(clie);
         who.setControl(b); 
      
         if (b==true)
         {
            String mssg="<EN_REQUEST><CLIENT>"+clie+"</CLIENT></EN_REQUEST>";
            xClientConn[] xccs =registered.on();
            for (int i=0; i<xccs.length; i++)
            {
               if (xccs[i].getRegID().equals(serv))
               {xccs[i].send(mssg);
                  System.out.println("Found server match");
                  break;
               }
            }
         }
         who.offListen(this);
         who.send("<VOLLEY></VOLLEY>");
         enlisten.enable(who,b);
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


