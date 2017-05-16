package cxtable.core_comm;


/*this class is constructed with a xWriteQueue -vector manager handling
Strings-.  It polls the xWriteQueue to determine whether there is 
anything to draw...and then when there is, cycles through them one 
at a time.  The xMessageConverter is for pack()ing the Strings so 
that each line (ends with \r\n or \n) is placed between two tags..
<LN></LN>...this keeps the BufferedReader on the other end
from barfing.*/
  

  public class xWriteWorker extends Thread{
   
      private xSockWriter xsw; private xWriteQueue xwq; 
      private boolean running;
   	private boolean debug = false;
	private xMessageConverter xmc = new xMessageConverter();
   
      public xWriteWorker(xWriteQueue q, xSockWriter w)
      {
        xsw=w; xwq=q;
      }
   
      public void kill()
      {
         running = false;
      }
   
      public void restart()
      {
         new Thread(this).start();
      }
   
      public void run()
      {
         running = true;
         while (running)
         {
            String s="";
            boolean z;
            boolean now =xwq.empty();
            while (now)
            {now = xwq.empty();
               try{Thread.sleep(300);}
                  catch(Exception e){
                  }
            }
            s=xwq.pop();
	    try{
 	    String wrt = xmc.pack(s);
           	     z = xsw.write_(wrt);
	     }
	catch(Exception e){z=xsw.write_(s);}

            if (debug==true){System.out.println("Wrote:"+z);}
         }
      
      }
   
   }
