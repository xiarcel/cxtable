package cxtable.setup_boot;
import cxtable.xLineSplit;

/*a data storage object that creates the info from a single string..
This is used also to store the original string...so that on re-saves.... it has
the data... it has a Checkbox as well that it provides for choosing to remove
from the panel that provides that choice in setup*/


import java.awt.Checkbox;

 public class xFTPData{

private String ftpsite,ftpuser,ftppass,ftpdir,ftpfile,savedata;
private Checkbox cb;


public xFTPData(String s) throws Exception
{savedata=s;
 xLineSplit xls = new xLineSplit();
 ftpsite = xls.ssplit("FTPSITE",s);
 ftpuser = xls.ssplit("FTPUSER",s);
 ftppass = xls.ssplit("FTPPASS",s);
 ftpdir = xls.ssplit("FTPDIR",s);
 ftpfile = xls.ssplit("FTPFILE",s);
 if (ftpsite.equals("") | ftpuser.equals("") | ftppass.equals("") | ftpdir.equals("") | ftpfile.equals(""))
		{throw new Exception();}
 cb = new Checkbox(new String(ftpsite+"/"+ftpuser+"/"+ftpdir+"/"+ftpfile));

}

public Checkbox getBox() {return cb;}
public String getSite() {return ftpsite;}
public String getUser() {return ftpuser;}
public String getPass() {return ftppass;}
public String getDir() {return ftpdir;}
public String getFile() {return ftpfile;}
public String getSave() {return savedata;}
}

