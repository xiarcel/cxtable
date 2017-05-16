package cxtable.setup_boot;
import cxtable.xLineSplit;



/*
This reads the setup file, and the user chooses the preferred connection
to proceed with...works with the xRegistryServer, and with the xMobileServer
*/


import java.awt.*;
import java.awt.event.*;
import java.io.*;


 public class xSetupBootPopup extends Thread implements ActionListener{

private String fs = System.getProperty("file.separator");
private String pth = System.getProperty("user.dir");
private String for_reg = "xTableServerSetup.cgt";
private String for_use = "xTableUserSetup.cgt";
private Frame booty;
private File setupfile;
private CheckboxGroup cbg= new CheckboxGroup();
private Checkbox[] cbxs;
private boolean reg_server;
private String framename;
private xLineSplit xls=new xLineSplit();
private xSetupBootListener xbl;
private xSetupData[] xsd;

private Button proceed=new Button("Proceed with boot");



public xSetupBootPopup(String f, boolean b, xSetupBootListener x)
{
if (b==true) {for_reg=f;
	   framename="Registry server selection";
		}
	else {for_use=f;
		framename="MobileServer(FatClient) Selection";
		}
reg_server=b;
xbl=x;
}

public xSetupBootPopup(boolean b, xSetupBootListener x)
{
xbl=x;
reg_server = b;
if (b==true){framename="Registry server selection";}
else {framename="MobileServer(FatClient) Selection";}

}


public void run()
{
booty=new Frame(framename);
booty.addWindowListener(new WindowAdapter()
			{public void windowClosing(WindowEvent we)
				{System.exit(0);}
			});

if (reg_server==true){setupfile = new File(pth+fs+for_reg);}
else {setupfile = new File(pth+fs+for_use);}

String contents = readfile(setupfile);
if (contents.equals("")==true){System.err.println("Empty (\"\") or missing setup file!");
			System.exit(1);}

xSetupDataContainer xsdc = new xSetupDataContainer();
xsdc.add(contents);

if (reg_server){xsd=xsdc.get(new int[]{0,2});}
	else{xsd=xsdc.get(1);}

cbxs = new Checkbox[xsd.length];
for (int j=0; j<xsd.length;j++)
	{
	cbxs[j] = new Checkbox(xsd[j].getLabel(),cbg,false);
	}
booty.setLayout(new BorderLayout());
Panel p=new Panel();
p.setLayout(new GridLayout(cbxs.length,1));
for (int x=0; x<cbxs.length; x++)
	{p.add(cbxs[x]);}
booty.add(new Label("Select and press button to boot"),BorderLayout.NORTH);
booty.add(p,BorderLayout.CENTER);
booty.add(proceed,BorderLayout.SOUTH);
proceed.addActionListener(this);
booty.pack();
booty.setVisible(true);
}


public void actionPerformed(ActionEvent ae)
{
 if(ae.getSource()==proceed)

 { 	int i = check();
	if (i == -1){return;}
 	booty.setVisible(false);
 	booty=null;
 	xbl.boot(xsd[i]);

 }

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

private int check()
{
 if (cbxs == null){return -1;}
 for (int x=0; x<cbxs.length;x++)
	{
	 if (cbxs[x].getState()==true){return x;}
	}
 return -1;
}


/*finis*/
}

