package cxtable.peer;
import cxtable.core_comm.xClientConn;


/*not quite sure what this is used for...but it should come for me.. it
handles processing the new enable connections, and the boolean is
a toggle for server-client*/


   public interface xEnableListener{
   
      public void enable(xClientConn x, boolean b);
      public void appende(String s);
   
   }
