package cxtable.gui;

import cxtable.core_comm.*;


/*this processes a message written in the textarea provided
with the current (default) xCommShell

...and offers the user the chance
to send it to main...or to individual users individual spots...
Works well with existing comm-shell*/


   import java.awt.event.*;
   import java.awt.*;
   import java.util.Date;
   import java.util.Vector;
   import java.text.SimpleDateFormat;

    public class xPostPopup extends Frame implements ActionListener,Runnable{
   
   
      SimpleDateFormat sdf= new SimpleDateFormat("::<>HH:mm:ss zzz<>:->>");
      Date time;
      String timestamp;
      private Frame f = new Frame("Process this post...");
      private Panel one=new Panel();
      private Panel two=new Panel();
      private Panel three=new Panel();
      private Button ok = new Button("Proceed with post");
      private Button abort = new Button("Abort this post");
      private Checkbox[] indies;
      private String[] indy;
      private Checkbox other,mn;
      private xRegistry postreggy;
      private xClientConn[] who_on;
      private CheckboxGroup cbg;
      private String post,label;
      private xReadDeposit solo,chat;
   	
      public xPostPopup(xRegistry x,String p,String l,xReadDeposit xrmain,xReadDeposit xrdep)
      {	solo=xrdep;chat=xrmain;
         label=l;
         postreggy=x;
         post=p;
      }
   
      public void run()
      {
         time = new Date();
         timestamp= sdf.format(time);
      
         f.addWindowListener(
                               new WindowAdapter()
                               {
                                  public void windowClosing(WindowEvent we)
                                  {end();}
                               });
	f.setLocation(200,200);
      
         who_on = postreggy.on();
         indy = new String[who_on.length];
         indies = new Checkbox[who_on.length];
         cbg=new CheckboxGroup();
      
         mn=new Checkbox("Main Window",cbg,true);
         other=new Checkbox("Others",cbg,false);
      
         mn.addItemListener(
                              new ItemListener(){
                                 public void itemStateChanged(ItemEvent ie)
                                 {
                                    if (mn.getState() == true)
                                    {other.setState(false);
                                       dechoose();}
                                 }});
      
         other.addItemListener(
                                 new ItemListener(){
                                    public void itemStateChanged(ItemEvent ie)
                                    {
                                       if (other.getState() == true)
                                       {enab(true); mn.setState(false);
                                       }
                                       if (other.getState() == false)
                                       {enab(false); dechoose();									
                                          mn.setState(true);
                                       }
                                    }});
      
         for (int i=0; i<who_on.length; i++)
         {
            indy[i] = who_on[i].get_Name();
            indies[i] = new Checkbox(indy[i]);
         }
         if (post.length()<65) {one.add(new Label(post+"..."));}
         else{one.add(new Label(post.substring(0,62)+"..."));}
      
         int lay = indies.length+2;
         two.setLayout(new GridLayout(lay,1));
         two.add(mn);two.add(other);
         for (int j=0; j<indies.length; j++)
         {
            two.add(indies[j]);
         }
         mn.setState(true);dechoose();
      
         three.setLayout(new BorderLayout());
         three.add(ok,BorderLayout.WEST);
         three.add(abort,BorderLayout.EAST);
         ok.addActionListener(this); abort.addActionListener(this);
         f.setLayout(new BorderLayout());
         f.add(one,BorderLayout.NORTH);
         f.add(two,BorderLayout.CENTER);
         f.add(three,BorderLayout.SOUTH);
         f.pack();
         f.setVisible(true);
      }
   
      public void enab(boolean bo)
      {
         for (int x=0; x<indies.length; x++)
         {
            indies[x].setEnabled(bo);
         }
      }
   
      public void dechoose()
      {
         for (int x=0; x<indies.length; x++)
         {
            indies[x].setState(false);
            indies[x].setEnabled(false);}
      }
      public void end()
      {f.setVisible(false); f=null;who_on=null;}
   
   
      public void actionPerformed(ActionEvent ae)
      {
         if (ae.getSource() == abort) {end();}
         if (ae.getSource() == ok) {complete();}
      }
   
      public void complete()
      {
         String to_convert = timestamp+"<>"+label+"<>:\n";
         if (mn.getState()==true)
         {
            to_convert=to_convert+"\t"+post+"\n";
				String to_send = "<MSSG><MAIN>"+to_convert+"</MAIN></MSSG>";
            for (int v=0; v<who_on.length; v++)
            {	System.out.println(who_on[v].get_Name()+":inVector");
               who_on[v].send(to_send);
            }
            chat.append(new String[]{to_convert});
            end();
         }
         if (other.getState() == true)
         {
            to_convert=to_convert+"[[TO:";
            Vector chosen = new Vector();
            for (int j=0; j<indies.length;j++)
            { String ss="";
               if (j !=0) {ss=",";}
               if(indies[j].getState()==true) 
               {chosen.addElement(who_on[j]);
                  to_convert=to_convert+ss+indy[j];}
            }
         
            if (chosen.size() < 1)
            {end();}
            to_convert=to_convert+"]]\n\t"+post+"\n";
				String to_send ="<MSSG><INDY>"+to_convert+"</INDY></MSSG>";
            for (int z=0; z<chosen.size(); z++)
            {
               xClientConn xx = (xClientConn)chosen.elementAt(z);
               xx.send(to_send);
            }
            solo.append(new String[]{to_convert});
            end();
         }
      /*end finish*/
      }
   
   
   /*end*/	
   
   }
