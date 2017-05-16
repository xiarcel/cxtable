package cxtable.core_comm;

import cxtable.xLineSplit;

/*This class handles two actions.  pack(String) returns a String where
each line that ends with a linebreak is placed between <LN> </LN> tags.
The unpack(String) returns a String containing it switched back.
There is a potential weakness currently, in that any text potentially
"outside" of tags would be lost...however... the pack() method
defaults to automatically placing all text between those tags..so it should
be ok..   xMessageConverter has a counter on it to measure how many
are created... 2 per xClientConn (and therefore 4 per 'peer-to-peer' connection)
are created..*/



   public class xMessageConverter{
   
      private xLineSplit xls;
	static int wawa=1;
   
      public xMessageConverter()
      {System.out.println("xMessageConverter("+wawa+") created");
	wawa++;
         xls=new xLineSplit();
      }
   
      public String unpack(String source)
      {
         String ret="";
         String[] spl = xls.split("LN",source);
         for(int i=0; i<spl.length; i++)
         {
            ret=ret+spl[i]+"\n";
         }
         return ret;
      }
   
      public String pack(String source)
      {
         String ret="";
         boolean eline=true;
         for (int i=0; i<source.length(); i++)
         {
            if (eline==true) {eline=false; ret=ret+"<LN>";}
            if (source.charAt(i) == '\n')
            {ret=ret+"</LN>"; eline=true;}
            else{ret=ret+source.charAt(i);}
         	}
         if (eline==false){
         	ret=ret+"</LN>";}

         return ret;
      }
   
      public static void main(String[] args)
      {
         xMessageConverter xmc = new xMessageConverter();
         String mssg="Hello, I am\n the walrus\n\n Who Who Cachoo\n";
         System.out.println("Original:\n"+mssg+"\n");
         String s=xmc.pack(mssg);
         System.out.println("Formatted for transfer:\n"+s);
         String t=xmc.unpack(s);
         System.out.println("Converted back for output:\n"+t+"\n\nEND");
      }
   
   /*end*/
   }
