package cxtable.core_comm;
import cxtable.peer.xPlugClientListen;




/*this is a threaded dispatcher..it receives the registry of listeners...
the xMessageConverter (uses it one at a time)  and the line read...
It dispatches the line to each listener registered*/
 
/*new xListenerDispatch function...including updated xListener interface...
  This will now first ask an xListener if it reads all (readAll())..
	if (true){dispatch;return;}
	if (false){request String[] of keys from xListener;
		   navigate String[]:
			if (true){dispatch;return;}
		   if still there, fall-through
		  }

*/


 import java.util.Vector;

    public class xListenerDispatch extends Thread{
   
      private xListenerRegistry listen; private String rd;
      private String[] rds;
      private boolean single;
      private boolean debug=false;
      private xMessageConverter xmc;
      private String _s;
      private String[] _ss;
   
	static{System.out.println("Dynamic xListenerDispatch vers 11-21-01");}

	
   
      public xListenerDispatch(xListenerRegistry v, xMessageConverter xm, String s)
      {	xmc=xm;
         listen=v; 
         single=true;
         _s=s;
	
      }
   
      public void setDeb(boolean b){
         debug = b;}
      public void run()
      {
         if (_s == null){System.out.println("xListenerDispatch:null");
            return;}
	long start = System.currentTimeMillis();
         if (xmc == null){xmc=new xMessageConverter();}
      
         	/*'low level' pack|unpack new 7-16-01*/
      
         try{rd = xmc.unpack(_s);}
         
            catch(Exception e){
            
               rd=_s;}
      
      
         xListener[] xl = listen.on();
      
         for (int j=0; j<xl.length; j++)
         {
            if (xl[j] ==null)
            {
               continue;}
         
            try {
               if (debug==true){System.out.println("Dispatching to "+xl[j].who());}
	       if (xl[j].readAll()==true)
		{xl[j].read(rd);continue;}
	       
               String[] kys = xl[j].readKeys();
		    for (int q=0; q<kys.length;q++)
			{
			int p = rd.indexOf(kys[q]);
			if (p>-1)
               		    {xl[j].read(rd);continue;
			    }
			}
		}
            
               catch(Exception e){
                  System.out.println("Failed at xlistener");
                  try{System.out.println("who?:"+xl[j].who()+e.toString());}
                     catch(Exception ee){
                        System.out.println("who?null"+ee.toString());}
                  try{xPlugClientListen xxx = (xPlugClientListen)xl[j];
                     System.out.println("xPCL:Failed at reading-->"+rd);   
                     e.printStackTrace();}
                     catch(Exception cce){
                     }
               }
         
         
         }
 benchmark(start);           
      }

private void benchmark(long l)
{
long end = System.currentTimeMillis();
long tot = end-l;
System.out.println("Dispatcher 11-21-01, dispatched in "+tot+" millis..");
}

   
   }
