package cxtable.core_comm;

/*
This class is what I refer to as a Vector manager.  It happens to handle
String objects.  The wrapping xSockWriter calls the add(String) method to
add String objects to the Vector.   The xWriteWorker is constantly checking 
the empty() method for a false, so it can pull the next String and pack/send it.
*/

   import java.util.Vector;

    public class xWriteQueue{
   
      private Vector q;
   
      public xWriteQueue()
      {
         q=new Vector();
      }
   
      public void add(String s)
      {
         q.addElement(s);
      }
   
   	public String dump()
   	{String s="";
   	 for (int i=0; i<q.size(); i++)
   		{s=s+(String)q.elementAt(i);}
   	 return s;
   	}
   	
      public String pop()
      {
         if (q.isEmpty() == true) {
            return ">NO<";}
         String s = (String)q.elementAt(0);
         q.removeElementAt(0);
         return s;
      }
   
      public String all()
      {
         if (q.isEmpty() == true) {
            return ">NO<";}
         String s="";
         for (int i=0; i<q.size(); i++)
         {
            s=s+(String)q.elementAt(i);
         }
         return s;
      }
   
      public boolean empty()
      {
         return q.isEmpty();
      }
   
   /*end*/
   }

