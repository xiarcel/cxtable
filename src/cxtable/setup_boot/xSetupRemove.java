package cxtable.setup_boot;
import java.awt.*;
import java.awt.event.*;


public class xSetupRemove extends Thread implements ActionListener{
private xSetupDataContainer xsdc;
private Label users=new Label("These are users");
private Label reggies=new Label("These are 'registry-servers'");
private xSetupData[] r,u;
private Checkbox[] cbs_r,cbs_u;
private Button erase=new Button("Press to erase all above checked");
private Frame f=new Frame("View and/or remove entries");


public xSetupRemove(xSetupDataContainer x)
{xsdc=x;}

public void run()
{

r=xsdc.get(new int[]{0,2});
u=xsdc.get(1);
cbs_r=new Checkbox[r.length];
cbs_u=new Checkbox[u.length];
Panel p=new Panel();
p.setLayout(new GridLayout((u.length+r.length+3),1));
p.add(reggies);
for (int i=0; i<r.length;i++)
	{
	cbs_r[i]=new Checkbox(r[i].getLabel(),false);
	p.add(cbs_r[i]);
	}
p.add(users);

for (int j=0; j<u.length;j++)
	{
	cbs_u[j]=new Checkbox(u[j].getLabel(),false);
	p.add(cbs_u[j]);
	}
p.add(erase);
erase.addActionListener(this);
f.add(p);
f.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent we)
			{kill();}
		});
f.pack();
f.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{
 for (int i=0; i<cbs_r.length;i++)
	{
	if (cbs_r[i].getState())
		{xsdc.remove(r[i]);}
	}
 for (int j=0; j<cbs_u.length;j++)
	{
	if (cbs_u[j].getState())
		{xsdc.remove(u[j]);}
	}
kill();
}

public void kill()
{
if (f!=null) {f.setVisible(false); f=null;}
}

}
