package cxtable.gui;
import cxtable.registry.*;
import cxtable.*;
import cxtable.plugin.*;
import cxtable.core_comm.*;



/*this is an example of the 'default' comm shell..now written as a
"plugged" in hard-coded..*/


import java.awt.*;
import java.awt.event.*;

 public class xCommAsPlug extends Thread implements xCommPluginable{

private Frame f=new Frame("xComm Shell as Plugin");
private xLinkableComm xlinkcomm;
private Button process_post;
private Button process_plug;
private Button run_process,multi_plug;
private TextArea ta=new TextArea("",4,30,TextArea.SCROLLBARS_VERTICAL_ONLY);
private xPanel mn=new xPanel("Main Chat","MAIN");
private xPanel ot;
private xPluginable dice;
private xRegDisplay regdisplay;
private String name;
private xPanel report;


public xCommAsPlug(String n)
{
 name=n;
 ot=new xPanel(name,"INDY");
}

public void setNam(String n)
{name=n;}

public void make_visible(boolean b)
{
if (f!=null){f.setVisible(b);}

}

public void setLinkable(xLinkableComm x)
{
xlinkcomm=x;
}

public void setReport(xPanel xp)
{
report=xp;
xlinkcomm.link_reader(xp);
}

public void setRegDisplay(xRegDisplay x)
{
regdisplay=x;
}


public void run()
{

try{xlinkcomm.setMTA(ta);
xlinkcomm.setPanel(mn,true);
xlinkcomm.setPanel(ot,false);
/*set the 'four' buttons*/

process_plug=xlinkcomm.make_plug_button("2-person plugins");
process_post=xlinkcomm.make_post_button("Post this message");
run_process=xlinkcomm.make_process_button("Run new process");
multi_plug=xlinkcomm.make_multi_button("Run a multi-user plugin");


xlinkcomm.link_reader(mn); xlinkcomm.link_reader(ot);
try{dice=xlinkcomm.create_plugin("cxtable.xDicePanel",true,true);}
      catch(Exception e)
	{dice=null;}
Panel mssgy=new Panel();
mssgy.setLayout(new BorderLayout());
mssgy.add(ta,BorderLayout.SOUTH);
mssgy.add(new Label("Type your message here:"),BorderLayout.NORTH);

Panel northw=new Panel(); northw.setLayout(new BorderLayout());
Panel butts=new Panel(); butts.setLayout(new BorderLayout());
Panel butts_outer=new Panel();butts_outer.setLayout(new BorderLayout());
Panel butts_e=new Panel();butts_e.setLayout(new BorderLayout());
butts_e.add(multi_plug,BorderLayout.EAST);
butts_e.add(run_process,BorderLayout.WEST);

butts.add(process_plug,BorderLayout.EAST);
butts.add(process_post,BorderLayout.WEST);

butts_outer.add(butts,BorderLayout.NORTH);
butts_outer.add(butts_e,BorderLayout.SOUTH);
northw.add(mssgy,BorderLayout.NORTH);
northw.add(butts_outer,BorderLayout.CENTER);
if (report !=null)
	{northw.add(report.create(),BorderLayout.SOUTH);}
else{
	report=new xPanel("Report from System:","REPORT");
	report.setSize(5,30,true);
	xlinkcomm.link_reader(report);
	northw.add(report.create(),BorderLayout.SOUTH);
	}
Panel upper=new Panel(); Panel lower=new Panel();
lower.setLayout(new GridLayout(1,2));
upper.setLayout(new BorderLayout());

upper.add(northw,BorderLayout.WEST);
upper.add(regdisplay.create(),BorderLayout.CENTER);
if (dice!=null){upper.add(dice.as_panel(),BorderLayout.EAST);}
else{upper.add(new Label("Dice should be here"),BorderLayout.EAST);}

f.setLayout(new BorderLayout());

lower.add(mn.create());lower.add(ot.create());
f.add(upper,BorderLayout.NORTH);
f.add(lower,BorderLayout.SOUTH);
f.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent we)
			{System.exit(0);}
		});
f.pack();
f.setVisible(true);
}
catch(Exception ee){ee.printStackTrace();}
}

/*done*/
}




