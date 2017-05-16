package cxtable.setup_boot;

import cxtable.xLineSplit;

public class xHTTPUserData implements xSetupData{

public static final int type = xSetupData.USER;
public static final String[] query = new String[]{"Http-site", "User-name"};
public static final String[] defs = new String[]{"",""};
public static final String[] tags = new String[]{"HTTP","NAME"};
private String fulldata="";
private String[] answers=new String[]{"",""};

public xHTTPUserData(){}

public int getDataType(){return type;}
public String[] getDefaults(){return defs;}
public String[] getTags(){return tags;}
public String getFullData(){return fulldata;}
public String[] getElements(){return answers;}
public String getLabel(){return answers[1]+"::"+answers[0];}
public String[] getQuestions(){return query;}

public boolean setElements(String[] elem)
{
 if (elem.length != query.length){return false;}
 fulldata= new String(""+xSetupData.begin+"<TYPE>"+type+"</TYPE>");
 for (int i=0; i<answers.length;i++)
	{
	answers[i]=new String(""+elem[i]);
	fulldata=fulldata+xSetupData.begin+"<"+tags[i]+">";
	fulldata=fulldata+elem[i]+"</"+tags[i]+">";
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
