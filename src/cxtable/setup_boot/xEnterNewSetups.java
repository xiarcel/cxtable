package cxtable.setup_boot;
import java.awt.*;
import java.awt.event.*;


public class xEnterNewSetups extends Thread implements xEntryHook, ActionListener
{
private Frame f = new Frame("Setup window");
private Button process = new Button("Process entries");
Panel for_ftps = new Panel();
Panel for_https =new Panel();
Panel for_phpservs = new Panel();
Panel for_phpusers = new Panel();
ScrollPane fftps=new ScrollPane();
ScrollPane fhttps=new ScrollPane();
ScrollPane fphps=new ScrollPane();
ScrollPane fphpu=new ScrollPane();

xSetupDataContainer xsdc;
xSetupDataPanel[] p_ftp, p_http,p_phps,p_phpu;


public xEnterNewSetups(xSetupDataContainer x)
{
xsdc=x;
}

public void run()
{
new Thread(new xEntriesSelector(this)).start();
}

public void perform(String s)
{}

public void perform(String[] s)
{
 int numpans=4;
 if (s==null){System.out.println("s == null");return;}
 int[] types = new int[s.length];
 if (types.length < 4) {return;}
  for (int i=0; i<s.length;i++)
	{
	try{
	   types[i] = Integer.parseInt(s[i]);
	   }
	   catch(Exception e){types[i]=1;}
	}
p_ftp = new xSetupDataPanel[types[0]];
if (p_ftp.length < 1){numpans--;}
if (p_ftp.length > 0)
	{
	for_ftps.setLayout(new GridLayout(1,p_ftp.length));
	}
p_http = new xSetupDataPanel[types[1]];
if (p_http.length <1){numpans--;}
if (p_http.length > 0)
	{
	for_https.setLayout(new GridLayout(1,p_http.length));
	}
p_phps =new xSetupDataPanel[types[2]];
if(p_phps.length <1){numpans--;}
if (p_phps.length > 0)
	{
	for_phpservs.setLayout(new GridLayout(1,p_phps.length));
	}
p_phpu =new xSetupDataPanel[types[3]];
if (p_phpu.length <1){numpans--;}

if (p_phpu.length > 0)
	{
	for_phpusers.setLayout(new GridLayout(1,p_phpu.length));
	}

for (int m=0; m<p_ftp.length; m++)
	{
	xSetupData x = new xFTPServData();
	p_ftp[m] = new xSetupDataPanel(x);
	for_ftps.add(p_ftp[m].create());
		}
fftps.add(for_ftps);
for (int n=0; n<p_http.length; n++)
	{
	xSetupData x=new xHTTPUserData();
	p_http[n] = new xSetupDataPanel(x);
	for_https.add(p_http[n].create());
	
	}
fhttps.add(for_https);
for (int o=0; o<p_phps.length;o++)
	{
	xSetupData x=new xPHPServerData();
	p_phps[o]=new xSetupDataPanel(x);
	for_phpservs.add(p_phps[o].create());
	}
fphps.add(for_phpservs);
for (int p=0;p<p_phpu.length;p++)
	{
	xSetupData x=new xPHPUserData();
	p_phpu[p] =new xSetupDataPanel(x);
	for_phpusers.add(p_phpu[p].create());
	
	}
fphpu.add(for_phpusers);

Panel inner=new Panel(); 
Panel inner_n=new Panel(); inner_n.setLayout(new BorderLayout());
Panel inner_s=new Panel(); inner_s.setLayout(new BorderLayout());

inner.setLayout(new BorderLayout());

if(p_ftp.length >0){inner_n.add(fftps,BorderLayout.WEST);fftps.setSize(new Dimension(290,160));}
if(p_http.length >0){inner_s.add(fhttps,BorderLayout.WEST);fhttps.setSize(new Dimension(290,100));}
if(p_phps.length >0){inner_s.add(fphps,BorderLayout.EAST);fphps.setSize(new Dimension(320,100));}
if(p_phpu.length >0){inner_n.add(fphpu,BorderLayout.EAST);fphpu.setSize(new Dimension(320,90));}
inner.add(inner_n,BorderLayout.NORTH);
inner.add(inner_s,BorderLayout.SOUTH);

f.setLayout(new BorderLayout());

f.add(inner,BorderLayout.NORTH);
f.add(process,BorderLayout.SOUTH);
process.addActionListener(this);

f.pack();

f.addWindowListener(new WindowAdapter()
		{public void windowClosing (WindowEvent we)
			{kill();}
		});

f.setVisible(true);

}

 
	

public void actionPerformed(ActionEvent ae)
{
add(p_ftp);add(p_http);add(p_phps);add(p_phpu);
kill();

}

private void add(xSetupDataPanel[] x)
{
 for (int i=0; i<x.length; i++)
	{xsdc.add(x[i].getSetupData());}
}


private void kill()
{
if (f!=null){f.setVisible(false); f=null;}
}

public static void main(String[] args)
{
 new Thread(new xEnterNewSetups(new xSetupDataContainer())).start();
}

}
