package cxtable.peer;
import cxtable.core_comm.*;


/*this will connect a client that will then become a server piece for the
fallback peer-enable*/

   import java.net.*;

    public class xPEListenWorker extends Thread{
   
      private xPeerListener xpeer;
      private String reg_id_local, reg_id, inet;
      private int port;
      private Socket ss=null;
   	private String name;

      public xPEListenWorker(xPeerListener x, String ril, String ri, String in, String nm,int p)
      {
         port=p; reg_id=ri; reg_id_local=ril; inet=in; xpeer=x;name=nm;
      }
   
      public void run()
      {
         System.out.println("Attempting client for peer-enable");
         try{
            ss=new Socket(inet,port);
            xClientConn x = new xClientConn(ss);
		x.set_Name(name);
            new Thread(x).start();
            xpeer.peer_enable(reg_id_local,reg_id,x);
            System.out.println("Connected the client side of peer :-)~");
         }
            catch(Exception e)
            {System.out.println("Failed to connect");
               return;}
      
      }
   
   /*done*/
   }
