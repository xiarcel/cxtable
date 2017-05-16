package cxtable;
/*this class redirects standard output to a logfile*/


   import java.io.*;
   import java.util.Date;
   import java.text.SimpleDateFormat;
   import java.awt.Frame;
   import java.awt.event.*;

    public class xReDirect extends Thread{
   
      private File logfile; private Date time;
      private SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss zzz");
      private File logpath;
      private String filepath= new String(System.getProperty("user.dir")+System.getProperty("file.separator"));
      private String file;
      private File check_exist;
      private File fpath;
      private boolean ex;
      private String wapa,logstring;
	private boolean log_to_tac=false;
	private TextAreaConsole tac;
   
      public xReDirect(String log_to)
      {
        System.out.println("xReDirect: 11-7-01");
         wapa = new String(filepath+"CXTLogs");
         fpath = new File(wapa);
         logstring = new String(wapa+System.getProperty("file.separator")+log_to);
         logfile = new File(logstring);
         check_exist = new File(filepath+"xconredir.cgt");
      
      }
       public xReDirect()
     {
     System.out.println("xReDirect: 11-7-01");
     log_to_tac=true;
	tac=new TextAreaConsole();
     }
   

      public void run()
      {System.out.println("xReDirect run()");

	if (log_to_tac == true)
	      {redir_tac();return;}

         if (check_exist.exists()!=true)
	{System.out.println("Logfile not present..");return;}
	else
         {System.out.println("Logfile controller present");
            ex = fpath.mkdirs();
            System.out.println("Required new directory:"+ex);
            System.out.println("Redirecting console to:\n"+logstring+"\n...please stand by...");
            time = new Date(); String tme = new String(fmt.format(time)+"\n");
            try{FileWriter f_w=new FileWriter(logstring,true);
               f_w.write("\n\r=======================\n\r"+tme+"\r===========================\n");
               f_w.close();
            }
               catch(Exception e){
                  System.out.println("Could not create:"+logfile);}
            try{System.setOut(new PrintStream(new FileOutputStream(logstring,true)));
            } 
               catch (Exception e){
                  System.out.println("failed redirect");
                  System.out.println(e.toString());}
         
            System.out.println("Programmer info/debug/console follows:");
            System.out.println("--------------------------------------\n");
         }
      }

public void redir_tac()
{
 OutputStream ops = (OutputStream)tac;
 final Frame f = tac.create_frame();

 System.setOut(new PrintStream(ops));
 f.setLocation(150,150);
 f.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent we)
			{f.setVisible(false);}
		});
 time = new Date(); String tme = new String(fmt.format(time)+"\n");
System.out.println("\n\r=======================\n\r"+tme+"\r===========================\n");
System.out.println("Programmer info/debug/console follows:");
            System.out.println("--------------------------------------\n");
f.pack(); f.setVisible(true);
}



   
   }

