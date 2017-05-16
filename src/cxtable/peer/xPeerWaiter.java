package cxtable.peer;

import cxtable.xLineSplit;
import cxtable.core_comm.*;



/*this class waits for the login  command to be received, 
and then makes it a client when it was once a server*/

    public class xPeerWaiter extends Thread implements xListener{
      private xClientConn xcc;
      private xLineSplit xls=new xLineSplit();
      private xRemCommandListener xrem;
   	private static final String[] key =new String[]{"<COMMAND>"};
   
      public xPeerWaiter(xClientConn x,xRemCommandListener xrcl)
      {
         xcc=x;
         xrem=xrcl;
      }
      public void run()
      {
         xcc.setListen(this);
      }
   
      public void read(String s)
      {String cmmd="";
      
         cmmd = xls.ssplit("COMMAND",s);
         if (cmmd.equals("")){
            return;}
         if (cmmd.equals("LOGIN"))
         {
            xrem.switch_state(xcc);
            xcc.offListen(this);
         }
      }
   
   /*obligatory 'who' for debugging/errors*/
   
      public String who(){
         return "xPeerWaiter";}

public boolean readAll()
{return false;}

public String[] readKeys()
{return key;  
}
   


   /*done*/
   }
