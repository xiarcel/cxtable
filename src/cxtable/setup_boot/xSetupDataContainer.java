package cxtable.setup_boot;
import cxtable.xLineSplit;
import java.util.Vector;

public class xSetupDataContainer{

private Vector sets = new Vector();
private xSetupContainerListener set_listen;


public xSetupDataContainer(){}

public void add(xSetupData xsd)
{
sets.addElement(xsd);
if (set_listen!=null){set_listen.update();}
}

public void setListen(xSetupContainerListener xscl)
{
set_listen=xscl;
}



public xSetupData[] get(int type)
{
 xSetupData[] all = in();
 Vector v=new Vector();
 for (int i=0; i<all.length; i++)
	{
	if (all[i].getDataType()==type)
		{
		v.addElement(all[i]);
		}
	}
 xSetupData[] to_ret = new xSetupData[v.size()];
 for (int j=0; j<to_ret.length;j++)
	{to_ret[j]=(xSetupData)v.elementAt(j);
	}
return to_ret;
}

public xSetupData[] get (int[] types)
{
 xSetupData[] all=in();
 Vector v=new Vector();
 for (int i=0; i<all.length;i++)
	{
	for (int j=0; j<types.length;j++)
		{
		if (all[i].getDataType() == types[j])
			{
			v.addElement(all[i]);
			break;
			}
		}
	}
 xSetupData[] to_ret=new xSetupData[v.size()];
 for (int k=0; k<to_ret.length;k++)
	{
	to_ret[k]=(xSetupData)v.elementAt(k);
	}
 return to_ret;
}

public xSetupData[] in()
{
 xSetupData[] to_ret=new xSetupData[sets.size()];
 for (int i=0; i<to_ret.length;i++)
	{
	to_ret[i]=(xSetupData)sets.elementAt(i);
	}
 return to_ret;
}

public boolean remove(xSetupData x)
{String[] a_one=x.getElements();

 xSetupData[] all = in();
 for (int i=0; i<all.length; i++)
	{
	if (all[i] == x) {sets.removeElement(x);
				if (set_listen != null){set_listen.update();}
			  	return true;
				}
	String[] a_two = x.getElements();
 	boolean yes = true;
	for (int j=0; j<a_one.length;j++)
		{
		try{
		    if (!a_one[j].equals(a_two[j]))
				{yes=false; break;}
			}
			catch(Exception woops) {yes=false;break;}
		}
	 if (yes)
		{sets.removeElement(x); if (set_listen != null){set_listen.update();} return true;}
	}
return false;
}

public void add(String filedata)
{
 xLineSplit xls=new xLineSplit();
 String[] data = xls.split("SETUPDATA",filedata);
 for (int i=0; i<data.length; i++)
	{
	String typ = xls.ssplit("TYPE",data[i]);
	try{
	    int tp = Integer.parseInt(typ);
  	    add(data[i],tp);
	   }
         catch(Exception woops){/*not sure what to do here*/}
	}
if (set_listen != null){set_listen.update();}
}

private void add(String s, int i)
{
 switch(i)
	{
	case 0: {xSetupData x = new xFTPServData();
		  x.setElements(s);
		  add(x);break;}
	case 1: {xSetupData x = new xHTTPUserData();
		  x.setElements(s);
		  add(x); break;}
	case 2: {xSetupData x = new xPHPServerData();
		  x.setElements(s);
		  add(x); break;}
	default: {break;}
	}
if (set_listen != null){set_listen.update();}
}

/*finis*/
}

