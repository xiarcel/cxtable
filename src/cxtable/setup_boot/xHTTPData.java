package cxtable.setup_boot;
import cxtable.xLineSplit;



import java.awt.Checkbox;

/*a data storage object that creates the info from a single string..
This is used also to store the original string...so that on re-saves.... it has
the data... it has a Checkbox as well that it provides for choosing to remove
from the panel that provides that choice in setup*/

 public class xHTTPData{

private String user,httpsite,savedata;
private Checkbox cb;



public xHTTPData(String s) throws Exception
{
savedata=s;
xLineSplit xls=new xLineSplit();
user = xls.ssplit("NAME",s);
httpsite = xls.ssplit("HTTP",s);

if (user.equals("") | httpsite.equals(""))
	{throw new Exception();}
cb=new Checkbox(new String(user+"::"+httpsite));

}

public Checkbox getBox(){return cb;}
public String getUser(){return user;}
public String getHTTP(){return httpsite;}
public String getSave(){return savedata;}


}
