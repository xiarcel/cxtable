package cxtable.core_comm;

import cxtable.*;

/*this class is the storage manager for xConnect objects...  one
of the things compared to the new xConnects created...  (The other
is the client xRegistry...to make sure that the actual new connect isn't
already  completely processed...)...  It can either create a new 
xConnect from the data provided...or it can handle an xConnect object.
*/


   import java.util.Vector;

    public class xConnectPool implements xRemoveListen{
   
      private Vector connects;
      private boolean debug=true;
      private xLineSplit xls=new xLineSplit();
   
      public xConnectPool()
      {
         connects=new Vector();
      }
   
      public boolean add(String user, String name, String regid, xConnectHook xch, String my_ip, String my_port)
      {
         if (user.equals("")==true){
            return false;}
         boolean added_one=false;
            String ip=xls.ssplit("IP",user);
            String nm=xls.ssplit("NAME",user);
            String lreg = xls.ssplit("REG_ID",user);
            int pt=0;
            try {pt=Integer.parseInt(xls.ssplit("PORT",user));}
               catch(Exception e){
                  pt=0;}
         
            if (ip.equals("")|nm.equals("")|lreg.equals(""))
            {
               System.out.println("Skipping this connect, incomplete");
               return false;}
            if (debug==true){System.out.println("Adding:"+nm+" to connect pool");}
            xConnect xcn=new xConnect(ip,nm,lreg,name,regid,pt,my_ip,my_port);
            add(xcn);
            xch.process(xcn);
            added_one=true;
         
      	/* if ONE got added, true..*/
         return added_one;
      }
   
   
   
      private boolean add(xConnect xc)
      {
      /*does this work?  It is checking to see if one of what it currently has
       matches this xConnect*/
         if (connects.size() == 0){connects.addElement(xc);
            xc.setRemoveListen(this);
            return true;}
         xConnect[] xcons = on();
         for (int i=0; i<xcons.length; i++)
         {
            if (xConnect.are_equal(xc,xcons[i])==true)
            {
               return false;}
         }
         connects.addElement(xc);
         return true;
      }
   
      public void remove(xRemovable r)
      {
         try {xConnect xx = (xConnect)r;
            boolean b= remove(xx);
         }
            catch(Exception e){
            }
      }

	public boolean remove(xConnect xc, xClientConn x)
	{
	xConnect[] xcons=on();
	for (int i=0; i<xcons.length; i++)
	{
	if (xc==xcons[i])
		{
		if (x.get_Name() == null)
			{
			String sn=xc.get_ServName()+"(as_client_pe)";
	
			x.set_Name(sn);
			System.out.println("Set xcc-client name to:"+sn);
			}
		if (x.getServerID().equals(""))
			{
			String sr=xc.get_ServReg();
			x.setServerID(sr);
			System.out.println("Set xcc-servreg to:"+sr);
			}
		if (x.getClientID().equals(""))
			{
			String rg=xc.get_RegID();
			x.setClientID(rg);
			System.out.println("Set xcc-clientid to:"+rg);
			}
		System.out.println("Removing "+xcons[i].get_ServName()+" from pool");
		connects.removeElementAt(i); 
               return true;
		}
	}
   	return false;
	}
   
      public boolean remove(xConnect xc)
      {
         xConnect[] xcons = on();
         for (int i=0; i<xcons.length; i++)
         {
            if (xc == xcons[i]) {
		System.out.println("Removing "+xcons[i].get_ServName()+" from pool");
		connects.removeElementAt(i); 
               return true;}
         }
         return false;
      }
   	public xConnect[] on_(){return on();}
   	
      private synchronized xConnect[] on()
      {		
         xConnect[] xcs = new xConnect[connects.size()];
         if (xcs.length == 0){ 
            return xcs;}
         if (debug==true)
         {System.out.println("xConnectPool..dump from .on()");}
         for (int i=0; i<xcs.length;i++)
         {	
            xcs[i] = (xConnect)connects.elementAt(i);
            if (debug==true) {System.out.println("Remote:"+xcs[i].get_ServName());}
         }
      
      
         return xcs;
      }
   
   /*done?*/
   }
