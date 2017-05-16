package cxtable.plugin;
import cxtable.peer.xPlugDataListener;

/*this factory creates plugins from info about them.. and launches their
launcher and provides it with the info on where to send its launching info
when done*/
 
   public class xPluginFactory{
   
      public static xPluginable create(boolean init, String cl, xPlugDataListener xpdl)
      {
         try{
            Class c = Class.forName(cl);
            Object o= c.newInstance();
            xPluginable xpa = (xPluginable)o;
            if ((init == true) & (xpdl != null)){
               try{xpa.launch(xpdl);}
                  catch(NullPointerException npe)
                  {System.out.println("Failed at dispatch");}
            }
            return xpa;
         }
            catch(Exception e){
               System.out.println("Failed at plugin...");
               System.out.println(e.toString());
            }
         return null;
      }
      public static void main(String[] args)
      {	String pt = System.getProperty("user.dir");
         String fs = System.getProperty("file.separator");
      
         if (args.length < 1){System.out.println("No args");System.exit(0);}
         System.setProperty("user.dir",pt+fs+"Plugins"+fs);
         System.out.println(System.getProperty("user.dir"));
         xPluginable xpa = create(false,args[0],new xPDLTest());
         if (xpa!=null){System.out.println("Created xpa");}
         else{System.out.println("Failed.");}
      }
   
   
   /*end*/
   }
