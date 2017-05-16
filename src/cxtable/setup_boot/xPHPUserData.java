package cxtable.setup_boot;

import cxtable.xLineSplit;

public class xPHPUserData implements xSetupData{

public static final int type = xSetupData.USER;
public static final String[] tags = new String[]{"HTTP","NAME"};
public static final String[] query = new String[]{"User-name", "HTTP-registry.php site", "Server's name"};
public static final String[] defs = new String[]{"","http://cxtable.sourceforge.net/",""};
private String fulldata="";
private String[] answers = new String[]{"",""};

public xPHPUserData(){}

public int getDataType(){return type;}
public String getLabel(){return answers[1]+"::"+answers[0];}
public String getFullData(){return fulldata;}
public String[] getElements(){return answers;}
public String[] getTags(){return tags;}
public String[] getQuestions(){return query;}
public String[] getDefaults(){return defs;}

public boolean setElements(String[] elem)
{
 if (elem.length != query.length){return false;}
 answers[0] = new String("");
 if (!elem[1].startsWith("http://")){answers[0]=answers[0]+"http://";}
 answers[0]=answers[0]+elem[1];
 if (!answers[0].endsWith("/")){answers[0]=answers[0]+"/";}
 answers[0]=answers[0]+"registry.php?GET:"+elem[2];
 answers[1]=new String(""+elem[0]);

 fulldata=new String(""+xSetupData.begin+"<TYPE>"+type+"</TYPE>");
 for (int i=0; i<answers.length; i++)
	{
	fulldata=fulldata+xSetupData.begin+"<"+tags[i]+">";
   	fulldata=fulldata+answers[i]+"</"+tags[i]+">";
	}
 return true;
}

public boolean setElements(String data)
{
 fulldata=new String(""+data);
 xLineSplit xls=new xLineSplit();
 for (int i=0; i<tags.length;i++)
	{
	answers[i]=new String(""+xls.ssplit(tags[i],data));
	}
 return true;
}

}

