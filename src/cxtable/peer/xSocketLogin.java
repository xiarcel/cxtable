package cxtable.peer;

/*originally for manual entry of name when logging in..replaced with
automated file*/

import java.awt.*;
import java.awt.event.*;


 public class xSocketLogin extends Frame implements Runnable,ActionListener{

String lab = new String("Please type your name");
private Frame f = new Frame(lab);
private Label label = new Label(lab);
private TextField entry = new TextField(20);
private xNameListen xnl;
Panel p = new Panel();
private Button booboo = new Button("Press -here- when done");

public xSocketLogin(xNameListen x)
{xnl=x;}

public void run()
{
f.addWindowListener(new WindowAdapter()
			{public void windowClosing(WindowEvent we)
				{System.exit(0);}
			});

f.setLayout(new BorderLayout());
p.setLayout(new BorderLayout());
p.add(label,BorderLayout.WEST);
p.add(entry,BorderLayout.EAST);
p.setVisible(true);
booboo.addActionListener(this);
f.add(p,BorderLayout.NORTH);
f.add(booboo,BorderLayout.SOUTH);
f.pack();
f.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{
if (ae.getSource() == booboo)
	{String s= entry.getText();
	 entry.setText("");
	 booboo.setEnabled(false);
	 booboo.setLabel("Logging in");
	 f.setVisible(false);
	 xnl.login_name(s);
	
	}
}
/*end*/

}
