package cxtable.core_comm;
import cxtable.gui.*;
import cxtable.*;

/*this class is a Vector manager...  It has a Vector that
contains xRemovable(s).  Normally, this stores xClientConn(s),
so the on() call returns an xClientConn[] representing the
Vector....  It can also be used to store anything that implements
xRemovable... There is one case where this is used...

xClientConn calls xRegistry's remove on itself to clean up when
the connection dies... This keeps the xRegistry's contents fairly
synchronized with the connections...

xValidator adds "server-side" connects to the xRegistry.
xClientNeg adds "client-side" connects to a different xRegistry.
(There are two of these in each "xMobileServer")
*/


  import java.util.Vector;

    public class xRegistry implements xRemoveListen{
      private boolean debug=true;
      private Vector reg;
      private xListener def;
      private xPanel reporter;
   	private xConnectPool xcp=null;
      public xRegistry()
      {
	System.out.println("xRegistry version 10-6-01");
         reg=new Vector();
      }
   
      public xRegistry(boolean deb)
      {
         debug=deb;
      }
   
      public xRegistry(xListener x)
      {def=x;}
   
      public void setReport(xPanel xp)
      {
         reporter=xp;}
   
      public void setDefaultListen(xListener x)
      {def = x;}
   
   	public void setConnectPool(xConnectPool x)
   	{
   	xcp=x;
   	}
   	
      public xClientConn[] on()
      {
         xClientConn[] temp = new xClientConn[reg.size()];
         if (temp.length < 1)
         {
            return temp;}
      
         for (int i=0; i<temp.length; i++)
         {
            temp[i] = (xClientConn)reg.elementAt(i);
         }
         return temp;
      }
   
      public void add(xClientConn x)
      {
         if (debug==true){System.out.println("Adding "+x.get_Name()+" to registry");}	
         	/*This is a new method to check if the one being added equals one already in*/
      
         xClientConn[] xcca = on();
      
         for (int j=0;j<xcca.length;j++)
         {
            if (xClientConn.equals_(x,xcca[j]) == true)
            {
               if (debug==true)
               {System.out.println(x.get_Name()+", already in registry");
               }
               x.kill();
               return;
            }
         }
      if (debug == true) {System.out.println(x.get_Name()+"..added to Registry");}
         if (reporter !=null) {reporter.post(x.get_Name()+" is a logged connection..");} 
         if(def != null){x.setListen(def);
            if (debug==true){System.out.println("Added registry's default listener");}
         }
	
      		/*new..triggers remover to erase connects in pool*/
      	if (xcp !=null) {new Thread(new xConnectRemover(xcp,x)).start();}

         reg.addElement(x);
         x.setReg(this);

         if (debug == true)
         {dump();}
      }
      public void dump()
      {
         xClientConn[] tempies = on();
         if (tempies.length > 0)
         {
            System.out.println("ONLINE\n=======");
            for(int i=0; i<tempies.length; i++)
            {System.out.println(tempies[i].get_Name()+":SK:"+tempies[i].getServerID()+":CK:"+tempies[i].getClientID());}
         }
      }
   
      public xClientConn find(String name)
      {
         for (int i=0; i<reg.size(); i++)
         {
            xClientConn tmp = (xClientConn)reg.elementAt(i);
            if (tmp.get_Name().equalsIgnoreCase(name))
            {
               return tmp;}
         }
         return null;
      }
   
      public xClientConn remove(String name)
      {
         for (int i=0; i<reg.size(); i++)
         {
            xClientConn tmp = (xClientConn)reg.elementAt(i);
            if (tmp.get_Name().equalsIgnoreCase(name))
            {reg.removeElementAt(i); 
               return tmp;}
         }
         return null;
      }
   
      public void remove(xRemovable x)
      {
         for (int i=0; i<reg.size();i++)
         {
            try{
               xRemovable r = (xRemovable)reg.elementAt(i);
               if (x == r) {
                  if (reporter !=null)
                  {
                     try{
                        xClientConn tm = (xClientConn)r;
                        reporter.post(tm.get_Name()+", removed as a connection");
                     }
                        catch(Exception ee)
                        {reporter.post("Removed something as a connection");}
                  }
               
                  reg.removeElementAt(i);
                  return;}
            }
               catch(Exception e)
               {System.out.println("remove removable called on non-removable");
               }
          /*end of for-loop*/
         }
      }
   
      public void remove(xClientConn xx)
      {
         for (int i=0; i<reg.size(); i++)
         {
            xClientConn tmp = (xClientConn)reg.elementAt(i);
            if (tmp==xx) 
            {
               if (reporter!=null){reporter.post(tmp.get_Name()+" is logging off.._xcc_");}
               System.out.println("Removing "+tmp.get_Name()+" from registry");
               reg.removeElementAt(i);}
         
            if (debug==true)
            {dump();}
         }
      }
   
   
   /*end*/
   }


