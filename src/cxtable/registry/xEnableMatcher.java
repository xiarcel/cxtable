package cxtable.registry;
import cxtable.*;
import cxtable.core_comm.*;
import cxtable.peer.*;




/*this class  runs through the Client registry and Server registry of
the enable connections in the enable server... it handles matching them up
by sweeping the contents...so that the connection between the two can
actually be made... this is part of the registry-relay last fallback.*/


 import java.util.Vector;

    public class xEnableMatcher extends Thread implements xTimed, xRemoveListen{
      private xRegistry spiece,cpiece;
      private Vector connects = new Vector();
      boolean running;
      private xEnableListener xel;
   
      public xEnableMatcher(xRegistry o, xRegistry t,xEnableListener el)
      {xel=el;
         cpiece=o; spiece=t;
      }
   
      public void run()
      {
         running=true;
         while(running)
         {/*
            if ((cpiece.on().length==0) | (spiece.on().length==0))
            {
               try{Thread.sleep(1000);}
                  catch(Exception e){
                  }
               continue;}*/
            xClientConn[] c = cpiece.on();
            if (c.length==0){
               try{Thread.sleep(100);}
                  catch(Exception e){
                  }
               continue;}
            for (int i=0; i<c.length; i++)
            {
               xClientConn[] s = spiece.on();
               String cs = c[i].getServerID();
               String cc = c[i].getClientID();
               for (int j=0; j<s.length; j++)
               {
                  String ss=s[j].getServerID();
                  String sc=s[j].getClientID();
                  System.out.println("cs:"+cs+":cc:"+cc+":ss:"+ss+":sc:"+sc);
                  if ((cs.equals(ss)) & (cc.equals(sc)))
                  {
                     connect(c[i],s[j]); 
                     cpiece.remove(c[i]); spiece.remove(s[j]);
                     break;
                  }
               }
            }
         /*end of while loop*/
         }
      /*end of run*/
      }
   
      public void connect(xClientConn client_, xClientConn serv_)
      {
         xConnPair xcp = new xConnPair(client_,serv_,this);
         xcp.connect();
         connects.addElement(xcp);
         report();
      }
   
      public void remove(xRemovable r)
      {
         for (int i=0; i<connects.size(); i++)
         {
            try{xRemovable rem = (xRemovable)connects.elementAt(i);
               if (r == rem) {connects.removeElementAt(i);go(); 
                  return;}
            }
               catch(Exception e)
               {System.out.println("XEM:remove:class-cast");}
         }
      }
   
      public void go()
      {report();
         return;}
      private void report()
      {/*this method can be called from inside or out, to report*/
         xConnPair[] xca = on();
         String sa ="";
         for (int i=0; i<xca.length; i++)
         {
            sa = sa+ xca[i].cl().getClientID()+"&&"+xca[i].sv().getServerID()+"\n";
         }
         xel.appende(sa);
      }
   
      public xConnPair[] on()
      {
         xConnPair[] ons = new xConnPair[connects.size()];
         for (int i=0; i<ons.length; i++)
         {
            ons[i]=(xConnPair)connects.elementAt(i);
         }
         return ons;
      }
   /*end*/
   }

