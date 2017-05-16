package cxtable.plugin;


/*this class checks "plugins" by name...and adds them to the
plugin db that is used to select available plugins...run from outside the
main xTable*/


   import java.awt.*;
   import java.awt.event.*;
   import java.lang.reflect.*;
   import java.io.*;


    public class xPlugRegistration extends Thread implements ActionListener{
   
      private Frame f=new Frame("Register new xTable plugins");
      private Button reg = new Button("Check and register");
      private String fname = "PlugDB.cgt";
      private Label tf=new Label("Enter 'plugin' to check-->");
      private TextField plentry=new TextField(20);
      private String fs=System.getProperty("file.separator");
      private String curr = System.getProperty("user.dir");
      private File file;
      private TextArea report=new TextArea("",10,10,TextArea.SCROLLBARS_VERTICAL_ONLY);
   
   
      public xPlugRegistration()
      {
         file = new File(curr+fs+fname);
      }
      public xPlugRegistration(String _name)
      {
         file= new File(curr+fs+_name);
         fname=_name;
      }
   
      public void run()
      {
         Panel p=new Panel();p.setLayout(new BorderLayout());
         p.add(tf,BorderLayout.WEST);
         p.add(plentry,BorderLayout.EAST);
         f.setLayout(new BorderLayout());
         f.add(p,BorderLayout.NORTH);
         f.add(reg,BorderLayout.CENTER);
         f.add(report,BorderLayout.SOUTH);
         report.setEditable(false);
         reg.addActionListener(this);
         f.addWindowListener(
                               new WindowAdapter(){
                                  public void windowClosing(WindowEvent we)
                                  {System.exit(0);}
                               });
         f.pack();
         f.setVisible(true);
      }
   
      public void actionPerformed(ActionEvent ae)
      {
         String s= plentry.getText();
         plentry.setText("");
         if (s.equals("")){report.append("No entry!\n");
            return;}
      
         if (file.exists() == false) {report.append("Plug file does not exist!\n");
            return;}
         process(s);
      }
   
      public void process(String ss)
      {
         try{
            Class c= Class.forName(ss);
    			Object o=null;        
				try{o=c.newInstance();}
					catch(NoSuchMethodError nsme)
						{report.append("No <plug>() default\n");throw nsme;}
            xPluginable xpa = (xPluginable)o;
            report.append("Check ok!\n");
         }
				catch(NoSuchMethodError n)
					{report.append("Failed at converting to plug!!\n");return;}
            catch(Exception e){report.append("Failed at converting to pluginable!\n");

               System.out.println(e.toString());return;
               }
         try{
            FileWriter fw = new FileWriter(curr+fs+fname,true);
            fw.write("<PLG>"+ss+"</PLG>");report.append("Wrote plugin to file!\n");
         
            fw.close();
            return;
         }
            catch(Exception e){
               System.out.println(e.toString());report.append("File error\n");}
      }
   
      public static void main(String[] args)
      {System.out.println("Starting frame");
         xPlugRegistration xpr=new xPlugRegistration();
         new Thread(xpr).start();
      }
   
   }


