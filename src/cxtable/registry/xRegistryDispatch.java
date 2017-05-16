package cxtable.registry;
import cxtable.core_comm.xRegistry;
import cxtable.core_comm.xClientConn;


/*this class is the onetime dispatch of already-on info to the new person
and new-person info the already-on people*/

    public class xRegistryDispatch extends Thread{
   
      private xRegistry xreg; 
      private xClientConn xcc;
      private String pb;
   
   
      public xRegistryDispatch(xRegistry xr, xClientConn xc, String p)
      {
         xcc=xc;  xreg=xr; pb=p;
      }
   
      public void run()
      {
         xClientConn[] dispatch = xreg.on();
         xreg.add(xcc);
         String mssg = "<USER><NAME>"+xcc.get_Name()+"</NAME><PORT>"+xcc.get_Port()+"</PORT><IP>"+xcc.get_IP()+"</IP>";
         mssg=mssg+"<REG_ID>"+xcc.getRegID()+"</REG_ID></USER>";
      
         String already = "";
         for (int i=0; i<dispatch.length; i++)
         {	already=already+"<USER><NAME>"+dispatch[i].get_Name()+"</NAME><PORT>"+dispatch[i].get_Port()+"</PORT>";
            already=already+"<IP>"+dispatch[i].get_IP()+"</IP><REG_ID>"+dispatch[i].getRegID()+"</REG_ID></USER>";
            dispatch[i].send(mssg+pb);
         }
         System.out.println("Message to already on:"+mssg);
         already=already+pb;
         xcc.send(already);
      }
   
   /*end*/
   }
