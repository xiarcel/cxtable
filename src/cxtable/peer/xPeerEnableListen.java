package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.gui.*;
import cxtable.*;


/*this class constantly listens on its client end for requests to make
the a client-server switch for the peer-enable fallback*/

    public class xPeerEnableListen extends Thread implements xReadDeposit,xPeerListener,xRemCommandListener,xValidListener{
   
   
      private xLineSplit xls=new xLineSplit();
      private String reg_id_local,name;
      private String key="PEER_ENABLE";
      private String skey = "<PEER_ENABLE>";
      private String ekey = "</PEER_ENABLE>";
      private xRegistry servers;
      private xPanel reporter;
      private xValidator xval;
   
      public xPeerEnableListen(String ri, String nm,xRegistry servs)
      {reg_id_local=ri;
         servers=servs;name=nm;
      }
   
      public String getKey()
      {
         return key;
      }
   
      public void setKey(String k)
      {
         key=k;
         skey = "<"+key+">";
         ekey = "</"+key+">";
      }
   
      public void setReport(xPanel r)
      {reporter=r;}
   
   
      public void append(String[] s)
      {
         for (int i=0; i<s.length;i++)
         {	String regid="";
            regid = xls.ssplit("REG_ID",s[i]);
            String inet="";
            inet=xls.ssplit("INET",s[i]);
            String port="";
            port=xls.ssplit("PORT",s[i]);
		String nmy="";
		nmy=xls.ssplit("NAME",s[i]);
         
            if ((regid.equals(""))| (inet.equals(""))| (port.equals("")))
            {
	    System.out.println("Peer Enable called w/o complete data");}
            int pt=0; 
            try{
               pt=Integer.parseInt(port);
            }
               catch(Exception e){
                  pt=0;}	 
            if (reporter!=null){reporter.post("A peer_enable for "+regid+" has been requested");}
            new Thread(new xPEListenWorker(this,reg_id_local,regid,inet,nmy,pt)).start();
         
         }
      
      }
   
      public void peer_enable(String ril, String ri, xClientConn x)
      {
         
         new Thread(new xPeerWaiter(x,this)).start();
         x.setClientID(ri);x.setServerID(ril);
	
      /*	x.send("<COMMAND>SET_NAME="+name+"</COMMAND>");*/
      }
   
      public void switch_state(xClientConn x)
      {
         String ril = x.getServerID();
         String om="<SERVERPEER><REG_ID>"+ril+"</REG_ID><NAME>"+name+"</NAME></SERVERPEER>";
         x.send(om);
         System.out.println("mssg:"+om+", sent");
         try{Thread.sleep(1000);}
            catch(Exception e){
            }
         System.out.println("xpel:in switch_state()");
         xval=new xValidator(x,this);
         new Thread(xval).start();
      
      }
   
      public void validated(xClientConn x)
      {
         servers.add(x);
      }
   
   }
