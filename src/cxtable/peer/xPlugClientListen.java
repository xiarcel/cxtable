package cxtable.peer;
import cxtable.core_comm.*;
import cxtable.xLineSplit;
import cxtable.plugin.*;


/*this class listens for the receiving end of the plugin..currently..it
unabashedly accepts all plugin directives and looks to match
its client piece with the server piece...and then launches the
plugin..if it is there*/
 
  import java.awt.*;
   import java.awt.event.*;

    public class xPlugClientListen extends Thread implements xListener{
      private xRegistry servies; private xClientConn xcc;
      private xLineSplit xls=new xLineSplit();
      private xReadSorter xrs;
      Frame fram;
	private final static String[] key=new String[]{"<PLUG>"};

      public xPlugClientListen(xClientConn x, xRegistry serv,xReadSorter xr)
      {
         xrs=xr;
         servies = serv;
         xcc=x;
      }
   
      public void run()
      {
         xcc.setListen(this);
      }
      public String who(){
         return "xPlugClientListen";}
      public void read(String s)
      {
         String plgs = xls.ssplit("PLUG",s);
         if (plgs.equals(""))
            {return;}
         process(plgs);
      }
   
      private void process(String s)
      {
      
         String file = xls.ssplit("FILE",s);
         String data="<PLUG>"+xls.ssplit("DATA",s)+"</PLUG>";
         System.out.println("xPluginable data:"+data);
         xPluginable xpa=xPluginFactory.create(false,file,null);
         xpa.setVars(data);
         new Thread(xpa).start();
         try{Thread.sleep(1500);}
         
            catch(Exception e){
            
            }
      
         fram = xpa.as_frame();
         try{xrs.addxRead(xpa);
            xcc.setListen(xrs);}
            catch(Exception e)
            {System.out.println("Failed at xrs.add(xpa) or xcc.setL(xrs)");}
         String calkey=""; xClientConn[] servs=null;
         try{
            calkey=xcc.getServerID();
         }
            catch(Exception ee){
               System.out.println("error in getServId()");}
         try{
            servs = servies.on();
         }
            catch(Exception eee){
               System.out.println("error in servies.on()");}
      
         xClientConn out=null;
         if (servs==null){System.out.println("servs==null");
            return;}
         for(int z=0;z<servs.length;z++)
         {
            String salkey = servs[z].getClientID();
            System.out.println("calkey:"+calkey+"::salkey:"+salkey);
            if (salkey.equals(calkey))
            {out = servs[z];}
         }
         if (out == null) {System.out.println("No server-match found");
            return;}
         xpa.setOut(out);
         fram.addWindowListener(
                                 new WindowAdapter()
                                 {
                                    public void windowClosing(WindowEvent we)
                                    {fram.setVisible(false); fram=null;
					
					}
                                 });
      
      /*end of process*/
      }
   
public boolean readAll()
{return false;}

public String[] readKeys()
{return key; 
}

   /*end*/
   }




