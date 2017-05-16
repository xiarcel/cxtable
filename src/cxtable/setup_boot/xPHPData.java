package cxtable.setup_boot;
import java.awt.Checkbox;
import cxtable.xLineSplit;


/*a data storage object that creates the info from a single string..
This is used also to store the original string...so that on re-saves.... it has
the data... it has a Checkbox as well that it provides for choosing to remove
from the panel that provides that choice in setup*/

 public class xPHPData{

private String user,httpsite,savedata,php_name;
private Checkbox cb;



public xPHPData(String s) throws Exception
{
savedata=s;
xLineSplit xls=new xLineSplit();
user = xls.ssplit("NAME",s);
httpsite = xls.ssplit("HTTP",s);
php_name=xls.ssplit("PNAME",s);

if (user.equals("") | httpsite.equals("")|php_name.equals(""))
	{throw new Exception();}
cb=new Checkbox(new String("PHP::"+user+"::"+httpsite));

}

public Checkbox getBox(){return cb;}
public String getUser(){return user;}
public String getHTTP(){return httpsite;}
public String getPHPName(){return php_name;}

public String getSave(){return savedata;}


}
