package cxtable.setup_boot;

public interface xSetupData{

/*constants*/
public final static int FTP=0;
public final static int USER=1;
public final static int PHP=2;
public final static String begin=System.getProperty("line.separator")+"\t";

/*required methods*/

public int getDataType();
public String getLabel();
public String getFullData();
public String[] getElements();
public String[] getTags();
public String[] getQuestions();
public String[] getDefaults();
public boolean setElements(String[] s);
public boolean setElements(String s);


}
