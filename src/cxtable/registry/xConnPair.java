package cxtable.registry;

import cxtable.core_comm.*;

/*this is related to relay enablement...the last fallback in a failed standard
connection.

The 1st fallback is to route the request from an existing server-end connect..
this server end connect another client...which switches...
In other words... if Bob has connected to me, but I cannot connect
to him... I route through my "outtie" to him, asking for a second connect
the same way...and then we switch that so that it becomes his "outtie"...
*/

    public class xConnPair implements xRemovable,xRemoveListen{
   
      private xClientConn client,server;
      private xRemoveListen xrl;
   
      public xConnPair(xClientConn c, xClientConn s, xRemoveListen xr)
      {xrl=xr;
         client=c; server=s;
      }
   
      public void connect()
      {
         client.setListen(new xCCDebug(server.getServerID(),server));
         server.setListen(new xCCDebug(client.getClientID(),client));
         String s= "<COMMAND>CONNECT</COMMAND>";
         client.send(s); server.send(s);
         client.setReg(this); server.setReg(this);
      }
   
      public xClientConn cl()
      {
         return client;
      }
   
      public void remove(xRemovable r)
      {
         client=null; server=null;
         xrl.remove(this);
      }
   
      public xClientConn sv()
      {
         return server;
      }
   
   /*end*/
   }
