package cxtable.gui;
import cxtable.core_comm.*;


/*this class is for the text-area that receives messages, posts..etc..
from a particular location..includes a button for refresh and a label to put 
on top*/


   import java.awt.*;
   import java.awt.event.*;

    public class xPanel extends Panel implements ActionListener, xReadDeposit{
      int fir=9; int sec=38;
      boolean horiz=false;
      private TextArea ta;
      private boolean caching = true;
      private String name,key;
      private Button clr = new Button("Clear/Reset");
      private Panel p,top;
      private String cache = "";
   
      public xPanel(String n, String k)
      {
         name=n;
         key=k;
      }
      public void setSize(int h, int w)
      {fir=h; sec=w;}
      public void setSize(int h,int w,boolean b)
      {horiz=b; fir=h; sec=w;}
   
      public Panel create()
      { 
         if (horiz==true)
         {ta=new TextArea("",fir,sec,TextArea.SCROLLBARS_BOTH);}	
         else{ta=new TextArea("",fir,sec,TextArea.SCROLLBARS_VERTICAL_ONLY);}
         p=new Panel(new BorderLayout());
         top=new Panel(new BorderLayout());
      
         clr.addActionListener(this);
         top.add(new Label(name), BorderLayout.WEST);
         top.add(clr,BorderLayout.EAST);
      
         p.add(top,BorderLayout.NORTH);
         p.add(ta,BorderLayout.SOUTH);
      
         p.setVisible(true);
         if (cache.equals("")==false)
         {
            post(cache);
            cache="";
         }
         caching = false;
         return p;
      }
   
      public void setKey(String k)
      {key=k;}
   
      public void append(String[] s)
      {
         for(int i=0; i<s.length; i++)
         {	
         
            append(s[i]);
         
         }
      }
      public void post(String s)
      {append(s+"\n");}
   
      public void actionPerformed(ActionEvent ae)
      {
         if (ae.getSource() == clr){ta.setText("");}
      }
   
      public String getKey(){
         return key;}
   
      private void append(String s)
      {
         if(caching == true)
         {cache=cache+s+"\n";}
         else{ta.append(s);}
      }
   
   
      private void save()
      {
         caching=true;
      /*save*/
         ta.append(cache);
         caching=false;
         cache="";
      }
   
      public int get_length()
      {
         return ta.getText().length();}
   
   /*end*/
   }
