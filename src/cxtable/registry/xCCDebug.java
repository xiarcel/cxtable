package cxtable.registry;

import cxtable.core_comm.*;


/*quite frankly, I am not sure what this was for... although it is used,
I still believe, in the xEnable thing to pipe reads from one xClientConn
to the other when the xRegistryServer is functioning as relay*/
  

  public class xCCDebug implements xListener{
   
      private xClientConn out;
      private String name;
      private boolean chat=false;
   
      public xCCDebug(String nm, xClientConn o)
      {
         name=nm+":"; out=o;
      }
   
      public String who(){
         return name;}

      public boolean readAll()
	{return true;}
 
      public String[] readKeys()
	{return new String[0];}
   
      public void read(String s)
      {
         if(chat==true){System.out.println(name+s);}
         out.send(s);
      }
   
   }
