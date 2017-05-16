package cxtable.peer;

import cxtable.xLineSplit;
import cxtable.core_comm.*;


/*this class maintains the location of the enable server for that last
fallback...whenever an update is broadcast..it adjusts its info...  So that
if it is needed...*/

    public class xEnableContinual extends Thread implements xListener{
   
      private String inet="";private String pt="";private String reg_key;
      private xLineSplit xls=new xLineSplit();
      private xClientConn from_reg;
      private xRegistry servers;
	private final static String[] key =new String[]{"<ENABLER>","<EN_REQUEST>"};
   	
   
   
      public xEnableContinual(xClientConn x, xRegistry r, String reg_k)
      {
         reg_key= reg_k; from_reg=x; servers=r;
      }
   
      public void run()
      {
         from_reg.setListen(this);
      }
      public String who(){
         return "xEnableContinual";}
      public void read(String s)
      {String enabs = ""; String cmmds="";
         enabs = xls.ssplit("ENABLER",s);
         if (inet.equals("") | pt.equals(""))
         {	
            if (enabs.equals("")){
               return;}
            inet = xls.ssplit("IP",enabs);
            pt = xls.ssplit("PORT",enabs);
            System.out.println("inet:"+inet+"::port:"+pt);
         
         }
      
         cmmds = xls.ssplit("EN_REQUEST",s);
         if (cmmds.equals("")){
            return;}
         String cl_id = xls.ssplit("CLIENT",cmmds);
         new Thread(new xEnabAsServer(inet,pt,cl_id,reg_key,servers,this)).start();
      }

public String[] readKeys()
{return key; 
}

public boolean readAll()
{return false;}


   
      public String getInet(){
         return inet;}
      public String getPort(){
         return pt;}
   
   
   /*end*/
   }
