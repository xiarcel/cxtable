package cxtable.registry;

import cxtable.core_comm.xRegistry;
import cxtable.core_comm.xClientConn;
import cxtable.*;
import cxtable.gui.ImageTest;





/*currently part of the xCommShell that is the default GUI...
North is the logo...south is the xPanel with current users on*/


 import java.awt.*;

    public class xRegDisplay implements xTimed{
   
      private Panel p;
      private TextArea ta = new TextArea("",9,17,TextArea.SCROLLBARS_VERTICAL_ONLY);
      private xRegistry servers;
   
      public xRegDisplay(xRegistry r)
      {
         servers=r;
      }
   
      public void go(){
         update();}
   
      public Panel create()
      {
         p = new Panel(); p.setLayout(new BorderLayout());
         Panel pp = new Panel();
         pp.setLayout(new BorderLayout());
      
         Panel it = new ImageTest("cxtlogo.jpg");
      	

         pp.add(it,BorderLayout.CENTER);
         pp.repaint();
         Panel ppp = new Panel();
         ppp.setLayout(new BorderLayout());
      
         p.add(pp,BorderLayout.CENTER);
         ppp.add(new Label("Users(other) on"),BorderLayout.NORTH);
         ppp.add(ta,BorderLayout.SOUTH);
         p.add(ppp,BorderLayout.SOUTH);
         p.setVisible(true);
         new Thread(new xTimer(2500,this,true)).start();
         p.repaint();
         return p;
      }
   
      public void update()
      {
         ta.setText("");
         xClientConn[] xcc=servers.on();
         for (int i=0; i<xcc.length; i++)
         {
            if (xcc[i] !=null)
            {ta.append(xcc[i].get_Name()+"\n");}
         }
      }
   
   /*end*/
   }
