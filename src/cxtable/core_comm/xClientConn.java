package cxtable.core_comm;
import cxtable.plugin.*;
import cxtable.peer.*;



/*
This class manages the Socket streams and provides a front end for
the rest of the application to write to and read from the sockets.  
When a class calls the send(String) method, it passes that to xSockWriter
for writing.
This class maintains an xListenerRegistry with all of the objects
that implement xListener who've registered with this xClientConn.

When xSockReader reads, it passes line by line to this class, which
then dispatches a xListenerDispatch thread.  This thread handles passing
the String to the xListener(s) for processing.

Currently, each peer-to-peer connection uses -2- of these... as the
connection is "client-end" initiated... 
*/

   import java.io.*;
   import java.net.*;


    public class xClientConn extends Thread implements xOutListen,xListener,xRemovable{
   
      private Socket sock;
	private static int total_xcc=0;
	private int me;
	private static final String[] key = new String[0];
      private boolean enabled=false;
      private xMessageConverter xmc=new xMessageConverter();
      private boolean chatty=false;
      private xRemoveListen xreggy;
      private boolean listening=false;
      private xSockReader xsr;
      private xSockWriter xsw;
      private String name,port,itad;
      private xListenerRegistry listeners;
      private String alkey="";
      private String server_id=""; private String client_id="";
      private String reg_id ="";
      private boolean control;
   	
      public xClientConn(Socket _s)
      {
	total_xcc++;
	 me=total_xcc;
         	sock=_s;
         xsr = new xSockReader(sock,this);
         xsw = new xSockWriter(sock);
         listeners=new xListenerRegistry();
      }
   
      public void run()
      {
         if (xsr.create() == true) {new Thread(xsr).start();}
      
         if (xsw.create() == true) 
         {
            try{xsw.write("<OK>"+sock.getInetAddress()+"[port]:"+sock.getLocalPort()+"</OK>");}
               catch(Exception e) {
                  System.out.println("Error writing 'OK'");
               /*error here*/
               }
         }	
      }
      public void send(String s)
      {
         /*System.out.println("in_send");*/
         boolean b;
         int x=0;
         do{x++; b=xsw.write(s);
            if (x>10) {b=true;}} while(!b);      
      }
      public String who(){
         return "xClientConn:"+reg_id+"(("+me+" of "+total_xcc+"))" ;}

      public boolean readAll()
	{return true;}

      public String[] readKeys()
	{return key;}


      
      public void process(String s)
      {
         
      	 if (s==null)
	 	{System.out.println("xCC("+name+"):Received null string");
		 return;}

         new Thread(new xListenerDispatch(listeners,xmc,s)).start();
      
      
      /*end process*/
      }
   
      public void set_Name(String n)
      {name=n;}
      public String get_Name()
      {return name;}

      public void set_IP(String s)
      {itad=s;}
      public String get_IP()
      {return itad;}
      
      public void set_Port(String s)
      {port=s;}
      public String get_Port()
      {return port;}
   
      public void setListen(xListener x)
      { 
      
         listeners.add(x);
         listening = true;
      }
   	
   	/*The next two methods are for the SocketReader*/
      public void removeListeners()
      {
         listeners.removeAll();listening=false;
      }
   
      public void remove()
      {
         if (xreggy !=null)
         {xreggy.remove(this);}
         xsw.dump(who());
      
      }
   	/*The following method is for the xRegistry to add itself*/
   
      public void setReg(xRemoveListen x)
      {
         xreggy=x;
      }
   
      public void offListen(xListener x)
      {
         listeners.remove(x);
      }

      public void read(String s)
      {send(s);}
   
      public void setRegID(String s)
      {reg_id = s;}
   
      public void setClientID(String s)
      {client_id=s;
         if (chatty == true){System.out.println("xCC setClientID to:"+client_id);}
      }
   
      public void setServerID(String s)
      {server_id=s;}
   
      public String getRegID()
      {
         return reg_id;}
   
      public String getClientID()
      {
         return client_id;}
   
      public String getServerID()
      {
         return server_id;}
      public void setControl(boolean b)
      {control=b;}
      public boolean getControl()
      {
         return control;}
   
   
   /*newest methods for peer-to-peer enable... need to check the instance
   of xCC to make sure that it has not been accomplished by reg-server 
   enable, or else attempting it peer-to-peer is futile*/
   
      public void setEnableBit(boolean b)
      {enabled=b;}
   
      public boolean getEnableBit()
      {
         return enabled;}
   
      public void kill()
      {
         send(who()+":"+name+":-> closing");
      
         try{Thread.sleep(1000);}
            catch(Exception e){
            }
         try{sock.close();}
            catch(Exception e){
               System.out.println("Could not close socket");}
      }
   
   
   /*end class*/
   
   /*static compare method*/
   
      public static boolean equals_(xClientConn one, xClientConn two)
      {
         if (one == two) {
            return true;}
         String or=one.getRegID();String oc=one.getClientID(); String os=one.getServerID();
         String tr=two.getRegID();String tc=two.getClientID(); String ts=two.getServerID();
      /*comment out later*/
         System.out.println(or+"::"+oc+"::"+os+"\n"+tr+"::"+tc+"::"+ts);
      
      /*this compares RegID, ClientID and ServerID.. if ALL three are the same, this 
      is the same ClientConn {even if it is not the same EXACT object}*/
         if ((or.equals(tr)) & (oc.equals(tc))&(os.equals(ts)))
         {
            return true;}
      
         return false;
      }
   }
