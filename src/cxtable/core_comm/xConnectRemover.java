package cxtable.core_comm;


/*this is a threaded remover that handles checking the new
xClientConn against the existing xConnectPool... and pulling
the xConnect object out on a match*/

 public class xConnectRemover extends Thread{

private xClientConn xcc; private xConnectPool xcp;
public xConnectRemover(xConnectPool x, xClientConn xc)
{
 xcp=x; xcc=xc;
}

public void run()
{
 String sid = xcc.getServerID();
 xConnect[] xcon = xcp.on_();
 for (int i=0; i<xcon.length; i++)
	{
	 if (xcon[i].get_ServReg().equals(sid)==true)
		{xcon[i].kill(xcc);}
	}
}

/*done*/
}

	
