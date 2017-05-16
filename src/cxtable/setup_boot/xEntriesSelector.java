package cxtable.setup_boot;

import java.awt.*;
import java.awt.event.*;

public class xEntriesSelector extends Thread implements ActionListener
{
 private xEntryHook xeh; private Frame f=new Frame("Please select entry types/quantities");
 private Label lab = new Label("Please select which configs you will be entering");
 private Checkbox ftp=new Checkbox("Registry Server which uses FTP",false);
 private Checkbox http= new Checkbox("Client(User) for an FTP Registry",false);
 private Checkbox phps = new Checkbox("Registry Server that uses 'registry.php'",false);
 private Checkbox phpu = new Checkbox("Client(User) for a 'registry.php' Registry",false);
 private TextField ftp_t=new TextField(4);
 private TextField http_t= new TextField(4);
 private TextField phps_t=new TextField(4);
 private TextField phpu_t=new TextField(4);
 private Button entry =new Button("-ENTER-");
 
public xEntriesSelector(xEntryHook x)
 {xeh=x;}

public void run()
{
 Panel ft = new Panel(); ft.setLayout(new BorderLayout());
 ft.add(ftp,BorderLayout.WEST);
 ft.add(new Label("Qty:"),BorderLayout.CENTER);
 ft.add(ftp_t,BorderLayout.EAST);

 Panel ht = new Panel(); ht.setLayout(new BorderLayout());
 ht.add(http,BorderLayout.WEST);
 ht.add(new Label("Qty:"),BorderLayout.CENTER);
 ht.add(http_t,BorderLayout.EAST);
 
 Panel ps = new Panel(); ps.setLayout(new BorderLayout());
 ps.add(phps,BorderLayout.WEST);
 ps.add(new Label("Qty:"),BorderLayout.CENTER);
 ps.add(phps_t,BorderLayout.EAST);

 Panel pu = new Panel(); pu.setLayout(new BorderLayout());
 pu.add(phpu,BorderLayout.WEST);
 pu.add(new Label("Qty:"),BorderLayout.CENTER);
 pu.add(phpu_t,BorderLayout.EAST);

 Panel et = new Panel(); et.setLayout(new GridLayout(4,1));
 et.add(ft); et.add(ht); et.add(ps); et.add(pu);

 entry.addActionListener(this);
 f.setLayout(new BorderLayout());
 f.add(lab,BorderLayout.NORTH);
 f.add(et,BorderLayout.CENTER);
 f.add(entry,BorderLayout.SOUTH);
 
 f.addWindowListener(new WindowAdapter()
				{public void windowClosing(WindowEvent we)
						{
						kill();
						}
				});

 f.pack();
 f.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{
 if (ae.getSource()==entry)
		{process();}
}

public void process()
{
 String[] data=new String[4];
 if (ftp.getState()) {data[0]=ftp_t.getText();}
	else{data[0]=new String("0");}
 if (http.getState()) {data[1]=http_t.getText();}
	else{data[1]=new String("0");}
 if (phps.getState()) {data[2]=phps_t.getText();}
	else{data[2]=new String("0");}
 if (phpu.getState()) {data[3]=phpu_t.getText();}
	else{data[3]=new String("0");}

 kill();
 xeh.perform(data);
}

public void kill()
{
if (f!=null) {f.setVisible(false); f=null;}
}

}
