package cxtable.core_comm;


/*this class listens for sockets from a ServSocket piece*/ 

  import java.net.*;

   public interface xServerListener{
   
      public void add(Socket s);
      public void regserver(String inet, String port);
   
   }
