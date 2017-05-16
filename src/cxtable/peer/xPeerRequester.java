package cxtable.peer;
import cxtable.core_comm.*;

/*requests the peer from the other end...Calls that one to make a client
to it, and switch it and make it the server end for the failed client connect*/

    public class xPeerRequester extends Thread{
   
      private int attempts;
      private xRegistry servers;
      private String svid,svname,name,reg_id,my_ip,my_port;
      private xClientContinual xcont;
      private String inet,port;
      private xConnect xcon;
   
      public xPeerRequester(xClientContinual c, String si, String sn, String n, String ri, String in,String pt, xRegistry r, int tries)
      {
         xcont=c; svid=si; svname=sn; name=n; reg_id=ri; inet=in; port=pt;
         servers=r; attempts=tries;
      }
   
      public xPeerRequester(xClientContinual c, xConnect x, xRegistry r, int tries)
      {
         attempts=tries;
         xcont=c;
         xcon=x;
         svid = x.get_ServReg();
         svname = x.get_ServName();
         inet = x.get_IP();
         port = new String(""+x.get_Port());
         servers=r;
         reg_id=x.get_RegID();
         name=x.get_Name();
      }
   
   
      public void run()
      {
         for (int i=0; i<attempts;i++)
         {
            xClientConn[] ons = servers.on();
            for (int j=0; j<ons.length; j++)
            {
               if (ons[j].getEnableBit() == true)
               {
                  /*checking to make sure that we will not use one
                  that has been accomplished as enabled through registry*/
                  continue;
               	/*we have skipped this one*/
               } 
            
               if (ons[j].getClientID().equals(svid)==true)
               {                  
                  ons[j].send("<MSSG><PEER_ENABLE><INET>"+xcon.get_my_IP()+"</INET><PORT>"+xcon.get_my_Port()+"</PORT><REG_ID>"+reg_id+"</REG_ID></PEER_ENABLE></MSSG>");
                  return; 
               }
            }
            try{Thread.sleep(1500);}
               catch(Exception e){
               }
         }
      	/*let's do real-"enable" standard for now*/
         if (xcon !=null){xcont.enab_log(xcon); return;}
      	 

          xcont.enab_log(svid,svname,name);
      
	}
   
   /*end*/
   }
