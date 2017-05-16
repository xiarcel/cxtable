package cxtable.core_comm;
import cxtable.*;

/*
This class handles write requests made by the rest of the program
to the sockets.  When the wrapping xClientConn calls the write(String)
method, the String is added to the xWriteQueue's vector.  The xWriteWorker
is polling the Queue  for new messages to send, and will use its
xMessageConverter to pack() each String (remove line-breaks) and then 
call the xSockWriter's write_(String) method, which actually writes it
to the underlying socket.  It uses a BufferedWriter, buffer set to 3000,
matching the xSocketReader's buffer.
 */

   import java.net.*;
   import java.io.*;

    public class xSockWriter extends Thread{
   
      private PrintWriter pw;
      private Socket sock;
      private xWriteQueue xwq = new xWriteQueue();
      private xWriteWorker xww;
   
   
      public xSockWriter(Socket s)
      {
         System.out.println("SockWriter 10-3-01");
         sock = s;
      }
   
      public void run(){
         new Thread(xww).start();
      }
   
      public boolean create()
      {
         xww=new xWriteWorker(xwq,this); 
         try {
            OutputStreamWriter osw=new OutputStreamWriter(sock.getOutputStream());
            BufferedWriter buf = new BufferedWriter(osw,3000);
            pw = new PrintWriter(buf,true);
         }
            catch (Exception e) {
               System.out.println("Writer returning false");
               return false;}
         new Thread(this).start();
         return true;
      }
      public boolean write(String ss)
      {xwq.add(ss);
         return true;
      }
   
      public void dump(String label)
      {System.out.println(label+"\n"+xwq.dump());}
   
   
      public boolean write_(String ss)
      {
         if (pw ==null)
         {
	System.out.println("PW Null");
            	xww.kill(); xww=null;
	if (create()==false){return false;}
         }
         pw.println(ss);
         return true;
      }
   
   /*end*/
   }

