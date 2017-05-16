package cxtable.registry;
import cxtable.core_comm.*;
import cxtable.peer.*;
import cxtable.*;



/*This class is intended to provide support for situations in which one of 
the participants cannot participate as a ServerSocket, but obviously can
create a Socket connection to the "Registry"...  While this is intended as a 
de-centralized peer-to-peer (non relay chat), there might be times (and/or 
transient internet issues) that dictate an 'enabled' connection.  This will
still not be an IRC-style, the Registry side will merely maintain a connection
for them, by requesting of the one another 'client' connection (which functions
as the server side of their end) and then matching it with the actual client
who has requested the enabled connection

Another (later note)...  this class is kept separate from the xRegistry
server...and could be later removed if it turns out to be un-necessary...

*/


   import java.net.*;

   import java.awt.TextArea;
   import java.awt.Panel;


    public class xEnableServer extends Thread implements xEnableListener, xServerListener, xTimed{
   
      private xServSocket xss;
      private xRegistryServer xrs;
      private xRegistry reggy;
      private xRegistry servers = new xRegistry();
      private xRegistry clients = new xRegistry();
      private String inetad,portst;
      private String notify;
      private TextArea who = new TextArea("",7,20,TextArea.SCROLLBARS_VERTICAL_ONLY);
   
      public xEnableServer(xRegistry reg, xRegistryServer x)
      {
         reggy=reg; xrs = x;
      }
   
      public Panel getRef()
      {
         Panel p = new Panel();
         p.add(who);
         return p;
      }
   
      public void run()
      {
      /*create xServSocket, pass self to it*/
      
         new Thread(new xServSocket(this)).start();
      /*create a thread that monitors the client/server xRegs, and matches them
      connecting them into piped connections*/
         xEnableMatcher xem = new xEnableMatcher(clients,servers,this);
         new Thread(xem).start();
         new Thread(new xTimer(10000,xem,true)).start();
      /*used to be done anonymously (above 3 lines)... done this way to add 
      	a time-able function.. go() calls report() which posts based on enableds
      	on*/
      
      }
   
   
      public void add(Socket s){
      /*xServerListener, new Socket in from ServSocket*/
         xClientConn x = new xClientConn(s);
         xEnabler xen = new xEnabler(x,reggy,this);
         new Thread(x).start();
      /*create an enabler for this xClientConn*/
         new Thread(xen).start();
      
      
      
      }
   
   
      public void regserver(String inet, String port){
      /*xServerListener, ServSocket registers its inet,port*/
         inetad=inet; portst=port;
         /*set mssg*/
         notify = "<ENABLER><IP>"+inetad+"</IP><PORT>"+portst+"</PORT></ENABLER>";
      
      	/*create a recurring xTimer*/
      
         new Thread(new xTimer(60000,this,true)).start();
      
      /*let the xRegistryServer know which mssg to tag to xMobiles out there*/
         xrs.setPiggyBack(notify);
         go();
      }
   
      public void go()
      {
         xClientConn[] whom = reggy.on();
         for (int i=0; i<whom.length; i++)
         {whom[i].send("Mssg from xEnableServer:"+notify);}
      }
   
      public void appende(String s)
      {
         who.setText(s);
      }
   
   
   
      public void enable(xClientConn x, boolean b)
      {
      /*xEnableListener:: Adds to Client xRegistry on true, Server xRegistry on false*/
      /*These are 2 separate xRegistry's, for temporary pieces to a 2 xClientConn
      piped connection, until they are 'piped'__Two options: 1- Pass self to xRegServer
      so that self can be added to broadcasts 2- maintain separate broadcast to others
      (#1 seems stronger)*/
      
         if (b==true)
         {
            clients.add(x);
         }
         else {servers.add(x);}
      }
   
   /*end of xEnableServer*/
   }
