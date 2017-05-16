package cxtable.core_comm;


/*this class can be added as an xListener that then spews out everything
it reads..*/

    public class xPrinter implements xListener{
   
      String label;
	private final static String[] key=new String[0];
   	private final String _who = "xPrinter";
   
      public xPrinter(String ss)
      {
         label=ss;
      }
   	public String who(){return _who;}
      		
      public void read(String s)
      {
         System.out.println(label+"\n"+s);
      }
   
public boolean readAll()
{return true;}

public String[] readKeys()
{return key;}

   }

