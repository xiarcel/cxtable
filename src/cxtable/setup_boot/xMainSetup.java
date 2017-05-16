package cxtable.setup_boot;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class xMainSetup extends Thread implements xSetupContainerListener, ActionListener{

private Frame f = new Frame("FTP-server/USER/PHP-server setup");
private String php_l=new String("PHP-server configs:");
private String ftp_l=new String("FTP-server configs:");
private String use_l=new String("User configs:");
private Label lab_php,lab_ftp,lab_use;
private File server_file;
private File user_file;
private String fs=System.getProperty("file.separator");
private String bdir=System.getProperty("user.dir");
private xSetupDataContainer datacontain=new xSetupDataContainer();
private Button add_data = new Button("Add new configurations");
private Button remove_data =new Button("Remove/View configurations");
private Button save_data =new Button("Save these configs to file");
private String ls=System.getProperty("line.separator");


public xMainSetup()
{
server_file=new File(bdir+fs+"xTableServerSetup.cgt");
user_file=new File(bdir+fs+"xTableUserSetup.cgt");
}

public xMainSetup(String f, String h)
{
server_file=new File(bdir+fs+f);
user_file=new File(bdir+fs+h);
}

public void run()
{
datacontain.setListen(this);
String sdat=readfile(server_file);
String udat=readfile(user_file);

lab_php=new Label(php_l+"0");
lab_ftp=new Label(ftp_l+"0");
lab_use=new Label(use_l+"0");
Panel p=new Panel();p.setLayout(new GridLayout(6,1));
p.add(add_data);p.add(remove_data);p.add(lab_ftp);p.add(lab_php);
p.add(lab_use);p.add(save_data);
add_data.addActionListener(this);remove_data.addActionListener(this);
save_data.addActionListener(this);

f.add(p);
f.pack();
f.addWindowListener(new WindowAdapter()
				{public void windowClosing(WindowEvent we)
					{System.exit(0);}
				});
f.setVisible(true);
datacontain.add(sdat);
datacontain.add(udat);
update();
}

public void actionPerformed(ActionEvent ae)
{
 Object o=ae.getSource();
 if (o==save_data){save();}
 if (o==add_data){new Thread(new xEnterNewSetups(datacontain)).start();}
 if (o==remove_data){new Thread(new xSetupRemove(datacontain)).start();}

}

private void save()
{
String servdat ="This setup file contains server data for xRegistryServer"+ls+ls;
String usedat ="This setup file contains user data (for xMobileServers)"+ls+ls;
xSetupData[] servs= datacontain.get(new int[]{0,2});
xSetupData[] users= datacontain.get(1);

for(int i=0;i<servs.length;i++)
	{servdat=servdat+"<SETUPDATA>"+servs[i].getFullData()+"</SETUPDATA>"+ls;
	}
for(int j=0;j<users.length;j++)
	{usedat=usedat+"<SETUPDATA>"+users[j].getFullData()+"</SETUPDATA>"+ls;
	}
writefile(server_file,servdat);
writefile(user_file,usedat);
}




public void update()
{
lab_php.setText(php_l+datacontain.get(2).length);
lab_ftp.setText(ftp_l+datacontain.get(0).length);
lab_use.setText(use_l+datacontain.get(1).length);
}



private void writefile(File f, String s) 
{
try{
   System.out.println("Writing file:"+f.toString());
   FileWriter fw =new FileWriter(f);
   fw.write(s);
   fw.close();
   System.out.println("Success.");
   }
	catch(Exception e)
		{System.out.println("Failed!");
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
public static void main(String[] args)
{
if (args.length == 2){new Thread(new xMainSetup(args[0],args[1])).start();}
else{new Thread(new xMainSetup()).start();}
}

}
