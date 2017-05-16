package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.plugin.*;

/*changed 7-13-01... shut it up for log-file

A multi-broadcast piece...  more than likely used to
dispatch messages to multiple peers

*/

    public class xMultiBroadcast implements xOutListen{
   
      private xRegistry servers;
   
      public xMultiBroadcast(xRegistry x)
      {servers=x;}
   
      public void send(String s)
      {

/*WAY TOO CHATTY, shut this up to avoid cluttering the log files*/
/*System.out.println("Sending mssg:\n"+s);*/
/**/

         xClientConn[] xx = servers.on();
         new Thread(new xMultiBroadcastWorker(xx,s)).start();
      }
   
   /*end*/
   }
   class xMultiBroadcastWorker extends Thread{
   
      private xClientConn[] xcc; private String mssg;
   
      xMultiBroadcastWorker(xClientConn[] x, String s)
      {mssg="<MSSG>"+s+"</MSSG>"; xcc=x;}
   
      public void run()
      {
         for (int i=0; i<xcc.length; i++)
         {
            try{xcc[i].send(mssg);}
               catch(Exception e){
               }
         }
      }
   
   /*end*/
   }
