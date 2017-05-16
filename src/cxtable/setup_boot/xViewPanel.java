package cxtable.setup_boot;

/*this class is part of setup..views for erasing from and viewing current
connects of either FTP or User type*/


import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

 public class xViewPanel extends Thread implements ActionListener{

private int which;
private Frame p;
private Checkbox[] cb;
private Button erase = new Button("Erase all checked");
private xFTPData[] xftdat;
private xHTTPData[] xhtdat;
private xPHPData[] xpuser;
private xPHPServData[] xpserv;
private boolean w_php_counterpart=false;
private xSetupHook xsh;
private Vector phpcounterparts;

public xViewPanel(xFTPData[] xfd, xSetupHook x) throws Exception
{
xsh=x;
xftdat=xfd;
p=new Frame("Registry setup View (and erase) frame");
which=0;
cb = new Checkbox[xfd.length];
if (cb.length == 0){throw new Exception();}
p.setLayout(new GridLayout((cb.length+1),1));
for (int i=0; i<cb.length;i++)
{
cb[i]=xfd[i].getBox();
}

}

public xViewPanel(xFTPData[] xfd, xPHPServData[] xphpsd, xSetupHook x) throws Exception
{

w_php_counterpart=true;
xpserv=xphpsd;
xsh=x;
xftdat=xfd;
p=new Frame("Registry setup View (and erase) frame");
which=0;
cb = new Checkbox[(xfd.length+xpserv.length)];
phpcounterparts=new Vector();

if (cb.length == 0){throw new Exception();}
p.setLayout(new GridLayout((cb.length+1),1));
for (int i=0; i<xfd.length;i++)
{
cb[i]=xfd[i].getBox();
phpcounterparts.addElement(xfd[i]);
}

for (int j=xfd.length;j<cb.length;j++)
{
cb[j]=xpserv[(j-xfd.length)].getBox();
phpcounterparts.addElement(xpserv[(j-xfd.length)]);

}


}



public xViewPanel(xHTTPData[] xhd, xSetupHook x) throws Exception
{
xsh=x;
xhtdat=xhd;
p=new Frame("User setup View (and erase) frame");
which=1;
cb=new Checkbox[xhd.length];
if (cb.length==0) {throw new Exception();}
p.setLayout(new GridLayout((cb.length+1),1));
for (int i=0; i<cb.length;i++)
	{cb[i] = xhd[i].getBox();}
}


public xViewPanel(xHTTPData[] xhd, xPHPData[] xphpd, xSetupHook x) throws Exception
{
w_php_counterpart=true;
xpuser=xphpd;
xsh=x;
xhtdat=xhd;
p=new Frame("User setup View (and erase) frame");
which=1;
phpcounterparts=new Vector();

cb=new Checkbox[(xhd.length+xpuser.length)];
if (cb.length==0) {throw new Exception();}
p.setLayout(new GridLayout((cb.length+1),1));
for (int i=0; i<xhd.length;i++)
	{cb[i] = xhd[i].getBox();
	 phpcounterparts.addElement(xhd[i]);
	}
for (int j=xhd.length;j<cb.length;j++)
	{cb[j] = xpuser[(j-xhd.length)].getBox();
	phpcounterparts.addElement(xpuser[(j-xhd.length)]);
	}

}

public void run()
{
for (int i=0; i<cb.length;i++)
	{
	p.add(cb[i]);
	}

p.add(erase);
erase.addActionListener(this);
p.addWindowListener(new WindowAdapter()
	{public void windowClosing(WindowEvent we){kill();}
	});
p.pack();
p.setVisible(true);

}

public void kill()
{
p.setVisible(false); p=null;
}

public int getWhich()
{return which;}


public void actionPerformed(ActionEvent ae)
{
if (w_php_counterpart)
	{alternate();kill();return;}

Vector v= new Vector();

 for (int i=0; i<cb.length;i++)
	{
	if (cb[i].getState() == true)
		{continue;}

	if (which==0){v.addElement(xftdat[i]);}
	if (which==1){v.addElement(xhtdat[i]);}

		
	}
 if (which==0)
	{xftdat=null; xftdat=new xFTPData[v.size()];
	 for (int i=0; i<xftdat.length;i++)
		{xftdat[i]=(xFTPData)v.elementAt(i);}
	xsh.setFTP(xftdat);
	}
    else
	{
	xhtdat=null; xhtdat=new xHTTPData[v.size()];
	 for (int i=0; i<xhtdat.length;i++)
		{xhtdat[i]=(xHTTPData)v.elementAt(i);}
	xsh.setHTTP(xhtdat);
	}

 
xsh.update();
kill();

}

private void alternate()
{
Vector one=new Vector();
Vector two=new Vector();
for (int i=0; i<phpcounterparts.size();i++)
	{
	Object o =phpcounterparts.elementAt(i);
	if (which==0)
		{
		try{xFTPData x =(xFTPData)o;
		one.addElement(x);}
		catch(Exception e)
		{two.addElement(o);}
		}
	if (which==1)
		{
		try{xHTTPData x =(xHTTPData)o;
		one.addElement(x);
		}
		catch(Exception e)
		{two.addElement(o);}
		}
	}

if (which==0)
	{
	xFTPData[] xfnewdat= new xFTPData[one.size()];
	xPHPServData[] xpsnewdat = new xPHPServData[two.size()];
	for (int k=0; k<xfnewdat.length;k++)
		{
		xfnewdat[k]=(xFTPData)one.elementAt(k);
		}
	for (int l=0; l<xpsnewdat.length;l++)
		{
		xpsnewdat[l]=(xPHPServData)two.elementAt(l);
		}
	xsh.setFTP(xfnewdat);
	xsh.setPHPServ(xpsnewdat);
	return;
	}

if (which==1)
	{
	xHTTPData[] xhnewdat= new xHTTPData[one.size()];
	xPHPData[] xpnewdat=new xPHPData[two.size()];
	for (int m=0; m<xhnewdat.length; m++)
		{
		xhnewdat[m]=(xHTTPData)one.elementAt(m);
		}
	for (int n=0; n<xpnewdat.length;n++)
		{
		xpnewdat[n]=(xPHPData)two.elementAt(n);
		}
	xsh.setHTTP(xhnewdat);
	xsh.setPHP(xpnewdat);
	}


/*end of new alternate a_performed*/


}


/*finis*/

}
