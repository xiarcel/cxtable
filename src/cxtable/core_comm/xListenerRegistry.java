package cxtable.core_comm;


/*This class contains listeners that are then cycled through when a file
is broadcast...it is a vector manager for xListeners*/


   import java.util.Vector;

    public class xListenerRegistry {
   
      private Vector list;
   
      public xListenerRegistry()
      {
         list = new Vector();
      }
   
      public void add(xListener xl)
      {
         for (int i=0; i<list.size(); i++)
         {
            xListener x_l = (xListener)list.elementAt(i);
            if (x_l == xl){
               return;}
         }
         list.addElement(xl);
      }
   
      public void remove(xListener xl)
      {
         for (int i=0; i<list.size(); i++)
         {
            xListener x_l = (xListener)list.elementAt(i);
            if (x_l==xl) {list.removeElementAt(i);
               return;}
         }
      }
   
      public xListener[] on()
      {
         xListener[] xli = new xListener[list.size()];
         for (int i=0; i<xli.length; i++)
         {
            xli[i] = (xListener)list.elementAt(i);
         }
         return xli;
      }
   
      public xListener[] removeAll()
      {
         xListener[] xli = new xListener[list.size()];
         for (int i=0; i<xli.length; i++)
         {
            xli[i] = (xListener)list.elementAt(i);
         }
      
         list.removeAllElements();
         return xli;
      
      }
   
   
   }

