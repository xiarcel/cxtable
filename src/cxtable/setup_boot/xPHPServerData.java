package cxtable.setup_boot;

import cxtable.xLineSplit;

public class xPHPServerData implements xSetupData{

public static final int type = xSetupData.PHP;
public static final String[] query = new String[]{"Server's name", "Http-path to registry.php"};
public static final String[] defs = new String[]{"","http://cxtable.sourceforge.net/"};
public static final String[] tags =new String[]{"SERV_NAME","HTTP"};
private String fulldata="";
private String[] answers = new String[]{"",""};

public xPHPServerData(){}

public int getDataType(){return type;}
public String getFullData(){return fulldata;}
public String getLabel(){return answers[1]+answers[0];}
public String[] getElements(){return answers;}
public String[] getTags(){return tags;}
public String[] getDefaults(){return defs;}
public String[] getQuestions(){return query;}

public boolean setElements(String[] elem)
{
 if (elem.length != query.length){return false;}
 
 answers[1] = new String("");
 if (!elem[1].startsWith("http://")){answers[1]=answers[1]+"http://";}
 answers[1] = answers[1] + elem[1];
 if (!answers[1].endsWith("/")) {answers[1]=answers[1]+"/";}
 answers[1]=answers[1]+"registry.php?";
 answers[0]=new String(""+elem[0]);

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
 xLineSplit xls=new xLineSplit();
 fulldata=new String(""+data);
 for (int i=0; i<answers.length;i++)
	{
	answers[i] = new String(""+xls.ssplit(tags[i],data));
	}
 return true;
}

}
