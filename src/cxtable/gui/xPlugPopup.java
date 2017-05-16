package cxtable.gui;
import cxtable.plugin.*;
import cxtable.core_comm.*;
import cxtable.*;
import cxtable.peer.*;



/*this class provides a user with a list of the plugins available..and
who is online to use them with... and the user chooses and then the
plug is launched*/
 

  import java.awt.*;
   import java.awt.event.*;
   import java.io.*;


    public class xPlugPopup extends Thread implements ActionListener, xPlugDataListener{
   
      private xRegistry serv,clien;	xClientConn server_side=null;xClientConn client_side=null;
      private String name;String plugin_choice="";
   	/*frame for plugin*/
      private Frame fx;
      private xReadSorter xrs;private xPluginable xpa;
      private Frame frame=new Frame("Plugin Popup");
      private File pl;
      private String[] plgs;
      private Button plugit = new Button("Submit plugin info");
      private xClientConn[] xcs;
      private Checkbox[] ckplugs,peoplugs;
      private CheckboxGroup ckpg= new CheckboxGroup();
      private CheckboxGroup ppg = new CheckboxGroup();
      private xLineSplit xls=new xLineSplit();
      private String file = "PlugDB.cgt";
      private String f= System.getProperty("file.separator");
      private String path = System.getProperty("user.dir");
      public xPlugPopup(xRegistry s, xRegistry c, String n, xReadSorter x)
      {
         serv=s; clien=c; name=n; xrs=x;
         pl=new File(path+f+file);
      
      }
   
      public xPlugPopup(xRegistry s, xRegistry c, String n, xReadSorter x, String fil)
      {
         serv=s; clien=c; name=n; xrs=x;
         file=fil;
         pl=new File(path+f+file);
      }
   
      public void run()
      {
      /*try to read file*/
         try{FileReader fr = new FileReader(pl);
            BufferedReader br= new BufferedReader(fr);
            String total="";
            String inc;
            while ((inc=br.readLine())!=null)
            {total=total+inc;}
            plgs = xls.split("PLG",total);
            if (plgs.length < 1)
            {	System.out.println("plgs failed");
               return;}
         }
            catch (Exception e){
               return;}
         xcs = serv.on();
         if (xcs.length < 1){
            return;}
         peoplugs = new Checkbox[xcs.length];
         for (int j=0; j<xcs.length; j++)
         {peoplugs[j]=new Checkbox(xcs[j].get_Name(),ppg,false);}
         ckplugs=new Checkbox[plgs.length];
         for (int i=0; i<plgs.length;i++)
         {
            ckplugs[i]=new Checkbox(plgs[i],ckpg,false);
         }
         Panel one = new Panel();
         one.setLayout(new GridLayout((peoplugs.length+1),1));
         one.add(new Label("People"));
         Panel two = new Panel();
         two.setLayout(new GridLayout((ckplugs.length+1),1));
         two.add(new Label("Plugins"));
      
         Panel three = new Panel();
         three.setLayout(new BorderLayout());
         for(int z=0; z<peoplugs.length;z++)
         {
            one.add(peoplugs[z]);
         }
         for (int w=0; w<ckplugs.length;w++)
         {
            two.add(ckplugs[w]);
         }
         three.add(one,BorderLayout.WEST);
         three.add(two,BorderLayout.EAST);
         frame.setLayout(new BorderLayout());
         frame.add(new Label("Choose for the options below...."),BorderLayout.NORTH);
         frame.add(three,BorderLayout.CENTER);
         frame.add(plugit,BorderLayout.SOUTH);
         frame.addWindowListener(
                                 new WindowAdapter()
                                 {
                                    public void windowClosing(WindowEvent we)
                                    {frame.setVisible(false);}});
         plugit.addActionListener(this);
         frame.pack();
         frame.setVisible(true);
      }
   
   
      public void actionPerformed(ActionEvent ae)
      
      {
         for (int j=0;j<peoplugs.length;j++)
         {
            if (peoplugs[j].getState()==true)
            {server_side = xcs[j];}
         }
         if (server_side==null) {frame.setVisible(false);frame=null;
            System.out.println("No server_side");
            return;}
         for (int k=0; k<ckplugs.length;k++)
         {
            if (ckplugs[k].getState()==true)
            {plugin_choice = plgs[k];}
         }
      
         if (plugin_choice.equals(""))
         {frame.setVisible(false);frame=null;
            System.out.println("No plugin_choice");
            return;}
      
         xClientConn[] clients = clien.on();	
      	System.out.println("Evaluating clients with length:"+clients.length);
         for(int i=0; i<clients.length;i++)
         {
            System.out.println("clients:("+clients[i].getServerID()+") vs server:("+server_side.getClientID()+")");
         
            if (clients[i].getServerID().equals(server_side.getClientID()))
            {
               client_side=clients[i];System.out.println("Found client match");
            }
         }
         if (client_side==null) {
            frame.setVisible(false);frame=null;System.out.println("No client match found");
            return;}
      
      	/*dispatch stuff*/
      
         xpa = xPluginFactory.create(true,plugin_choice,this);
         System.out.println("Setting plug frame to null");
         frame.setVisible(false);
         frame=null;
      }
   
      public void reglaunch(String s)
      {
         String mssg= "<PLUG><NAME>"+server_side.get_Name()+"</NAME>"+s+"</PLUG>";
         xpa.setVars(mssg);
         String omssg="<PLUG><FILE>"+plugin_choice+"</FILE><DATA><NAME>"+name+"</NAME><PASSIVE></PASSIVE>"+s+"</DATA></PLUG>";
         server_side.send(omssg);
         xpa.setOut(server_side);
         client_side.setListen(xpa);
         new Thread(xpa).start();
         xrs.addxRead(xpa);
         System.out.println("Added to xrs, whose keys are:\n");xrs.dump();
      
         /*client_side.setListen(xrs);*/
      
         try{Thread.sleep(1500);}
            catch(Exception e){
            }
         fx = xpa.as_frame();
         fx.addWindowListener(
                                new WindowAdapter(){
                                   public void windowClosing(WindowEvent we)
                                   {fx.setVisible(false); fx=null;}
                                });
         System.out.println(s);
      }
   
   
   
   /*end*/
   }



