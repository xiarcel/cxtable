package cxtable.core_comm;
import cxtable.gui.xPanel;

import cxtable.*;

/*This class is new as of 10-26-01..intended to be used for
roundtrip benchmarks...but also for auto command processing
and custom command "negotiating"..more later*/


 public class xAutoProcessor extends Thread implements xListener{

private xLineSplit xline =new xLineSplit();
private xClientConn xcc;
private xPanel report=null;



public xAutoProcessor(xClientConn x)
{
xcc=x;
}

public xAutoProcessor(xClientConn x, xPanel xpan)
{
report=xpan; 
xcc=x;
}

public void run()
{
xcc.setListen(this);
}

public String who()
{
try{
return "((xAutoProcessor{10-26-01}:xcc:"+xcc.who()+"))";
}
catch(Exception e){}
return "xAutoProcessor::10-26-01";
}

public boolean readAll()
{return false;}

public String[] readKeys()
{
return new String[]{"<BENCHF>","<BENCHS>"};
}



public void read(String s)
{
/*for benchmarking*/
long bench = System.currentTimeMillis();

/*replace with dynamic commands*/
String[] comms = xline.split("BENCHF",s);
relay_proc(comms);
String[] comm2= xline.split("BENCHS",s);
return_proc(comm2, bench);
}

private  void relay_proc(String[] s)
{
if (s.length < 1){return;}

try{
   for (int i=0; i<s.length;i++)
	{
	xcc.send("<BENCHS>"+s[i]+"</BENCHS>");
	}
    }
	catch(Exception e){System.out.println("Error sending bench-relay");}


return;
}

public void return_proc(String[] s, long b)
{
if(s.length < 1){return;}

try{
    for (int i=0; i<s.length;i++)
	{
	String dat = xline.ssplit("START",s[i]);
	try{
	    long start = Long.parseLong(dat);
	    double st = (double)start;
	    double ed=(double)b;
	    double dif =( ed-st)/1000;
	    String mssg="Benchmark for "+xcc.getRegID()+"\n";
	    mssg =mssg+"Round trip(in sec):"+dif+"\n";
	    double rate = s[i].length()/dif;
	    mssg=mssg+"Roundtripped at "+rate+" chars-per-sec";
	    if (report !=null) {report.post(mssg);}
	   System.out.println(who()+"::"+mssg);
	    }
	   catch(Exception e)
		{System.out.println("Failed at processing bench");
		 if (report !=null) {report.post("Failed processing bench request");}
		}
	}

    }catch(Exception e){}
return;
}

}
