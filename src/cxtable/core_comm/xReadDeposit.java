package cxtable.core_comm;


/*classes that implement this get directed to append messages..
first..the xReadSorter checks if it is intended for that particular xRD..and
then calls its append method*/
 
  public interface xReadDeposit{
   
      public void append(String[] s);
      public String getKey();
   
      public void setKey(String s);
   
   }
