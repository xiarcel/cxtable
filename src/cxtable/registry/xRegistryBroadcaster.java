package cxtable.registry;
import cxtable.core_comm.xRegistry;
import cxtable.core_comm.xClientConn;



/*this class sends the list of who is currently on to who is currently on*/

    public class xRegistryBroadcaster extends Thread{
   
      private xRegistry registry;
      private boolean running;
      private long timeout;
   
   
      public xRegistryBroadcaster(xRegistry x, long t)
      {
         timeout = t;
         registry=x;
      }
   
      public xRegistryBroadcaster(xRegistry x)
      {
         timeout=360000;
         registry=x;
      }
   
      public void run()
      {
         running = true;
         while (running)
         {
            try{Thread.sleep(timeout);}
               catch(Exception e){
               }
         
            System.out.println("Sending Broadcasts");
            xClientConn[] xccn = registry.on();
            for (int i=0; i<xccn.length; i++)
            {System.out.println("Broadcasting to "+xccn[i].get_Name());
               String s="<BROADCAST></BROADCAST>";
               for(int j=0; j<xccn.length; j++)
               { 
                  if (i==j){
                     continue;}
                  s=s+"<USER><NAME>"+xccn[j].get_Name()+"</NAME>";
                  s=s+"<PORT>"+xccn[j].get_Port()+"</PORT>";
                  s=s+"<REG_ID>"+xccn[j].getRegID()+"</REG_ID>";
                  s=s+"<IP>"+xccn[j].get_IP()+"</IP></USER>";
               }
               xccn[i].send(s);
            }
         }
         System.out.println("xRegistryBroadcaster leaving run()");
      }
   
   /*end*/
   }






