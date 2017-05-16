package cxtable.setup_boot;
import cxtable.registry.xRegistryServer;


/*this class is specifically for the Registry...it pops up before the
xRegistry is loaded, and then it spawns the xRegistryServer thread...
This is done differently than in xMobileServer... where it is booted
after the server-side connect is made...*/


 public class xBootRegistry extends Thread implements xBootListener, xSetupBootListener{

private String s="";
private boolean as_php=false;

public xBootRegistry(String f)
{
s=f;
}

public xBootRegistry()
{
s="";
}

public void run()
{

 if (s.equals("")==true)
	{
	new Thread(new xSetupBootPopup(true,this)).start();
	}
	else
	  {new Thread(new xSetupBootPopup(s,true,this)).start();}

    

}

public void boot(xSetupData x)
{
if (x.getDataType()==0)
	{
	String[] ftpdata= x.getElements();
	if (ftpdata.length < 5)
		{System.out.println("Bad xRegistry Data");return;}
	new Thread(new xRegistryServer(ftpdata[0],ftpdata[1],ftpdata[2],ftpdata[3],ftpdata[4],true)).start();
	}
if (x.getDataType()==2)
	{
	String[] phpdata=x.getElements();
	if (phpdata.length != 2)
		{
		System.out.println("Bad xRegistry data-php");return;}
	new Thread(new xRegistryServer(phpdata[1],phpdata[0],true)).start();
	}

}


public void boot(xFTPData x)
{
try{ new Thread(new xRegistryServer(x.getSite(),x.getUser(),x.getPass(),x.getDir(),x.getFile(),true)).start();
   }
   catch(Exception e)
	{System.out.println("Failed at reading xRegistry Data");}
}

public void boot(xHTTPData x)
{
}

public static void main(String[] args)
{
 if (args.length > 0)
	{
	new Thread(new xBootRegistry(args[0])).start();
	}
 else
	{new Thread(new xBootRegistry()).start();}
}

}
