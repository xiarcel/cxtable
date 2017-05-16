package cxtable.setup_boot;
import cxtable.xLineSplit;

public class xFTPServData implements xSetupData{

public final static int type= xSetupData.FTP;
public final static String[] query = new String[] {"Ftp-site","Ftp-user","Ftp-password","Ftp-dir","Ftp-file"};
public final static String[] defs = new String[]{"","","","",""};
public final static String[] tags= new String[]{"FTPSITE","FTPUSER","FTPPASS","FTPDIR","FTPFILE"};
private String fulldata="";
private String[] answers=new String[]{"","","","",""};

public xFTPServData(){}

public int getDataType(){return type;}
public String getFullData(){return fulldata;}
public String[] getElements(){return answers;}
public String[] getQuestions() {return query;}
public String[] getDefaults(){return defs;}
public String[] getTags(){return tags;}
public String getLabel(){return answers[0]+"/"+answers[3]+"/"+answers[4];}

public boolean setElements(String[] elem)
{
 if (elem.length != query.length){return false;}
 fulldata="";
 fulldata=fulldata+xSetupData.begin+"<TYPE>"+type+"</TYPE>";
 for (int i=0; i<answers.length; i++)
	{
	answers[i]= new String(""+elem[i]);
	fulldata=fulldata+xSetupData.begin+"<"+tags[i]+">";
	fulldata=fulldata+elem[i]+"</"+tags[i]+">";
	}
 return true;
}

public boolean setElements(String data)
{
 xLineSplit xls=new xLineSplit();
 fulldata=new String(""+data);
 for (int i=0; i<answers.length;i++)
	{
	answers[i] = new String(""+xls.ssplit(tags[i],data));
	}
 return true;
}
 
}
 
