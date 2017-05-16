package cxtable.setup_boot;

import cxtable.*;


/*this class handles setup of FTP and HTTP files for use with
the automated nature of the program..

These files can be written by hand... FTP setups need
ftpsite, ftpuser, ftppass, ftpdir, ftpfile

HTTP setups need
user, http
*/

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.io.*;


 public class xSetup extends Thread implements xEntryListener,ActionListener,xSetupHook{

String dirpath = System.getProperty("user.dir");
String fs = System.getProperty("file.separator");
private File ftpfilepath;
private File httpfilepath;
private xPHPData[] phpdat;
private xPHPServData[] phpservdat;
private xHTTPData[] htpdat;
private xFTPData[] ftpdat;
private xLineSplit xls=new xLineSplit();
private Frame sframe=new Frame("xTable Setups......");
private Button add_user,add_reg,view_user,view_reg,save;
private Label ftplabs,htplabs;



public xSetup(String fname, String hname)
{
ftpfilepath=new File(dirpath+fs+fname);
httpfilepath=new File(dirpath+fs+hname);
}

public xSetup()
{
ftpfilepath=new File(dirpath+fs+"xTableFTPsetup.cgt");
httpfilepath=new File(dirpath+fs+"xTableHTTPsetup.cgt");
}

public void run()
{
add_user=new Button("Add a new user profile");
add_reg=new Button("Add a new registry-serv profile");
view_user=new Button("View (and remove) user profiles");
view_reg=new Button("View (and remove) registry profiles");
save=new Button("Save current configurations");

add_user.addActionListener(this);
add_reg.addActionListener(this);
view_user.addActionListener(this);
view_reg.addActionListener(this);
save.addActionListener(this);

sframe.setLayout(new GridLayout(7,1));
sframe.add(add_user);
sframe.add(add_reg);
sframe.add(view_user);
sframe.add(view_reg);

/*create arrays for starts*/


String ftps = readfile(ftpfilepath);
String htps = readfile(httpfilepath);

String[] ftp_spots = xls.split("REG",ftps);
Vector v=new Vector(); Vector u=new Vector();
for (int i=0; i<ftp_spots.length;i++)
	{
	try{v.addElement(new xFTPData(ftp_spots[i]));}
		catch(Exception e)
			{}
	}
ftpdat=new xFTPData[v.size()];
for (int j=0; j<ftpdat.length;j++)
	{
	ftpdat[j]=(xFTPData)v.elementAt(j);
	}


String[] http_spots = xls.split("USER",htps);
for (int k=0; k<http_spots.length;k++)
	{
	try{u.addElement(new xHTTPData(http_spots[k]));}
		catch(Exception e)
			{}
	}
htpdat = new xHTTPData[u.size()];
for (int l=0; l<htpdat.length;l++)
	{
	htpdat[l]=(xHTTPData)u.elementAt(l);
	}
	
ftplabs=new Label("There are "+ftpdat.length+" registry setups");
htplabs=new Label("There are "+htpdat.length+" user setups");

sframe.add(ftplabs);sframe.add(htplabs);

sframe.add(save);

sframe.addWindowListener(new WindowAdapter()
			{public void windowClosing(WindowEvent we)
				{System.exit(0);}
			});
sframe.pack();
sframe.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{
if (ae.getSource()==save){save();}
if (ae.getSource()==add_reg){new xSetupPopup(0,this).create();}
if (ae.getSource()==add_user){new xSetupPopup(1,this).create();}
if (ae.getSource()==view_reg)
{
try{new Thread(new xViewPanel(ftpdat,this)).start();}
	catch(Exception e){update();
				System.out.println("Call on 0 cb[]");}
}


if (ae.getSource()==view_user)
{
try{new Thread(new xViewPanel(htpdat,this)).start();}
	catch(Exception e){update();System.out.println("Call on 0 cb[]");}
}


}

public void entryDone(xEntryListened ed)
{
String[] answ = ed.getAnswers();
for (int i=0; i<answ.length;i++)
	{System.out.println("answer #"+i+":"+answ[i]);}
if (ed.getWhich() == 0)
	{process_new_ftp(answ);}
else {process_new_http(answ);}
ed.kill();
}

private void save()
{
String fstring="";String hstring="";

for (int i=0; i<ftpdat.length;i++)
	{
	fstring=fstring+"<REG>\n"+ftpdat[i].getSave()+"\n</REG>\n";
	}

for (int j=0; j<htpdat.length;j++)
	{
	hstring=hstring+"<USER>\n"+htpdat[j].getSave()+"\n</USER>\n";
	}


try{
writefile(ftpfilepath,fstring);
System.out.println("Wrote reggies:OK");
}
catch(Exception e){System.out.println("Failed at writing reggies");}

try{
writefile(httpfilepath,hstring);
System.out.println("Wrote users:OK");

}
catch(Exception e){System.out.println("Failed at writing users");
}

}

private void writefile(File f, String s) throws Exception
{

   FileWriter fw =new FileWriter(f);
   fw.write(s);
   fw.close();
   
}



private String readfile(File f)
{
try{
  BufferedReader br=new BufferedReader(new FileReader(f));
  String cont="";
  String s;
  while ((s=br.readLine())!=null)
	{
	cont=cont+s+"\n";
	}
   br.close();
  return cont;
   }
catch(Exception e)
	{return "";}
}



private void process_new_ftp(String[] s)
{
 String ss = "";
 try{
     ss="<FTPSITE>"+s[0]+"</FTPSITE>\n";
     ss=ss+"<FTPUSER>"+s[1]+"</FTPUSER>\n";
     ss=ss+"<FTPPASS>"+s[2]+"</FTPPASS>\n";
     ss=ss+"<FTPDIR>"+s[3]+"</FTPDIR>\n";
     ss=ss+"<FTPFILE>"+s[4]+"</FTPFILE>\n";
     xFTPData xfd = new xFTPData(ss);
     Vector v=new Vector();
     for (int i=0; i<ftpdat.length;i++)
	{v.addElement(ftpdat[i]);}
     v.addElement(xfd);
     ftpdat=null;
     ftpdat=new xFTPData[v.size()];
     for (int j=0; j<ftpdat.length;j++)
	{ftpdat[j]=(xFTPData)v.elementAt(j);}
     }
     catch(Exception e){System.out.println("Error on new ftp:"+e.toString());}
	update();

}

public void setFTP(xFTPData[] f)
{
ftpdat=f;
}

public void setHTTP(xHTTPData[] h)
{
htpdat=h;
}


public void setPHP(xPHPData[] x)
{phpdat=x;}

public void setPHPServ(xPHPServData[] x)
{phpservdat=x;}



public void update()
{
htplabs.setText("There are "+htpdat.length+" user(s) set up");
ftplabs.setText("There are "+ftpdat.length+" registr(y|ies) set up");
}

     
private void process_new_http(String[] s)
{
String ss="";
try{
	ss="<NAME>"+s[0]+"</NAME>\n";
	ss=ss+"<HTTP>"+s[1]+"</HTTP>\n";
	xHTTPData xhd = new xHTTPData(ss);
	Vector v=new Vector();
	for (int i=0; i<htpdat.length;i++)
	{
	v.addElement(htpdat[i]);
	}
	v.addElement(xhd);
	htpdat=null;
	htpdat=new xHTTPData[v.size()];
	for (int j=0; j<htpdat.length;j++)
	{
	htpdat[j] = (xHTTPData)v.elementAt(j);
	} 
     }
	catch(Exception e)
		{System.out.println("Error on new HTTP::"+e.toString());}
	update();
}

public void kill()
{
System.exit(0);
}


public static void main(String[] args)
{
new Thread(new xSetup()).start();
}

/*finis*/
}




