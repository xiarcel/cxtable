package cxtable.plugin;
import cxtable.core_comm.*;
import cxtable.peer.*;



/*this interface specifies which requirements are made for the xPluginable
piece...  These methods are naturally expected by xTable..and are thus
inforced with a xPluginable interface...  */
  

 import java.awt.Panel;
   import java.awt.Frame;

   public interface xPluginable extends Runnable,xListener,xReadDeposit{
   
      public boolean setVars(String s);
      public void setOut(xOutListen x);
      public Panel as_panel();
      public Frame as_frame();
      /*public void read(String s);
	public String who();
	public boolean readAll();
	public String[] readKeys();
	*/
 
      void launch(xPlugDataListener x);
   
      public boolean requireName();
   
      public void setNam(String s);
   
   }

