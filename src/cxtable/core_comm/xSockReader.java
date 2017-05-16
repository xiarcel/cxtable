package cxtable.core_comm;


/*this class reads through a buffered-reader a line at a time
from the Socket's inputstream.  Its buffer is set to match xSockWriter's, 
currently 3000.  With each line read, the wrapping xClientConn's 
process(String) method is called.  the xClientConn handles delegating
the messages to its xListener(s).  If the socket reads 20 nulls in a row, 
it closes.. by triggering methods in xClientConn it also makes sure
that the container (xRegistry) removes it for gc()
*/
 
  import java.io.*;
   import java.net.*;

    public class xSockReader extends Thread{
   
      private BufferedReader br;
      private xClientConn ef;
      private Socket sock;
      boolean febug=false;
	private int nullcount=0;
   
      public xSockReader(Socket s, xClientConn _e)
      {
         ef=_e;
         sock = s; 
      
      }
   
      public boolean create()
      {
         try {br = new BufferedReader(new InputStreamReader(sock.getInputStream()),3000);
         }
            catch (Exception e){
               System.out.println("Could not create reader"); 
               return false;}
         return true;
      }
   
      public void run()
      {
         String so;
         try{
            while (true)
            
            {
               so = br.readLine();
               if(febug==true){
                  System.out.println(so);}
	       if (so!=null)
		{
               try{ef.process(so);
			nullcount=0;
			}
                  catch(Exception e){
                     System.out.println("Missed::"+so);}
            	}
		else{nullcount++;
			if(nullcount >20){
			System.out.println("Read over 20 continuous nulls");
				throw new Exception();
			}
		    }            
		}
         } 
            catch(Exception e){
               System.out.println("Reader closed::"+ef.get_Name()+"::\n"+e.toString());
            
               ef.removeListeners();
               System.out.println("Removed listeners from "+ef.get_Name());
               System.out.println("Sending remove notification to registry");
               ef.remove();
            
            	/*End of Exception:The SockReader has now removed itself to 
            	lessen the probability of being referenced by Chat :-)*/
            }
      
         if(febug){System.out.println("Read");}
      
      }
   
   /*
   end*
   */
   }

