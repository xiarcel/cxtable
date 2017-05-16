package cxtable.setup_boot;
/*this class handles getting valid info for new setup additions*/


import java.awt.*;
import java.awt.event.*;

 public class xSetupPopup implements xEntryListened, ActionListener{

private Frame topop;
private Button entrydone;
public static int AS_FTP=0;
public static int AS_HTTP=1;
private int which;
private String[] labs; private TextField[] texts;
private xEntryListener xel;


static String[] for_ftp= new String[]{"FTPSite", "FTPUser", "FTP-pass", "FTPDir", "FTP-File"};
static String[] for_http = new String[]{"User name", "HTTP-Address"};

public xSetupPopup(int w, xEntryListener xl)
{xel=xl;
 if ((w<0)|(w>1)){w=0;}
 which=w;
 if (w==0) {labs=for_ftp;topop=new Frame("FTP Entry Panel");}
 if (w==1) {labs=for_http;topop = new Frame("HTTP Entry Panel");}
 entrydone = new Button("Press when done");
 texts = new TextField[labs.length];
}

public void create()
{
 Panel forquests = new Panel();
 forquests.setLayout(new GridLayout((labs.length),1));
 for (int i=0; i<texts.length;i++)
	{
	texts[i]=new TextField(25);
   	Panel this_one = new Panel();
        this_one.setLayout(new BorderLayout());
        this_one.add(new Label(labs[i]),BorderLayout.WEST);
   	this_one.add(texts[i], BorderLayout.EAST);
	forquests.add(this_one);
	}
 topop.setLayout(new BorderLayout());
 topop.add(new Label("Enter the setup data below, press button to commit"), BorderLayout.NORTH);
 topop.add(forquests,BorderLayout.CENTER);
 entrydone.addActionListener(this);
 topop.add(entrydone,BorderLayout.SOUTH);
 topop.addWindowListener(new WindowAdapter()
				{public void windowClosing(WindowEvent we)
					{kill();}
				});
 
 topop.pack();
 topop.setVisible(true);
}

public void kill(){
topop.setVisible(false);
topop=null;
}

public void actionPerformed(ActionEvent ae)
{
 xel.entryDone(this);
}

public String[] getAnswers()
{
String[] s = new String[texts.length];
for (int i=0; i<s.length;i++)
	{s[i]=texts[i].getText();}
return s;
}

public static void main(String[] args)
{
 new xSetupPopup(0,null).create();
 new xSetupPopup(1,null).create();
}
public int getWhich()
{return which;}


}


 


