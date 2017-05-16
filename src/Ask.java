import java.io.*;

 public class Ask{
static boolean DEBUG=false;

public static String ask()
{
try{
	InputStreamReader isr = new InputStreamReader(System.in);
	String s="";
	while (true)
	  {
	   int c = isr.read();
	   char cc = (char)c;
	   if ((cc == '\n')|(cc=='\r'))
 	     {
	     return s;}
	   s=s+cc;
 	  }
  }
  catch(Exception e)
	{return new String("\"error reading\"");}


   

}


public static void main(String[] args)
{

System.out.println(ask());
System.out.println(ask());

}

}


