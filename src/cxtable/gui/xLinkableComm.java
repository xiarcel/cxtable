package cxtable.gui;
import cxtable.*;
import cxtable.core_comm.*;
import cxtable.plugin.*;
import cxtable.peer.*;


/*this class has the purpose of providing all of the methods
needed by either -1- a dynamically created CommShell.. or -2- a 
.java class written to be a Comm Shell as a plugin...  there are a 
few more steps left for this...*/


import java.awt.Button;
import java.awt.TextArea;

 public class xLinkableComm{

private xRegistry servs,clients;
private xReadSorter sort;
private String name;
private xPanel one,two;
private TextArea ta;


public xLinkableComm(xRegistry s, xRegistry c, xReadSorter r, String n)
{
name=n; sort=r; servs=s; clients=c;
clients.setDefaultListen(sort);
}

public boolean link_reader(Object o)
{
 try{
     xReadDeposit xrd = (xReadDeposit)o;
     sort.addxRead(xrd);
     return true;}
    catch(Exception e)
	{}
return false;
}

public xPluginable create_plugin(String pn, boolean serv_listen, boolean clie_listen) throws Exception
{
 xPluginable xpa = xPluginFactory.create(false,pn,null);
 if (xpa.requireName()==true) {xpa.setNam(name);}
 if (serv_listen==true) {xpa.setOut(new xMultiBroadcast(servs));}
 if (clie_listen ==true){sort.addxRead(xpa);}
 return xpa;
}




public xPluginable create_plugin(String pn, boolean s_l, boolean c_l, xPlugDataListener xpdl, boolean launch) throws Exception
{
 xPluginable xpa= xPluginFactory.create(launch,pn,xpdl);
 if (xpa.requireName()==true){xpa.setNam(name);}
 if (s_l==true){xpa.setOut(new xMultiBroadcast(servs));}
 if (c_l==true){sort.addxRead(xpa);}
 return xpa;
}

public Button make_plug_button(String n)
{
 Button b=new Button(n);
 setPlugButton(b);
 return b;
}

public Button make_post_button(String n)
{
 Button p=new Button(n);
 setPostButton(p);
 return p;
}

public Button make_multi_button(String n)
{
 Button m=new Button(n);
 setMultiButton(m);
 /*empty setMB()*/
 return m;
}

public Button make_process_button(String n)
{
 Button pb=new Button(n);
 setProcessButton(pb);
 /*empty sPB()*/
 return pb;
}

private void setMultiButton(Button b)
{
/*empty*/
}

private void setProcessButton(Button b)
{
/*empty*/
}


private void setPlugButton(Button b)
{
xPlugButtonListener xpbl = new xPlugButtonListener(b,name,servs,clients,sort);
}

private void setPostButton(Button b)
{
if ((one!=null)&(two!=null)&(ta!=null))
{xPostButtonListener xpbl =new xPostButtonListener(b,name,servs,one,two,ta);}
else {System.out.println("Debug:setPostButton requires that setMTA(TextArea) and setPanel(xPanel,boolean) {twice} have been set");
}

}


public void setPanel(xPanel p, boolean toggle)
{
if (toggle==true){one=p;}
else{two=p;}
}

public void setMTA(TextArea t)
{
ta=t;
}

/*possibly other methods*/

}

