package cxtable.setup_boot;
import cxtable.xLineSplit;



/*a data storage object that creates the info from a single string..
This is used also to store the original string...so that on re-saves.... it has
the data... it has a Checkbox as well that it provides for choosing to remove
from the panel that provides that choice in setup*/


import java.awt.Checkbox;

 public class xPHPServData{

private String phpbase,servername,savedata;
private Checkbox cb;


public xPHPServData(String s) throws Exception
{savedata=s;
 xLineSplit xls = new xLineSplit();
 phpbase=xls.ssplit("PHPBASE",s);
 servername=xls.ssplit("PHPSERV",s);

 if (phpbase.equals("")|servername.equals(""))
		{throw new Exception();}
 cb = new Checkbox(new String("PHP::"+phpbase+"??:"+servername));

}

public Checkbox getBox() {return cb;}

public String getPHPBase(){return phpbase;}
public String getPHPServName(){return servername;}

public String getSave() {return savedata;}
}

