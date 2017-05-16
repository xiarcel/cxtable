package cxtable.setup_boot;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;


public class xSetupDataPanel{

private Panel datapan;
private Panel datapanel;
private xSetupData xsd;
private int control;
private TextField[] entries;
private Label[] queries;


public xSetupDataPanel(xSetupData x)
{
xsd=x;
control=xsd.getDataType();
}

public Dimension getSize()
{ return new Dimension(350,150);
}

public Panel create()
{
datapan =new Panel();
datapan.setSize(new Dimension(330,145));
Panel middle =new Panel();
datapan.setLayout(new BorderLayout());

String[] quests = xsd.getQuestions();
String[] defaults = xsd.getDefaults();
middle.setLayout(new GridLayout(quests.length,1));
entries=new TextField[defaults.length];
queries = new Label[quests.length];

 for(int i=0; i<entries.length;i++)
	{
	entries[i]=new TextField(20);
	entries[i].setText(defaults[i]);
	queries[i]=new Label(quests[i]);
	Panel inners=new Panel();
	inners.setLayout(new BorderLayout());
	inners.add(queries[i],BorderLayout.WEST);
	inners.add(entries[i],BorderLayout.EAST);
	middle.add(inners);
	}
datapan.add(new Label("---start entry--->"),BorderLayout.NORTH);
datapan.add(middle,BorderLayout.CENTER);
datapan.add(new Label("<------end entry--"),BorderLayout.SOUTH);

return datapan;
}

public xSetupData getSetupData()
{
 String[] stuff = new String[entries.length];
 for (int i=0; i<stuff.length;i++){stuff[i] = entries[i].getText();}
 xsd.setElements(stuff);
 return xsd;
}

public static void main(String[] args)
{
 Frame f=new Frame();
 f.setLayout(new GridLayout(4,1));
 final xSetupDataPanel one = new xSetupDataPanel(new xFTPServData());
 final xSetupDataPanel two = new xSetupDataPanel(new xHTTPUserData());
 final xSetupDataPanel three =new xSetupDataPanel(new xPHPServerData());
 final xSetupDataPanel four = new xSetupDataPanel(new xPHPUserData());
 f.add(one.create()); f.add(two.create()); f.add(three.create()); f.add(four.create());

 f.addWindowListener(new WindowAdapter()
			{public void windowClosing(WindowEvent we)
				{
				System.out.println("one");
				System.out.println(one.getSetupData().getFullData());
				System.out.println("two");
				System.out.println(two.getSetupData().getFullData());
				System.out.println("three");
				System.out.println(three.getSetupData().getFullData());
				System.out.println("four");
				System.out.println(four.getSetupData().getFullData());
				System.exit(0);
				}
			    });
f.pack();
f.setVisible(true);
}

}


 