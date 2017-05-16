package cxtable;

/*this can be used to re-direct stdout/stderr to a Frame w/ a textarea..
Debugging..*/


   import java.io.*;
   import java.awt.*;
   import java.awt.event.*;

    public class TextAreaConsole extends OutputStream{
   
      private TextArea ta;
      private Panel p;
   
      public TextAreaConsole()
      {
      }
   
      public Panel create()
      {
         ta=new TextArea("",20,40);
         p=new Panel();
         p.add(ta);
         return p;
      }
   
     public Frame create_frame()
     {
     ta=new TextArea("",20,40);
     Frame f = new Frame("Output from process...");
     f.setLayout(new BorderLayout());
     f.add(ta,BorderLayout.CENTER);
     return f;
     }

      public void println(String s)
      {
         ta.append(s);
      }
   
      public void write(int i)
      {
         char c = (char)i;
         print(""+c);
      }
      public void write(float f)
      {
         print(new Float(f));
      }
      public void write(long l)
      {
         print(new Long(l));
      }
      public void write(char[] c)
      {
         print(new String(""+c));
      }
      public void write(char c)
      {
         print(new Character(c));
      }
      public void write(String s)
      {
         print(s);
      }
   
      public void write (double d)
      {
         print(new Double(d));
      }
   
      public void print(Object s)
      {	
      ta.append(s.toString());
      }
   
      public void clear()
      {
         ta.setText("");
      }
   	
   
      public static void main(String[] args)
      {
         
         TextAreaConsole tc= new TextAreaConsole();
      	OutputStream tac = (OutputStream)tc;
	
         final Frame  f=tc.create_frame();

         System.setOut(new PrintStream(tac));
         System.setErr(new PrintStream(tac));
      
         f.addWindowListener(
                               new WindowAdapter()
                               {
                                  public void windowClosing(WindowEvent we)
                                  {f.setVisible(false);}
                               });
         f.pack();
         f.setVisible(true);
         System.out.println("HEHEHEHE");
         System.out.print(".......");
         System.out.println("...!");
      }
   
   /*end*/
   }


