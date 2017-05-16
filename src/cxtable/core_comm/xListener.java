package cxtable.core_comm;


/*this interface allows any class that wishes to register with a
connection (xClientConn) to receive the messages that are read.
The who() method is for each class to specify what it is...so that
the debugging output can be more helpful..
*/  


 public interface xListener{
   
      public void read(String s);
      public String who();

      public boolean readAll();
      public String[] readKeys();

   }
