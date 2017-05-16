package cxtable.core_comm;
import cxtable.xLineSplit;


/*this class is sent messages...  polls the xReadDeposits...and
sends the message to the appropriate one*/
  

 import java.util.Vector;


    public class xReadSorter implements xListener{
   
      private xLineSplit xls= new xLineSplit();
      private Vector xReads = new Vector();
      private final String _who = "xReadSorter";
   	private boolean debug=false;
	private static final String[] key =new String[]{"<MSSG>"};
   
      public xReadSorter(xReadDeposit[] x)
      {
         for (int i=0; i<x.length; i++)
         {xReads.addElement(x[i]);}
      }
   
      public xReadSorter()
      { /**/ }
      public String who(){
         return _who;}
      public void addxRead(xReadDeposit xr)
      {
         xReads.addElement(xr);
      }
   
      public void addxRead(xReadDeposit[] xr)
      {
         for (int i=0; i<xr.length; i++)
         {xReads.addElement(xr[i]);}
      }
   
   
      public void read(String s)
      {
         if (debug==true){System.out.println("Reading:"+s);}
         xReadDeposit[] xrds = xreads();
         String[] mssgs = xls.split("MSSG",s);
      	if(mssgs.length < 1){return;}
         for (int x=0; x<mssgs.length; x++)
         {if(debug==true){System.out.println("Mesage:"+mssgs[x]);}
            for (int y=0; y<xrds.length; y++)
            {
               String key = xrds[y].getKey();
               
               if(debug == true){System.out.println("Examining read_key:"+key);}
               String[] whats = xls.split(key, mssgs[x]);
               if (whats.length > 0) {xrds[y].append(whats);}
            }
         }
      }
      public void dump()
      {
         xReadDeposit[] nw = xreads();
         for (int i=0;i<nw.length;i++)
         {
            System.out.println("key:"+nw[i].getKey());
         }
      }
      private xReadDeposit[] xreads(){
      
         xReadDeposit[] xx = new xReadDeposit[xReads.size()];
         for (int i=0; i<xx.length; i++)
         {
            xx[i] = (xReadDeposit)xReads.elementAt(i);
         }
      
         return xx;
      }
	public boolean remove(xReadDeposit xread)
	{
	 xReadDeposit[] xx= xreads();
	 for (int i=0; i<xx.length;i++)
		{
		if (xx[i] == xread)
		{xReads.removeElementAt(i);return true;}
		}
	return false;
	}
public boolean readAll()
{return false;}

public String[] readKeys()
{return key;}

   
   /*end*/
   }
