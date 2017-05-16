package cxtable.gui;
import cxtable.*;
import cxtable.core_comm.*;
import cxtable.plugin.*;
import cxtable.registry.*;
import cxtable.peer.xMultiBroadcast;




/*this class started out as a simple test to put a front end on the client
connection....  It has developed...and is a pretty good GUI...future plans
are to make the entire GUI-front end pluginable, and customizable...
both at an administrator level...where a template could be created so that
the p2p ends are all running the same plugins...

I imagine this might have been bloated by now....   But it is full of
examples on how to write a different one..

This is the current default-hard coded GUI for xTable
*/


   import java.awt.*;
   import java.awt.event.*;

    public class xCommShell extends Frame implements ActionListener,Runnable{
   
      xRegistry xreg; String name;xRegistry cl;
      Frame f= new Frame("xTable hard-wired communicator");
      Panel p=new Panel();Panel pp = new Panel(); Panel ppp = new Panel();
      Button post = new Button("Post");
      Button plug = new Button("Plugin");
      TextArea ta = new TextArea("",4,30,TextArea.SCROLLBARS_VERTICAL_ONLY);
      private xPanel mn = new xPanel("Main Chat","MAIN");
      private xPanel ot;
	private Panel northw = new Panel();
	private xPanel report; 
      private xReadSorter xrs;
      private xRegDisplay xregd;
      public xCommShell(xRegistry x, xRegistry c, xReadSorter xx,String n)
      {ot=new xPanel(n,"INDY");
         xrs =xx;xrs.addxRead(new xReadDeposit[]{mn,ot});
         name=n; xreg=x;cl=c;}
   	public void setReport(xPanel xp)
	{report = xp;
	 xrs.addxRead(report);
	}

      public void run()
      {	xregd=new xRegDisplay(xreg);
         xPluginable xpla = xPluginFactory.create(false,"xDicePanel",null);
         if (xpla.requireName()==true){xpla.setNam(name);}
         xpla.setOut(new xMultiBroadcast(xreg));
         cl.setDefaultListen(xrs);
	 Panel mssgy = new Panel(); 
	 mssgy.setLayout(new BorderLayout());
	 mssgy.add(new Label("Type your message here:"),BorderLayout.NORTH);
	 mssgy.add(ta,BorderLayout.SOUTH);
         ppp.setLayout(new BorderLayout());
         p.setLayout(new BorderLayout());
	 Panel icky = new Panel();
         icky.setLayout(new BorderLayout());
         icky.add(post,BorderLayout.WEST);
         icky.add(plug,BorderLayout.EAST);
	 northw.setLayout(new BorderLayout());
	 northw.add(mssgy,BorderLayout.NORTH);
	 northw.add(icky,BorderLayout.CENTER);
	 if (report != null){northw.add(report.create(),BorderLayout.SOUTH);}
	 else{
		report= new xPanel("Report from system:","REPORT");
		report.setSize(5,30,true);
		xrs.addxRead(report);
		northw.add(report.create(),BorderLayout.SOUTH);
		}
         ppp.add(northw,BorderLayout.WEST);
         ppp.add(xregd.create(),BorderLayout.CENTER);
         ppp.add(xpla.as_panel(),BorderLayout.EAST);
         new Thread(xpla).start();
         p.add(ppp,BorderLayout.NORTH);
         
         plug.addActionListener(this);
         /**/

         pp.setLayout(new GridLayout(1,2));
         pp.add(mn.create());pp.add(ot.create());
         pp.setVisible(true);
         p.add(pp,BorderLayout.SOUTH);
         xrs.addxRead(xpla);
         post.addActionListener(this);
         f.add(p);
         f.addWindowListener(
                               new WindowAdapter()
                               {
                                  public void windowClosing(WindowEvent we)
                                  {System.exit(0);}
                               });
         f.pack();
         f.show();
      }
   
      public void actionPerformed(ActionEvent ae)
      {
         if(ae.getSource() == post)
         {
            new Thread(new xPostPopup(xreg,ta.getText(),name,mn,ot)).start();
            ta.setText("");}
         if (ae.getSource() == plug)
         {new Thread(new xPlugPopup(xreg,cl,name,xrs)).start();
         }
      }
   
   /*done*/
   }

