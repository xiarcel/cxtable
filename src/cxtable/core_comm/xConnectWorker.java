package cxtable.core_comm;

import cxtable.*;

/*
this class is threaded, and adds new xConnects to the xConnectPool..
which then notifies the xClientContinual of the new connect...launching
the actual connection attempt*/

    public class xConnectWorker extends Thread{
   
      private xConnectHook xchook;
      private String[] u; private String n,r; 
      private xConnectPool xpool;
	private String my_ip,my_port;
   
      public xConnectWorker(String[] users, String nm, String ri, xConnectPool xcp,xConnectHook xc, String mi, String mp)
      {
         xchook=xc; u=users;n=nm;r=ri;xpool=xcp;my_ip=mi;my_port=mp;
      }
   
      public void run()
      {
        String report="";
        for (int i=0; i<u.length;i++)
      	{
         boolean b =xpool.add(u[i],n,r,xchook,my_ip,my_port);
         report=report+"Added "+u[i]+":"+b+"\n";
   		}
   		System.out.println("xConnectWorker report:\n"+report);
   		}
   
   /*done*/
   }
